package com.hbhs.common.ratelimiter;

import com.hbhs.common.ratelimiter.rule.RateLimiterRule;

public interface RateLimiter {

    void addRateLimitRule(RateLimiterRule rule);

    RateLimiterResult rateLimit(RateLimiterRequest request);

    void shutdown();
}
