package com.hbhs.common.ratelimiter.enums;

import lombok.Getter;

public enum RateLimiterTypeEnums {
    /**
     * 普通限流
     */
    RATE_NORMAL("RATE_NORMAL","普通限流"),
    /**
     * 用户限流
     */
    RATE_USER("RATE_USER","用户级限流")
    ;
    @Getter
    private String name;
    @Getter
    private String description;
    RateLimiterTypeEnums(String name, String description){
        this.name = name;
        this.description = description;
    }
}
