package com.hbhs.common.dam.data;

import com.hbhs.common.dam.DamRequest;

import java.util.List;

public interface DamDataStorage<R extends DamRequest> {

    boolean addRequestToStandbyQueueIfNeed(R request);

    boolean addRequestToExecutedQueue(R request, long expireMillis);

    List<R> getExecutableRequest();

}
