package com.hbhs.common.tracing.api;

import com.hbhs.common.tracing.api.annotation.RpcApi;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;


/**
 * RPC接口切面：认证授权、接口统计、入参校验、入参补全、异常捕获、日志输出等。
 *
 */
@Aspect
@Slf4j
public class RpcApiAspect extends AbstractApi {

    @Around(value = "@annotation(rpcApi)")
    public Object rpcJoinPoint(ProceedingJoinPoint joinPoint, RpcApi rpcApi) throws Throwable {

        // 调用参数
        Object[] arguments = joinPoint.getArgs();

        // API ID
        String apiId = getApiId(rpcApi.id(), joinPoint);

        // 添加日志标识
        setRequestLogMark(apiId, false);

        // 认证授权检查
        authCheck(joinPoint, arguments, rpcApi.authorized(), apiId);

        // 接口调用开始记录
        recordStartCall(joinPoint, arguments, rpcApi.statistic(), apiId);

        // Facade采用单参数
        Object request = getFirstArgument(arguments);

        // 记录请求日志
        logRequest(apiId, arguments, rpcApi.logLevel());

        // 入参检查
        Object errorResponse = inputCheck(request);
        if (errorResponse != null) {
            errorResponse = convertResponse(errorResponse, joinPoint);
            logResponse(apiId, errorResponse, rpcApi.logLevel());
            return errorResponse;
        }

        // 入参补全
        fillRequest(request);

        // 调用接口
        Object response = callApi(joinPoint);

        response = convertResponse(response, joinPoint);

        // 记录响应日志
        logResponse(apiId, response, rpcApi.logLevel());

        // 接口调用结束记录
        recordEndCall(joinPoint, arguments, rpcApi.statistic(), apiId);

        return response;
    }

    @Override
    protected Logger getLogger() {
        return log;
    }
}