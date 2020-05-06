package com.hbhs.common.distribute.lock;

public interface DistributeLockCallback<T> {

    T actionWhenGetLock();

    T actionWhenTimeout();

    T actionWhenError();
}
