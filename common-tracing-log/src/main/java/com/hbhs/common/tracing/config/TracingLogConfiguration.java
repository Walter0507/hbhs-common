package com.hbhs.common.tracing.config;


import com.hbhs.common.tracing.log.http.HttpTracingLogFilter;
import org.springframework.context.annotation.Bean;

public class TracingLogConfiguration {

    @Bean
    public HttpTracingLogFilter httpTracingLogFilter(){
        return new HttpTracingLogFilter();
    }
}
