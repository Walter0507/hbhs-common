package com.hbhs.common.ratelimiter.config;

import com.hbhs.common.ratelimiter.CommonRateLimiter;
import com.hbhs.common.ratelimiter.IRateLimiterResponseHandler;
import com.hbhs.common.ratelimiter.RateLimiter;
import com.hbhs.common.ratelimiter.config.properties.RateLimitProperties;
import com.hbhs.common.ratelimiter.data.LocalCacheCalculator;
import com.hbhs.common.ratelimiter.enums.RateLimiterTypeEnums;
import com.hbhs.common.ratelimiter.listener.RateLimitRequestInterceptor;
import com.hbhs.common.ratelimiter.rule.RateLimiterRule;
import com.hbhs.common.ratelimiter.rule.RateLimiterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.util.Collections;

@Configuration
@EnableConfigurationProperties(RateLimitProperties.class)
public class RateLimiterAutoConfiguration {

    @Autowired
    private RateLimitProperties rateLimitProperties;

    @Bean
    public RateLimiter rateLimiter(){
        CommonRateLimiter limiter = new CommonRateLimiter();
        limiter.setCalculator(new LocalCacheCalculator(1000L,1024));
        for (RateLimitProperties.RateLimitConfig config : rateLimitProperties.getConfigList()) {
            RateLimiterRule rule = new RateLimiterRule();
            rule.setId("");
            rule.setLimitUrl(config.getUrlPattern());

            RateLimiterStrategy strategy = new RateLimiterStrategy();
            strategy.setLimiterType(RateLimiterTypeEnums.RATE_NORMAL);
            strategy.setThreshold(config.getThreshold());
            strategy.setLimitCount(config.getLimitCount());
            strategy.setTtl(config.getTtl());
            strategy.setParamList(config.getParamList());
            rule.setStrategyList(Collections.singletonList(strategy));

            limiter.addRateLimitRule(rule);
        }

        return limiter;
    }

    @Bean
    public RateLimitRequestInterceptor rateLimitRequestInterceptor(RateLimiter rateLimiter,
                                                                   @Autowired(required = false)IRateLimiterResponseHandler responseHandler){
        RateLimitRequestInterceptor interceptor = new RateLimitRequestInterceptor();
        interceptor.setRateLimiter(rateLimiter);
        interceptor.setResponseHandler(responseHandler);
        return interceptor;
    }
    

}
