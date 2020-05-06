package com.hbhs.common.dam.data.local;

import com.hbhs.common.dam.DamRequest;
import com.hbhs.common.dam.data.DamDataStorage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class LocalDamDataStorage<R extends DamRequest> implements DamDataStorage<R> {
    private Lock standbyLock = new ReentrantLock();
    private Lock executedLock = new ReentrantLock();

    private Map<String, R> standbyRequestMap = new HashMap<>();
    private Set<String> executedIdSet = new HashSet<>();
    private List<TempData> requestDataList = new ArrayList<>();

    @Override
    public boolean addRequestToStandbyQueueIfNeed(R request) {
        try {
            executedLock.lock();
            standbyLock.lock();
            String id = request.uniqueId();
            if (executedIdSet.contains(id)){
                standbyRequestMap.put(id, request);
                return true;
            }
        }finally {
            executedLock.unlock();
            standbyLock.unlock();
        }

        return false;
    }

    @Override
    public boolean addRequestToExecutedQueue(R request, long expireMillis) {
        try {
            executedLock.lock();

            executedIdSet.add(request.uniqueId());
            requestDataList.add(new TempData(request.uniqueId(), System.currentTimeMillis()+expireMillis));
        }finally {
            executedLock.unlock();
        }

        return true;
    }

    @Override
    public List<R> getExecutableRequest() {
        List<R> result = new ArrayList<>();
        try {
            executedLock.lock();
            standbyLock.lock();

            Iterator<TempData> iterator = requestDataList.iterator();
            while (iterator.hasNext()){
                TempData data = iterator.next();
                if (data.expiredTime>System.currentTimeMillis()){
                    break;
                }
                if (standbyRequestMap.containsKey(data.id)){
                    result.add(standbyRequestMap.get(data.id));
                    standbyRequestMap.remove(data.id);
                }
                iterator.remove();
                executedIdSet.remove(data.id);
            }
        }finally {
            executedLock.unlock();
            standbyLock.unlock();
        }
        return result;
    }

    @lombok.Data
    @NoArgsConstructor@AllArgsConstructor
    private static class TempData{
        private String id;
        private long expiredTime;
    }
}
