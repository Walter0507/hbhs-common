package com.hbhs.common.ratelimiter;

import javax.servlet.http.HttpServletResponse;

public interface IRateLimiterResponseHandler {

    boolean handlerResponse(HttpServletResponse response, RateLimiterRequest limitRequest, RateLimiterResult result);

}
