package com.hbhs.common.datasource.route;

public class DynamicDataSourceHolder {
    public static final String MASTER = "master";
    public static final String SLAVE = "slave";

    /**定义全局ThreadLocal对象，不同线程使用的是同一个对象。**/
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setDbType(String dbTypeName){
        DynamicDataSourceHolder.threadLocal.set(dbTypeName);
    }

    public static String getDbType(){
        return DynamicDataSourceHolder.threadLocal.get();
    }

    public static void clearDbType(){
        DynamicDataSourceHolder.threadLocal.remove();
    }

    public static boolean accessSlave(){
        return SLAVE.equalsIgnoreCase(threadLocal.get());
    }
}
