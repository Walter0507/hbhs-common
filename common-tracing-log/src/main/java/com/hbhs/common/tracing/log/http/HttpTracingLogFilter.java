package com.hbhs.common.tracing.log.http;

import com.hbhs.common.tracing.TracingLogConst;
import com.hbhs.common.tracing.TracingLogUtils;
import com.hbhs.common.tools.id.mongodb.ObjectId;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Order(value = 1)
public class HttpTracingLogFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        TracingLogUtils.addTracingUuidAndHost();

        String globalTraceId = request.getHeader(TracingLogConst.LOG_TRACE_FIELD_GLOBAL_ID);
        if (StringUtils.isEmpty(globalTraceId)){
            globalTraceId = ObjectId.get().toString();
        }
        // global trace id
        MDC.put(TracingLogConst.LOG_TRACE_FIELD_GLOBAL_ID, globalTraceId);
        // source id
        MDC.put(TracingLogConst.LOG_TRACE_FIELD_SOURCE_ID, request.getHeader(TracingLogConst.LOG_TRACE_FIELD_SOURCE_ID));

        // remote address
        MDC.put(TracingLogConst.LOG_TRACE_FIELD_REMOTE_ADDRESS, request.getRemoteAddr());
    }

}
