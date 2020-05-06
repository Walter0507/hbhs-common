package com.hbhs.common.tracing;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TracingLogConst {
    public static String HOST_NAME;
    /**
     * 全局traceId
     */
    public static String LOG_TRACE_FIELD_GLOBAL_ID = "globalId";

    public static String LOG_TRACE_FIELD_UUID = "uuid";
    public static String LOG_TRACE_FIELD_SOURCE_ID = "sourceId";

    public static String LOG_TRACE_FIELD_HOST_NAME = "hostName";
    public static String LOG_TRACE_FIELD_REMOTE_ADDRESS = "removeAddr";


    static {
        try {
            HOST_NAME = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
        }
    }

}
