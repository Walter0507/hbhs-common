package com.hbhs.common.ratelimiter.rule;

import lombok.*;

import java.util.List;

@Getter@Setter@ToString@Builder
@NoArgsConstructor@AllArgsConstructor
public class RateLimiterRule {
    private String id;
    private String limitUrl;
    private List<RateLimiterStrategy> strategyList;
}
