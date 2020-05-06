package com.hbhs.common.distribute.lock;


import com.hbhs.common.distribute.lock.reentrant.local.LocalReentrantLock;
import javafx.util.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DistributeLockTest {

    public static void main(String[] args) {
        test();

//        oneIdTesting();
    }

    private static void oneIdTesting(){
        DistributeLock lock = new DistributeLock();
        lock.setReentrantLock(new LocalReentrantLock());

        ExecutorService service = Executors.newFixedThreadPool(10);
        List<DistributeLockIdKey> list = build(1);
        for (int i = 0; i < 10; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    String result = lock.execute(list.get(0), 5, new DistributeLockCallback<String>() {
                        @Override
                        public String actionWhenGetLock() {
                            try {
                                Thread.sleep(new Random().nextInt(4000));
                            }catch (Exception e){}
                            return "SUCCESS";
                        }

                        @Override
                        public String actionWhenTimeout() {
                            try {
                                Thread.sleep(new Random().nextInt(4000));
                            }catch (Exception e){}
                            return "TIMEOUT";
                        }

                        @Override
                        public String actionWhenError() {
                            return "ERROR";
                        }
                    });

                    System.out.println(Thread.currentThread().getName()+": "+list.get(0).getId()+": "+result);
                }
            });
        }
    }

    private static void test(){
        DistributeLock lock = new DistributeLock();
        lock.setReentrantLock(new LocalReentrantLock());

        List<DistributeLockIdKey> list = build(1);
        String result = lock.execute(list.get(0), 5, new DistributeLockCallback<String>() {
            @Override
            public String actionWhenGetLock() {
                try {
                    Thread.sleep(new Random().nextInt(4000));
                }catch (Exception e){}
                return "SUCCESS";
            }

            @Override
            public String actionWhenTimeout() {
                try {
                    Thread.sleep(new Random().nextInt(4000));
                }catch (Exception e){}
                return "TIMEOUT";
            }

            @Override
            public String actionWhenError() {
                return "ERROR";
            }
        });

        System.out.println(Thread.currentThread().getName()+": "+list.get(0).getId()+": "+result);
    }
    
    private static List<DistributeLockIdKey> build(int n){
        List<DistributeLockIdKey> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(new DistributeLockIdKey("id-"+i, UUID.randomUUID().toString(), 5L, TimeUnit.SECONDS));
        }
        return list;
    }
}