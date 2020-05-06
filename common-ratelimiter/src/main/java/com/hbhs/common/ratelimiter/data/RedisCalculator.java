package com.hbhs.common.ratelimiter.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.Collections;

@Slf4j
public class RedisCalculator implements ICalculator {

    private RedisTemplate<Object, Object> redisTemplate;
    private DefaultRedisScript<Long> redisScript;

    public RedisCalculator(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        initRedisLuaScript();
    }

    private void initRedisLuaScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<Long>();
        redisScript
                .setScriptSource(new ResourceScriptSource(new ClassPathResource("/scripts/redis/redis_incr_by_expire.lua")));
        redisScript.setResultType(Long.class);
        this.redisScript = redisScript;
    }

    @Override
    public Long incrementAndExpired(String key, int delta, int expireMinSecond) {
        return redisTemplate.execute(redisScript, Collections.singletonList(key),
                new String[]{String.valueOf(delta), String.valueOf(expireMinSecond)});
    }

    @Override
    public void shutdown() {
    }

}
