package com.hbhs.common.tracing.log.rpc;

import com.hbhs.common.tracing.TracingLogConst;
import feign.RequestTemplate;
import org.slf4j.MDC;

public class FeignLogTracingInterceptor implements feign.RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // global trace id
        template.header(TracingLogConst.LOG_TRACE_FIELD_GLOBAL_ID, MDC.get(TracingLogConst.LOG_TRACE_FIELD_GLOBAL_ID));
        // source id: 当前的uuid作为 sourceId
        template.header(TracingLogConst.LOG_TRACE_FIELD_SOURCE_ID, MDC.get(TracingLogConst.LOG_TRACE_FIELD_UUID));
    }
}
