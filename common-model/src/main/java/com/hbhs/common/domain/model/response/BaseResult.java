package com.hbhs.common.domain.model.response;

import com.hbhs.common.domain.model.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString(callSuper = true)
public class BaseResult implements Response {
    public static final int SUCCESS_CODE = 200;
    public static final String SUCCESS_MESSAGE = "成功";
    public static final int SYS_ERROR_CODE = 500;
    public static final String SYS_ERROR_MESSAGE = "系统错误";

    private int statusCode;
    private String errorCode;
    private String comments;

}
