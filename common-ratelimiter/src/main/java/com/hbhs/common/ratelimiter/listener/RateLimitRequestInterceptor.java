package com.hbhs.common.ratelimiter.listener;

import com.hbhs.common.ratelimiter.IRateLimiterResponseHandler;
import com.hbhs.common.ratelimiter.RateLimiter;
import com.hbhs.common.ratelimiter.RateLimiterRequest;
import com.hbhs.common.ratelimiter.RateLimiterResult;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RateLimitRequestInterceptor extends HandlerInterceptorAdapter {

    @Getter@Setter
    private RateLimiter rateLimiter;
    @Getter@Setter
    private IRateLimiterResponseHandler responseHandler;
    private IRateLimiterResponseHandler defaultHandler = new DefaultRateLimiterResponseHandler();

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        RateLimiterRequest limitRequest = buildRateLimiterRequest(request);

        RateLimiterResult result = rateLimiter.rateLimit(limitRequest);
        if (responseHandler !=null){
            return responseHandler.handlerResponse(response,limitRequest,result);
        }
        return defaultHandler.handlerResponse(response,limitRequest, result);
    }

    private RateLimiterRequest buildRateLimiterRequest(HttpServletRequest request){
        RateLimiterRequest limitRequest = new RateLimiterRequest();
        limitRequest.setRequestUrl(request.getRequestURI());
        limitRequest.setRequestParamMap(buildParam(request));
        return limitRequest;
    }

    private Map<String, String> buildParam(HttpServletRequest request){
        Map<String,String> paramMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            paramMap.put(entry.getKey(),entry.getValue()[0]);
        }
        return paramMap;
    }

}
