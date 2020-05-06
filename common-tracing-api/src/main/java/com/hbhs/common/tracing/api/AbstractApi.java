package com.hbhs.common.tracing.api;

import com.hbhs.common.domain.model.Request;
import com.hbhs.common.domain.model.response.BaseResult;
import com.hbhs.common.tracing.TracingLogConst;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
public abstract class AbstractApi {

    @Autowired
    private HttpServletRequest request;

    /**
     * API调用
     */
    protected Object callApi(ProceedingJoinPoint joinPoint) throws Throwable {
        Object response = null;
        try {
            response = joinPoint.proceed();
        } catch (Exception e) {
            getLogger().error(e.getMessage(), e);
//            response = exceptionHandlerChain.handleException(e);
//            response = convertResponse(response,joinPoint);
        }
        return response;
    }

    /**
     * 入参检查
     */
    protected Object inputCheck(Object request) {
        if (request instanceof Request) {
            try {
                ((Request) request).checkParam();
            } catch (Exception e) {
                getLogger().debug(e.getMessage(), e);
//                return exceptionHandlerChain.handleException(e);
            }
        }
        return null;
    }


    /**
     * 将返回结果类型转换成方法声明的类型
     */
    protected Object convertResponse(Object response, ProceedingJoinPoint joinPoint) {
        Class clazz = response.getClass();
        Class returnClazz = ((MethodSignature) joinPoint.getSignature()).getMethod().getReturnType();

        if (clazz.getName().equals(returnClazz.getName())) {
            return response;
        }

        Object targetResponse;
        try {
            targetResponse = returnClazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            getLogger().error(e.getMessage(), e);
            return response;
        }

        if (targetResponse instanceof BaseResult && response instanceof BaseResult) {
            BaseResult result = (BaseResult) targetResponse;
            BaseResult errorResult = (BaseResult) response;

            result.setComments(errorResult.getComments());
            result.setErrorCode(errorResult.getErrorCode());
            result.setStatusCode(errorResult.getStatusCode());
            return result;
        }

        return response;
    }

    /**
     * TODO: 入参补全
     */
    protected void fillRequest(Object request) {

    }

    /**
     * TODO: 调用开始统计
     */
    protected void recordStartCall(ProceedingJoinPoint joinPoint, Object[] arguments, boolean statistic, String apiId) {

    }

    /**
     * TODO: 调用结束统计
     */
    protected void recordEndCall(ProceedingJoinPoint joinPoint, Object[] arguments, boolean statistic, String apiId) {

    }

    /**
     * TODO: 认证授权
     */
    protected boolean authCheck(ProceedingJoinPoint joinPoint, Object[] arguments,
                                boolean authorized,  String apiId) {
        // 无需授权直接返回
        if (!authorized) {
            return true;
        }

        return true;
    }

    /**
     * 请求参数写入日志
     */
    protected void logRequest(String apiId, Object[] arguments, LogLevelEnums logLevel) {

        if (getLogger().isInfoEnabled() && LogLevelEnums.INFO == logLevel) {
            getLogger().info("ApiId: {}, Request: {}", apiId, Arrays.asList(arguments));
        }

        if (getLogger().isDebugEnabled() && LogLevelEnums.DEBUG == logLevel) {
            getLogger().debug("ApiId: {}, Request: {}", apiId, Arrays.asList(arguments));
        }
    }

    /**
     * 响应结果写入日志
     */
    protected void logResponse(String apiId, Object response, LogLevelEnums logLevel) {

        if (getLogger().isInfoEnabled() && LogLevelEnums.INFO == logLevel) {
            getLogger().info("ApiId: {}, Response: {}", apiId, response);
        }

        if (getLogger().isDebugEnabled() && LogLevelEnums.DEBUG == logLevel) {
            getLogger().debug("ApiId: {}, Response: {}", apiId, response);
        }
    }

    protected String getApiId(String id, JoinPoint joinPoint) {

        if (StringUtils.isEmpty(id)) {
            // 默认为：类名+方法名
            id = joinPoint.getSignature() == null ? "" : joinPoint.getSignature().toString();
        }
        return id;
    }

    /**
     * 取第一个参数，通常RPC接口为单参数命令式
     */
    protected Object getFirstArgument(Object[] arguments) {
        if (arguments != null && arguments.length > 0) {
            return arguments[0];
        }
        return null;
    }

    /**
     * 设置请求的日志标识参数
     * @param apiId  请求接口ID
     */
    protected void setRequestLogMark(String apiId,boolean restApi){

        // StringUtils.isEmpty(MDC.get("restApiId"))==true 表示直接访问的RpcApi，同样需要设置标示
        if( restApi || StringUtils.isEmpty(MDC.get("restApiId"))){
            MDC.put("apiId",apiId);
//            MDC.put("uuid", CommonUtil.generateOID());
            MDC.put("hostname", TracingLogConst.HOST_NAME);
            MDC.put("restApiId",apiId);
            String remoteAddr = Optional.ofNullable(request).map(HttpServletRequest::getRemoteAddr).orElse("");
            MDC.put("remoteAddr",remoteAddr);
        }
    }

    /**
     * 清除请求日志标识参数.
     * 注意，只需要在RestApiAspect末尾调用，清除动作是为 避免对直接调用RpcApi带来的干扰。
     * RpcApiAspect则不需要调用此方法，在新的请求进来时会被覆盖。如果调用会影响到通过RestApi的记录
     */
    protected void clearRequestLogMark(){
        MDC.remove("apiId");
        MDC.remove("restApiId");
    }

    protected abstract Logger getLogger();
}
