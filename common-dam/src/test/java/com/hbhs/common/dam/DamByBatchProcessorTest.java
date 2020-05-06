package com.hbhs.common.dam;


import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DamByBatchProcessorTest {

    public static void main(String[] args) {
        DamByBatchProcessor<User> processor = new DamByBatchProcessor<>(10,new DamByBatchProcessor.BatchEventHandler<User>() {
            @Override
            public boolean handler(List<User> requestList) {
                System.out.println("BATCH HANDLER START... ...");
                for (User user : requestList) {
                    System.out.println(user);
                }
                System.out.println("BATCH HANDLER END... ...");
                return true;
            }
        });

        int size = 10;
        CountDownLatch latch = new CountDownLatch(size);
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < size; i++) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i1 = 0; i1 < 10; i1++) {
                        processor.process(new User(Thread.currentThread().getName()+"-"+i1, "THREAD - "+i1));
                        try {
                            Thread.sleep(new Random().nextInt(100));
                        }catch (Exception e){}
                    }
                    latch.countDown();
                }
            });
        }
        try {
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        processor.flush();
        service.shutdown();
    }

    @Data@NoArgsConstructor@AllArgsConstructor
    public static class User{
        private String id;
        private String name;
    }
}