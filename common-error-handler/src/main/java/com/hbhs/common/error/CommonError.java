package com.hbhs.common.error;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommonError implements IErrorTemplate {

    private int errorCode;
    private String errorMessage;

    @Override
    public String fullErrorCode() {
        return String.valueOf(errorCode);
    }

    @Override
    public int errorCode() {
        return errorCode;
    }

    @Override
    public String errorMessage() {
        return errorMessage;
    }
}
