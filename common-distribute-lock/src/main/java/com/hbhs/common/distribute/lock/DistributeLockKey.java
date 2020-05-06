package com.hbhs.common.distribute.lock;

import java.util.concurrent.TimeUnit;

public interface DistributeLockKey {

    String lockKey();

    String transactionId();

    long ttl();

    TimeUnit unit();
}
