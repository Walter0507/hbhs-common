package com.hbhs.common.ratelimiter.handler;

import com.hbhs.common.ratelimiter.RateLimiterRequest;
import com.hbhs.common.ratelimiter.rule.RateLimiterStrategy;

import java.util.List;

public interface ILimiterHandler {

    void handlerLimitRequest(RateLimiterRequest request, RateLimiterStrategy rejectStrategy,
                             List<RateLimiterStrategy> warningStrategyList);
}
