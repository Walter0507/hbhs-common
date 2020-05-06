package com.hbhs.common.ratelimiter.rule;

import com.hbhs.common.ratelimiter.enums.RateLimiterTypeEnums;
import lombok.*;

import java.util.List;

@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
public class RateLimiterStrategy {
    private RateLimiterTypeEnums limiterType;
    private List<String> paramList;
    private int ttl;
    private int limitCount;
    private int threshold;
}
