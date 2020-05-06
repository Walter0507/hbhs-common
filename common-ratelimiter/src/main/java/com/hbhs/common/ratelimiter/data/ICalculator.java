package com.hbhs.common.ratelimiter.data;

public interface ICalculator {

    Long incrementAndExpired(String key, int delta, int expireMinSecond);

    void shutdown();
}
