package com.hbhs.common.distribute.lock.config;

import com.hbhs.common.distribute.lock.DistributeLock;
import com.hbhs.common.distribute.lock.reentrant.DistributeReentrantLock;
import com.hbhs.common.distribute.lock.reentrant.zk.ZkDistributeReentrantLock;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.context.annotation.Bean;


public class ZkDistributeLockAutoConfiguration {

    @Bean
    public DistributeReentrantLock distributeReentrantLock(CuratorFramework curatorFramework){
        return new ZkDistributeReentrantLock(curatorFramework, "/DISTRIBUTE/LOCK/", 1000L);
    }

    @Bean
    public DistributeLock distributeLock(DistributeReentrantLock distributeReentrantLock){
        DistributeLock distributeLock = new DistributeLock();
        distributeLock.setReentrantLock(distributeReentrantLock);
        return distributeLock;
    }
}
