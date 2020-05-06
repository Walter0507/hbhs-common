package com.hbhs.common.domain.model.response;

import com.hbhs.common.domain.model.paging.Pagination;

import java.util.List;

public class TResultBuilder {

    public static BaseResult failResult(String errorCode, String errorMessage){
        BaseResult result = new BaseResult();
        result.setStatusCode(BaseResult.SYS_ERROR_CODE);
        result.setErrorCode(errorCode);
        result.setComments(errorMessage);
        return result;
    }

    public static <T> TSingleResult<T> failTSingle(int statusCode, String errorMessage){
        TSingleResult<T> result = new TSingleResult();
        result.setStatusCode(statusCode);
        result.setErrorCode(statusCode+"");
        result.setComments(errorMessage);
        return result;
    }

    public static <T> TMultiResult<T> failTMulti(int statusCode, String errorMessage){
        TMultiResult<T> result = new TMultiResult();
        result.setStatusCode(statusCode);
        result.setErrorCode(statusCode+"");
        result.setComments(errorMessage);
        return result;
    }
    public static <T> TPageResult<T> failTPage(int statusCode, String errorMessage){
        TPageResult<T> result = new TPageResult();
        result.setStatusCode(statusCode);
        result.setErrorCode(statusCode+"");
        result.setComments(errorMessage);
        return result;
    }

    public static <T> TSingleResult<T> succSingle(T value) {
        TSingleResult<T> result = new TSingleResult<>();
        result.setData(value);
        result.setStatusCode(BaseResult.SUCCESS_CODE);
        result.setComments(BaseResult.SUCCESS_MESSAGE);
        return result;
    }

    public static <T> TMultiResult<T> succMulti(List<T> values) {
        TMultiResult<T> result = new TMultiResult();
        result.setData(values);
        result.setStatusCode(BaseResult.SUCCESS_CODE);
        result.setComments(BaseResult.SUCCESS_MESSAGE);
        return result;
    }

    public static <T> TPageResult<T> succPageResult(Class<T> clazz, Pagination pagination) {
        TPageResult<T> result = new TPageResult<>();
        result.setStatusCode(BaseResult.SUCCESS_CODE);
        result.setComments(BaseResult.SUCCESS_MESSAGE);
        result.setPagination(pagination);
        return result;
    }

    public static <T> TPageResult<T> succPageResult(Class<T> clz, int pageNo, int pageSize, int totalCount) {
        return succPageResult(clz, new Pagination(pageNo,pageSize,totalCount));
    }


}
