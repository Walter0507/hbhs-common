package com.hbhs.common.distribute.lock.reentrant.local;

import com.hbhs.common.distribute.lock.DistributeLockKey;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class LocalCacheClient {
    private Map<String, String> lockKeyMap = new ConcurrentHashMap<>();
    private Map<String, Long> lockExpireMap = new ConcurrentHashMap<>();

    public boolean lock(DistributeLockKey lockKey) {

        String key = lockKey.lockKey();
        synchronized (LocalCacheClient.class){
            if (lockKeyMap.containsKey(key)){
                if (lockExpireMap.get(key)>System.currentTimeMillis()){
                    log.debug("Current lock is locked, lockKey:{}, lockTransactionId:{}, expiredTime:{}", key, lockKeyMap.get(key), lockExpireMap.get(key));
                    return false;
                }
            }
            lockKeyMap.put(key, lockKey.transactionId());
            lockExpireMap.put(key, System.currentTimeMillis()+lockKey.unit().toMillis(lockKey.ttl()));
        }
        return true;
    }

    public boolean unlock(DistributeLockKey lockKey) {
        String key = lockKey.lockKey();
        String transactionId = lockKey.transactionId();
        synchronized (LocalCacheClient.class){
            if (lockKeyMap.containsKey(key)){
                if (!transactionId.equalsIgnoreCase(lockKeyMap.get(key))){
                    log.debug("Lock transactionId not match, lockTransactionId:{}, currentTransactionId:{}", lockKeyMap.get(key), transactionId);
                    return false;
                }
                lockKeyMap.remove(key);
                lockExpireMap.remove(key);
            }
        }
        return true;
    }

}
