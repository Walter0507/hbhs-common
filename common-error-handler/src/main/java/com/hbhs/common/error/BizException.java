package com.hbhs.common.error;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class BizException extends RuntimeException {
    @Getter
    private List<IErrorTemplate> templateList = new ArrayList<>();
    @Getter
    private int errorCode;
    @Getter
    private String errorMessage;

    public BizException(IErrorTemplate errorTemplate) {
        super(errorTemplate.errorMessage());
        templateList.add(errorTemplate);
        this.errorCode = errorTemplate.errorCode();
        this.errorMessage = errorTemplate.errorMessage();
    }

    public BizException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public BizException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = IErrorTemplate.SYSTEM_ERROR_CODE;
        this.errorMessage = message;
    }

    public String errorTrace(){
        StringBuilder str = new StringBuilder();
        str.append("errorCode: ").append(errorCode)
                .append(", errorMessage: ").append(errorMessage);
        if (templateList.size()>0){
            str.append(", sourcing trace as follow: \n");
            for (IErrorTemplate template : templateList) {
                str.append(" |-- ").append(template.fullErrorCode())
                        .append("(")
                        .append(template.errorCode())
                        .append(", ").append(template.errorMessage())
                        .append(")\n");
            }
        }

        return str.toString();
    }
}
