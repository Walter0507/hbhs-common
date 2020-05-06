package com.hbhs.common.dam.data.redis;

import com.hbhs.common.dam.DamRequest;
import com.hbhs.common.dam.data.DamDataStorage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RedisDamDataStorage<R extends DamRequest> implements DamDataStorage<R> {
    private static final String STAND_BY_QUEUE = "";

    @Override
    public boolean addRequestToStandbyQueueIfNeed(R request) {
        return false;
    }

    @Override
    public boolean addRequestToExecutedQueue(R request, long expireMillis) {
        return false;
    }

    @Override
    public List<R> getExecutableRequest() {
        return null;
    }
}
