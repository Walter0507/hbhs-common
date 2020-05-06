package com.hbhs.common.distribute.lock.reentrant.redis;

import com.hbhs.common.distribute.lock.DistributeLockKey;
import com.hbhs.common.distribute.lock.reentrant.AbstractDistributeReeentrantLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
public class RedisDistributeReentrantLock extends AbstractDistributeReeentrantLock {

    private RedisDataClient redisDataClient;
    private long waitingMillisTime;

    public RedisDistributeReentrantLock(RedisTemplate<Object, Object> redisTemplate) {
        this(redisTemplate, 300);
    }

    public RedisDistributeReentrantLock(RedisTemplate<Object, Object> redisTemplate, long waitingMillisTime) {
        redisDataClient = new RedisDataClient(redisTemplate);
        this.waitingMillisTime = waitingMillisTime;
    }

    @Override
    public boolean lockByKey(DistributeLockKey lockKey) throws InterruptedException {
        return redisDataClient.lock(lockKey);
    }

    @Override
    public long waitingMillisTime() {
        return waitingMillisTime;
    }

    @Override
    public boolean unlock(DistributeLockKey lockKey) {
        return redisDataClient.unlock(lockKey);
    }
}
