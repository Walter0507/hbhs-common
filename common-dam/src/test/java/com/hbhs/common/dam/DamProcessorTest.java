package com.hbhs.common.dam;


import com.hbhs.common.dam.data.local.LocalDamDataStorage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DamProcessorTest {

    public static void main(String[] args) throws Exception{
        DamByTimeProcessor<IdDamRequest, String> processor = new DamByTimeProcessor<>(new LocalDamDataStorage(),
                new TestHandler(), 8,5, TimeUnit.SECONDS);

        for (int i = 0; i < 20; i++) {
            processor.process(new IdDamRequest("ID-"+(i%10)));
        }
        Thread.sleep(24000);
        for (int i = 0; i < 20; i++) {
            processor.process(new IdDamRequest("ID-"+(i%10)));
        }
//        Thread.sleep(1000000);
        processor.shutdown();
    }

    static class TestHandler implements EventHandler<IdDamRequest,String>{
        @Override
        public int expireMillis() {
            return 7*1000;
        }


        @Override
        public String handler(IdDamRequest request) {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+" - processing id: "+request.getId());
            return "processed - "+request.getId();
        }

       /* @Override
        public String handler(IdDamRequest request) {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+" - processing id: "+request.getId());
            return "processed - "+request.getId();
        }*/
    }
}