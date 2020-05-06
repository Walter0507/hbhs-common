package com.hbhs.common.error;

public interface IErrorTemplate {

    public static final int SYSTEM_ERROR_CODE = 500;

    int errorCode();

    String fullErrorCode();

    String errorMessage();
}
