package com.hbhs.common.dam;

import com.hbhs.common.dam.data.DamDataStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;


@Slf4j
public class DamByTimeProcessor<REQ extends DamRequest, RES> {
    private ScheduledExecutorService executorService;
    private DamDataStorage dataStorage;
    private EventHandler<REQ, RES> eventHandler;

    private int expireSecond = 10;

    public DamByTimeProcessor(DamDataStorage damDataStorage, EventHandler<REQ, RES> eventHandler, int expireSecond){
        this(damDataStorage, eventHandler, expireSecond, 10, TimeUnit.SECONDS);
    }
    public DamByTimeProcessor(DamDataStorage damDataStorage, EventHandler<REQ, RES> eventHandler, int expireSecond, int period, TimeUnit unit){
        this.dataStorage = damDataStorage;
        this.eventHandler = eventHandler;
        this.expireSecond = expireSecond;
        initSchedule(period, unit);
    }

    public RES process(REQ request){
        RES result = null;
        synchronized (DamByTimeProcessor.class){
            if (dataStorage.addRequestToStandbyQueueIfNeed(request)){
                if (log.isDebugEnabled()){
                    log.debug("Request already executed, add into standby queue. request:{}", request);
                }
                return null;
            }
            result = eventHandler.handler(request);
            dataStorage.addRequestToExecutedQueue(request, eventHandler.expireMillis());
        }

        if (log.isDebugEnabled()){
            log.debug("Execute current request event: request:{}, result:{}", request, result);
        }
        return result;
    }

    public void shutdown(){
        executorService.shutdown();
    }

    private void initSchedule(int period, TimeUnit unit){
        executorService = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<REQ> requestList = dataStorage.getExecutableRequest();
                if (CollectionUtils.isEmpty(requestList)){
                    return;
                }
                for (REQ request : requestList) {
                    process(request);
                }
            }
        },0, period,unit);
        log.info("Already starting dam auto-processing schedule...");
    }
}
