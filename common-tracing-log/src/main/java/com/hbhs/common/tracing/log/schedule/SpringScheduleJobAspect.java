package com.hbhs.common.tracing.log.schedule;

import com.hbhs.common.tracing.TracingLogUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.scheduling.annotation.Scheduled;

@Aspect
public class SpringScheduleJobAspect {

    @Around(value = "@annotation(scheduled)")
    public Object schedule(ProceedingJoinPoint point, Scheduled scheduled) throws Throwable{
        TracingLogUtils.addTracingUuidAndHost();
        return point.proceed();
    }
}
