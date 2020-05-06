package com.hbhs.common.distribute.lock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

@Data
@NoArgsConstructor@AllArgsConstructor
public class DistributeLockIdKey implements DistributeLockKey {

    private String id;
    private String transactionId;
    private long ttl;
    private TimeUnit unit;

    @Override
    public String lockKey() {
        return id;
    }

    @Override
    public String transactionId() {
        return transactionId;
    }

    @Override
    public long ttl() {
        return ttl;
    }

    @Override
    public TimeUnit unit() {
        return unit;
    }
}
