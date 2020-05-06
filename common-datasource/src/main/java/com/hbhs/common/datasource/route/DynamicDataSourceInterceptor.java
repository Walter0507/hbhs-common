package com.hbhs.common.datasource.route;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;

@Aspect
@Slf4j
public class DynamicDataSourceInterceptor implements Ordered {
    @Getter@Setter
    private int order = 1;
    @Override
    public int getOrder() {
        return 0;
    }

    @Around(value="@annotation(readOnlyConnection) ", argNames = "readOnlyConnection")
    public Object around(ProceedingJoinPoint pjp, ReadOnlyConnection readOnlyConnection) throws Throwable {
        try {
            DynamicDataSourceHolder.setDbType(DynamicDataSourceHolder.SLAVE);
            return pjp.proceed();
        }finally {
            DynamicDataSourceHolder.clearDbType();
        }
    }
}
