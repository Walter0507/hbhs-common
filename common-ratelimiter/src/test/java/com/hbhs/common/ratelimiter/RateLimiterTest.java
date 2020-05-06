package com.hbhs.common.ratelimiter;


import com.hbhs.common.ratelimiter.data.LocalCacheCalculator;
import com.hbhs.common.ratelimiter.enums.RateLimiterTypeEnums;
import com.hbhs.common.ratelimiter.rule.RateLimiterRule;
import com.hbhs.common.ratelimiter.rule.RateLimiterStrategy;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class RateLimiterTest {
    public static void main(String[] args) throws Exception{
        CommonRateLimiter rateLimiter = new CommonRateLimiter();
        rateLimiter.setCalculator(new LocalCacheCalculator(1000L, 1024));
        RateLimiterRule rule = new RateLimiterRule();
        rule.setId("100000001");
        rule.setLimitUrl("/public/v1/test/aaa");

        RateLimiterStrategy strategy = new RateLimiterStrategy();
        strategy.setTtl(10000);
        strategy.setLimiterType(RateLimiterTypeEnums.RATE_NORMAL);
        strategy.setLimitCount(6);
        strategy.setThreshold(8);
        rule.setStrategyList(Collections.singletonList(strategy));
        rateLimiter.addRateLimitRule(rule);

        for (int i = 0; i < 100; i++) {
            RateLimiterRequest request = new RateLimiterRequest();
            request.setRequestUrl("/public/v1/test/aaa");
            RateLimiterResult result = rateLimiter.rateLimit(request);
            System.out.println(dateStr()+" - 第 "+i+" 请求："+request.getRequestUrl()+", 限流结果:"+result.getResultStatus());
            Thread.sleep(1000);
        }
    }

    private static String dateStr(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss").format(new Date());
    }
}