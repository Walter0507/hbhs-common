package com.hbhs.common.ratelimiter;

import com.hbhs.common.ratelimiter.rule.RateLimiterStrategy;
import lombok.*;

import java.util.List;

@Getter@Setter@ToString@Builder
@NoArgsConstructor@AllArgsConstructor
public class RateLimiterResult {
    private ResultStatus resultStatus;
    private RateLimiterStrategy rejectStrategy;
    private List<RateLimiterStrategy> warnStrategyList;


    public enum ResultStatus{
        NO_LIMIT,
        PASS,
        REJECT,
        WARN

    }
}
