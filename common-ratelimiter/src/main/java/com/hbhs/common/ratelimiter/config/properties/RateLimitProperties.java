package com.hbhs.common.ratelimiter.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("rate.limit.config")
@Data
public class RateLimitProperties {
    private String id;
    private String name;
    private List<RateLimitConfig> configList;

    @Data
    public static class RateLimitConfig{

        private String urlPattern;
        private Integer ttl;
        private int limitCount;
        private int threshold;
        private List<String> paramList;
    }
}
