package com.hbhs.common.ratelimiter.listener;

import com.alibaba.fastjson.JSONObject;
import com.hbhs.common.ratelimiter.IRateLimiterResponseHandler;
import com.hbhs.common.ratelimiter.RateLimiterRequest;
import com.hbhs.common.ratelimiter.RateLimiterResult;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
class DefaultRateLimiterResponseHandler implements IRateLimiterResponseHandler {

    @Getter@Setter
    private int limitStatusCode = 712;
    @Getter@Setter
    private String limitErrorCode = "712";
    @Getter@Setter
    private String limitMessage = "请求受限制";

    @Override
    public boolean handlerResponse(HttpServletResponse response, RateLimiterRequest limitRequest, RateLimiterResult result) {
        if (RateLimiterResult.ResultStatus.REJECT == result.getResultStatus()){
            //
            try {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(JSONObject.toJSONString(buildLimitResponseMap()));
                response.getWriter().flush();
            }catch (Exception e){
                log.error("Failed to handler response for limit request:{}, response:{}", limitRequest, response);
                log.error("Failed to handler response，message: ",e);
            }
            return Boolean.FALSE;
        }
        return true;
    }

    private Map<String, Object> buildLimitResponseMap(){
        Map<String, Object> limitResponse = new HashMap<>();
        limitResponse.put("statusCode", limitStatusCode);
        limitResponse.put("errorCode", limitErrorCode);
        limitResponse.put("comments", limitMessage);
        return limitResponse;
    }
}
