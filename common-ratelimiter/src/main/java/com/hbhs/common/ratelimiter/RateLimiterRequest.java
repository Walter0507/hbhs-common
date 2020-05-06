package com.hbhs.common.ratelimiter;

import lombok.*;

import java.util.Map;

@Getter@Setter@ToString@Builder
@NoArgsConstructor@AllArgsConstructor
public class RateLimiterRequest {
    private String requestUrl;
    private Map<String, String> requestParamMap;
}
