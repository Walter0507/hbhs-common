package com.hbhs.common.ratelimiter.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalCacheCalculatorTest {

    public static void main(String[] args) throws Exception {
        LocalCacheCalculator calculator = new LocalCacheCalculator(1000L, 1024);
        ExecutorService service = Executors.newFixedThreadPool(100);
        List<JobEntity> entityList = init(100);
        for (JobEntity entity : entityList) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 20; i++) {
                        calculator.incrementAndExpired(entity.getPath(),1,entity.getExpiredSecond()*1000);
                        try {
                            Thread.sleep(new Random().nextInt(10)*100L);
                        }catch (Exception e){

                        }
                    }
                }
            });
        }

        System.out.println("aaa");
        Thread.sleep(100000L);

    }

    private static List<JobEntity> init(int count){
        List<JobEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new JobEntity("/public/v1/user/"+i, new Random().nextInt(10)));
        }
        return list;
    }

    @Data
    @NoArgsConstructor@AllArgsConstructor
    private static class JobEntity{
        private String path;
        private int expiredSecond;
    }
}