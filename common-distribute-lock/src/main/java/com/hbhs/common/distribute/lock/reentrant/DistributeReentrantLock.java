package com.hbhs.common.distribute.lock.reentrant;

import com.hbhs.common.distribute.lock.DistributeLockKey;

public interface DistributeReentrantLock {

    boolean tryLock(DistributeLockKey lockKey, int timeoutSecond) throws InterruptedException;

    boolean unlock(DistributeLockKey lockKey);
}
