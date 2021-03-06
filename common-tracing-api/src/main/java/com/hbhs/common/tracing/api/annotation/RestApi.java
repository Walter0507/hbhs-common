package com.hbhs.common.tracing.api.annotation;

import com.hbhs.common.tracing.api.LogLevelEnums;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 面向UI的REST接口注解
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface RestApi {

    /**
     * API的ID，默认使用类名+方法名
     */
    String id() default "";

    /**
     * 接口版本号
     */
    String version() default "v1";

    /**
     * API描述
     */
    String desc() default "";

    /**
     * 是否需要认证授权
     */
    boolean authorized() default false;

    /**
     * 是否要做统计
     *
     * @return 默认做统计
     */
    boolean statistic() default true;

    /**
     * 输入输出日志级别，可选级别：DEBUG、INFO
     *
     * @return 日志级别
     */
    LogLevelEnums logLevel() default LogLevelEnums.INFO;
}