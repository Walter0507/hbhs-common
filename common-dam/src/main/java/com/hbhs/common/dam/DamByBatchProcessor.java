package com.hbhs.common.dam;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DamByBatchProcessor<R> {
    private int batchSize = 100;
    private BatchEventHandler<R> eventHandler;
    private List<R> requestList = new ArrayList<>();

    public DamByBatchProcessor(BatchEventHandler<R> handler){
        this(100, handler);
    }
    public DamByBatchProcessor(int batchSize, BatchEventHandler<R> handler){
        this.batchSize = batchSize;
        this.eventHandler = handler;
    }

    public synchronized void process(R request){

        requestList.add(request);
        if (requestList.size() >= batchSize){
            doBatchHandler();
        }

    }

    public synchronized void flush(){
        if (requestList.size() >= batchSize){
            doBatchHandler();
        }
    }

    private void doBatchHandler(){
        boolean result = eventHandler.handler(requestList);
        if (log.isDebugEnabled()){
            log.debug("Execute current request event: request size:{}, result:{}", requestList.size(), result);
        }
        requestList.clear();
    }

    public interface BatchEventHandler <R>{
        boolean handler(List<R> requestList);
    }
}
