package com.hbhs.common.distribute.lock.config;

import com.hbhs.common.distribute.lock.DistributeLock;
import com.hbhs.common.distribute.lock.reentrant.DistributeReentrantLock;
import com.hbhs.common.distribute.lock.reentrant.redis.RedisDistributeReentrantLock;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;


public class RedisDistributeLockAutoConfiguration {

    @Bean
    public DistributeReentrantLock distributeReentrantLock(RedisTemplate<Object, Object> redisTemplate){
        return new RedisDistributeReentrantLock(redisTemplate);
    }
    @Bean
    public DistributeLock distributeLock(DistributeReentrantLock distributeReentrantLock){
        DistributeLock distributeLock = new DistributeLock();
        distributeLock.setReentrantLock(distributeReentrantLock);
        return distributeLock;
    }
}
