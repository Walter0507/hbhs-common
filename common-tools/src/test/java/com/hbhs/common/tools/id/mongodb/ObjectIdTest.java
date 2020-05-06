package com.hbhs.common.tools.id.mongodb;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ObjectIdTest {

    public static void main(String[] args) {
        ObjectIdTest test = new ObjectIdTest();
//        test.generateId();
        test.multiTest();
    }

    private void generateId(){
        System.out.println(ObjectId.get().toString());
        System.out.println(ObjectId.get().toString());
        System.out.println(ObjectId.get().toString());
    }

    private void multiTest(){
        int size = 1000;
        CountDownLatch latch = new CountDownLatch(size);
        ExecutorService service = Executors.newFixedThreadPool(10);
        Set<String> set = new HashSet<>();
        for (int i = 0; i < size; i++) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i1 = 0; i1 < 1000; i1++) {
                        addSet(set, ObjectId.get().toString());
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