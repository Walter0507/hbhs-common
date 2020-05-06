package com.hbhs.common.tracing;

import com.hbhs.common.tools.id.mongodb.ObjectId;
import org.slf4j.MDC;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TracingLogUtils {
    private static String HOST_NAME;

    static {
        try {
            HOST_NAME = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
        }
    }
    public static void addTracingUuidAndHost(){
        MDC.put(TracingLogConst.LOG_TRACE_FIELD_UUID, ObjectId.get().toString());
        MDC.put(TracingLogConst.LOG_TRACE_FIELD_HOST_NAME, HOST_NAME);
    }

}
