package com.hbhs.common.distribute.lock;

import com.hbhs.common.distribute.lock.reentrant.DistributeReentrantLock;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DistributeLock {

    @Setter
    private DistributeReentrantLock reentrantLock;

    public <T> T execute(final DistributeLockKey lockKey, final int timeoutSecond, DistributeLockCallback<T> callback) {
        boolean lock = Boolean.FALSE;
        try {
            if (reentrantLock.tryLock(lockKey, timeoutSecond)) {
                lock = Boolean.TRUE;
                return callback.actionWhenGetLock();
            } else {
                return callback.actionWhenTimeout();
            }
        } catch (InterruptedException e1) {
            log.error("Error to execute distribute-lock by interrupt for lockKey: "+lockKey, e1);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Error to execute distribute-lock for lockKey: "+lockKey, e);
        } finally {
            if (lock) {
                reentrantLock.unlock(lockKey);
            }
        }
        return callback.actionWhenError();
    }

}
