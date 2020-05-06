package com.hbhs.common.tools.id.snokeflake;

import com.hbhs.common.tools.id.mongodb.ObjectId;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SnowflakeIdGeneratorTest {

    public static void main(String[] args) {
        SnowflakeIdGeneratorTest test = new SnowflakeIdGeneratorTest();
//        test.test();
        test.multiTest();

    }

    private void test(){
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator(0L,0L);
        System.out.println(generator.nextId());
        System.out.println(generator.nextId());
        System.out.println(generator.nextStringId());
        System.out.println(generator.nextStringId());
    }

    public void multiTest(){
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator(0L,0L);
        int size = 1000;
        CountDownLatch latch = new CountDownLatch(size);
        ExecutorService service = Executors.newFixedThreadPool(10);
        Set<String> set = new HashSet<>();
        for (int i = 0; i < size; i++) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i1 = 0; i1 < 1000; i1++) {
                        addSet(set, generator.nextStringId());
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
        service.shutdown();
        System.out.println(set.size());
    }
    private synchronized void addSet(Set<String> set, String str){
        set.add(str);
    }
}