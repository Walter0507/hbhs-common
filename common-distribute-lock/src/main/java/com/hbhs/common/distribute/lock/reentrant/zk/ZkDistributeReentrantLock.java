package com.hbhs.common.distribute.lock.reentrant.zk;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hbhs.common.distribute.lock.DistributeLockKey;
import com.hbhs.common.distribute.lock.reentrant.DistributeReentrantLock;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.KeeperException;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ZkDistributeReentrantLock implements DistributeReentrantLock {

    private ThreadLocal<MutexEntry> mutexLocal = new ThreadLocal<>();

    /**
     * 线程池
     */
    private static final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(10,
            new ThreadFactoryBuilder().setNameFormat("zk-distribute-reentrant-lock-job").build());

    /**
     * 每次延迟清理PERSISTENT节点的时间  Unit:MILLISECONDS
     */
    private long delayTimeForClean;

    /**
     * 锁的ID,对应zk一个PERSISTENT节点,下挂EPHEMERAL节点.
     */
    private String path;

    /**
     * zk的客户端
     */
    private CuratorFramework client;


    public ZkDistributeReentrantLock(CuratorFramework client, String path, long delayTimeForClean) {
        this.client = client;
        this.path = path;
        this.delayTimeForClean = delayTimeForClean<=0?1000L:delayTimeForClean;
    }

    @Override
    public boolean tryLock(DistributeLockKey lockKey, int timeoutSecond) throws InterruptedException {
        try {
            String realPath = path + lockKey.lockKey();
            /*
             * 使用 zk 共享锁实现
             */
            InterProcessMutex interProcessMutex = new InterProcessMutex(client, realPath);
            mutexLocal.set(new MutexEntry(interProcessMutex, realPath));
            return interProcessMutex.acquire(timeoutSecond, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public boolean unlock(DistributeLockKey lockKey) {
        MutexEntry entry = null;
        try {
            entry = mutexLocal.get();
            if (entry == null) {
                return Boolean.TRUE;
            }
            entry.getInterProcessMutex().release();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        } finally {
            mutexLocal.remove();
            if (entry!=null){
                executorService.schedule(new NodeCleanerThread(client, entry.getPath()), delayTimeForClean, TimeUnit.MILLISECONDS);
            }

        }
        return true;
    }

    static class NodeCleanerThread implements Runnable {
        private CuratorFramework client;
        private String path;

        NodeCleanerThread(CuratorFramework client, String path) {
            this.client = client;
            this.path = path;
        }

        @Override
        public void run() {
            try {
                List list = client.getChildren().forPath(path);
                if (list == null || list.isEmpty()) {
                    client.delete().forPath(path);
                }
            } catch (KeeperException.NoNodeException e1) {
                //nothing
            } catch (Exception e) {
                //准备删除时,正好有线程创建锁
                log.error("Error to delete zk path: "+path, e);
            }
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class MutexEntry {
        private InterProcessMutex interProcessMutex;
        private String path;
    }
}
