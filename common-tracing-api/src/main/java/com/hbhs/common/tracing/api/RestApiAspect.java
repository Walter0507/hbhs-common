package com.hbhs.common.tracing.api;

import com.hbhs.common.tracing.api.annotation.RestApi;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;

@Aspect
@Slf4j
public class RestApiAspect extends AbstractApi {

    @Around(value = "@annotation(restApi)")
    public Object restJoinPoint(ProceedingJoinPoint joinPoint, RestApi restApi) throws Throwable {

        // 调用参数
        Object[] arguments = joinPoint.getArgs();

        // API ID
        String apiId = getApiId(restApi.id(), joinPoint);

        // 添加日志标识
        setRequestLogMark(apiId,true);

        // 认证授权检查
        authCheck(joinPoint, arguments, restApi.authorized(), apiId);

        // 接口调用开始记录
        recordStartCall(joinPoint, arguments, restApi.statistic(), apiId);

        // 记录请求日志
        logRequest(apiId, arguments, restApi.logLevel());

        // 调用接口
        Object response = callApi(joinPoint);

        // 记录响应日志
        logResponse(apiId, response, restApi.logLevel());

        // 接口调用结束记录
        recordEndCall(joinPoint, arguments, restApi.statistic(), apiId);

        clearRequestLogMark();
        return response;
    }

    @Override
    protected Logger getLogger() {
        return log;
    }
}
