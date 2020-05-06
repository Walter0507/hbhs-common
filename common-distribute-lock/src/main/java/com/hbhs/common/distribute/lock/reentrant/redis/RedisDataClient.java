package com.hbhs.common.distribute.lock.reentrant.redis;

import com.hbhs.common.distribute.lock.DistributeLockKey;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.Collections;

public class RedisDataClient {

    private static final String LUA_SCRIPT_LOCK = ""
            + "\nlocal r = tonumber(redis.call('SETNX', KEYS[1],ARGV[1]));"
            + "\nredis.call('PEXPIRE',KEYS[1],ARGV[2]);"
            + "\nreturn r";
    private static final String LUA_SCRIPT_UNLOCK = ""
            + "\nlocal v = redis.call('GET', KEYS[1]);"
            + "\nlocal r= 0;"
            + "\nif v == ARGV[1] then"
            + "\nr =redis.call('DEL',KEYS[1]);"
            + "\nend"
            + "\nreturn r";

    private DefaultRedisScript<Boolean> lockScript;
    private DefaultRedisScript<Boolean> unlockScript;

    private RedisTemplate<Object, Object> redisTemplate;

    public RedisDataClient(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        // lock
        lockScript = new DefaultRedisScript<>();
        lockScript.setScriptText(LUA_SCRIPT_LOCK);
        lockScript.setResultType(Boolean.TYPE);

        // unlock
        unlockScript = new DefaultRedisScript<>();
        unlockScript.setScriptText(LUA_SCRIPT_UNLOCK);
        unlockScript.setResultType(Boolean.TYPE);
    }

    public boolean lock(DistributeLockKey lockKey) {
        Boolean result = redisTemplate.execute(lockScript,
                Collections.singletonList(lockKey.lockKey()), Arrays.asList(lockKey.transactionId(),
                        lockKey.unit().toMillis(lockKey.ttl())));
        return result != null && result;
    }


    public boolean unlock(DistributeLockKey lockKey) {
        Boolean result = redisTemplate.execute(unlockScript,
                Collections.singletonList(lockKey.lockKey()), Collections.singletonList(lockKey.transactionId()));
        return result != null && result;
    }
}
