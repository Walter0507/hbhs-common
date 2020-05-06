package com.hbhs.common.distribute.lock.reentrant.local;

import com.hbhs.common.distribute.lock.DistributeLockKey;
import com.hbhs.common.distribute.lock.reentrant.AbstractDistributeReeentrantLock;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalReentrantLock extends AbstractDistributeReeentrantLock {
    private LocalCacheClient localCacheClient;
    private long waitingMillisTime;

    public LocalReentrantLock() {
        this(300);
    }

    public LocalReentrantLock(long waitingMillisTime) {
        localCacheClient = new LocalCacheClient();
        this.waitingMillisTime = waitingMillisTime;
    }

    @Override
    public boolean lockByKey(DistributeLockKey lockKey) throws InterruptedException {
        return localCacheClient.lock(lockKey);
    }

    @Override
    public long waitingMillisTime() {
        return waitingMillisTime;
    }

    @Override
    public boolean unlock(DistributeLockKey lockKey) {
        return localCacheClient.unlock(lockKey);
    }
}
