package com.hbhs.common.error.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorHandlerConfigProperty {
    private String errorCode = "errorCode";
    private String errorMessageKey = "errorMessage";

}
