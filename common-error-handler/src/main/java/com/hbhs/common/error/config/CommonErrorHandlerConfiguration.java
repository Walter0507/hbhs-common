package com.hbhs.common.error.config;

import com.hbhs.common.error.handler.CommonErrorHandler;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ErrorHandlerConfigProperty.class)
public class CommonErrorHandlerConfiguration {
    private static final String ERROR_CODE_KEY = "hbhs.error.handler.key.errorCode";
    private static final String ERROR_MESSAGE_KEY = "hbhs.error.handler.key.errorMessage";

    @Autowired
    private ErrorHandlerConfigProperty errorHandlerConfigProperty;

    @Bean
    public CommonErrorHandler commonErrorHandler(ErrorHandlerConfigProperty errorHandlerConfigProperty){
        CommonErrorHandler handler = new CommonErrorHandler();
        handler.setErrorCodeKey(errorHandlerConfigProperty.getErrorCode());
        handler.setErrorMessageKey(errorHandlerConfigProperty.getErrorMessageKey());
        return handler;
    }
}
