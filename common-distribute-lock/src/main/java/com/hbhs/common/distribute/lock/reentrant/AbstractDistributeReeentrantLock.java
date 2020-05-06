package com.hbhs.common.distribute.lock.reentrant;

import com.hbhs.common.distribute.lock.DistributeLockKey;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public abstract class AbstractDistributeReeentrantLock implements DistributeReentrantLock {

    @Override
    public boolean tryLock(DistributeLockKey lockKey, int timeoutSecond) throws InterruptedException {
        final long startMillisTime = System.currentTimeMillis();
        final long waitingMillisTime = timeoutSecond * 1000;
        boolean result = Boolean.FALSE;
        long retryMillisTime = waitingMillisTime();
        while (true) {
            result = lockByKey(lockKey);
            if (result) {
                break;
            }
            if (System.currentTimeMillis() - startMillisTime - retryMillisTime > waitingMillisTime) {
                break;
            }
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(retryMillisTime));
        }
        return result;
    }

    public abstract boolean lockByKey(DistributeLockKey lockKey) throws InterruptedException;

    public abstract long waitingMillisTime();

}
