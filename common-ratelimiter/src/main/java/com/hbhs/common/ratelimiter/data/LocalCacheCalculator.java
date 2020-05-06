package com.hbhs.common.ratelimiter.data;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class LocalCacheCalculator implements ICalculator {
    private Map<String, AtomicLong> keyCountMap = new ConcurrentHashMap<>();
    private long pauseMillSecond ;
    private List<KeyEntity> keyList = new ArrayList<>();

    private ExecutorService executorService = null;

    public LocalCacheCalculator(long pauseMillSecond, int maxCacheSize){
        this.pauseMillSecond = pauseMillSecond<0?100L:pauseMillSecond;
        initExpireThread(maxCacheSize);
    }

    private void initExpireThread(int maxCacheSize){
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("rate-limiter-key-expired-job").build();
        executorService = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(maxCacheSize), factory, new ThreadPoolExecutor.AbortPolicy());
        executorService.execute(new ExpiredThread(this));
    }

    @Override
    public Long incrementAndExpired(String key, int delta, int expireMinSecond) {
        if (key==null||"".equals(key)){
            return 0L;
        }
        makeSureKeyExist(key, expireMinSecond);
        return keyCountMap.get(key).addAndGet(delta);
    }

    private void makeSureKeyExist(String key, int expireMinSecond){
        if (keyCountMap.containsKey(key)){return ;}
        synchronized (this){
            if (!keyCountMap.containsKey(key)){
                keyCountMap.put(key,new AtomicLong(0L));
                keyList.add(new KeyEntity(key, System.currentTimeMillis()+expireMinSecond));
            }
        }
    }

    private boolean expireFirstKey(){
        if (keyList==null||keyList.size()==0){return Boolean.FALSE;}
        if (keyList.get(0).getExpiredTime()<=System.currentTimeMillis()){
            synchronized (this){
                KeyEntity entity = keyList.get(0);
                keyList.remove(0);
                keyCountMap.remove(entity.getKey());
//                System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date())+" - remove key: "+ entity.getKey());
                log.info("Already remove current key: {}, expireTime:{}, currentTime:{}",
                        entity.getKey(), entity.getExpiredTime(), System.currentTimeMillis());
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public  void shutdown(){
        executorService.shutdown();
    }

    private class ExpiredThread extends Thread {

        private LocalCacheCalculator calculator;
        public ExpiredThread(LocalCacheCalculator calculator){
            this.calculator = calculator;
        }
        @Override
        public void run(){
            try {
                while (true){
                    try {
                        long waitTime = calculator.pauseMillSecond;
                        boolean expired = calculator.expireFirstKey();
                        while (expired){
                            expired = calculator.expireFirstKey();
                        }
                        Thread.sleep(waitTime);
                    }catch (Exception e){
                        e.printStackTrace();
                        log.error("Failed to execute local rate-limit expire action", e);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Data
    @NoArgsConstructor@AllArgsConstructor
    private static class KeyEntity{
        private String key;
        private long expiredTime;
    }
}
