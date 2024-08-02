package com.demo.aop.aspect;

import com.demo.constant.Const;
import org.apache.commons.lang3.RandomStringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * trace id
 */
@Aspect
@Component
@Order(1)
public class TraceIDAspect {

    /**
     * add log trace id
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "com.demo.aop.pointcut.PointcutDefinition.restLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MDC.put(Const.TRACE_ID_KEY, RandomStringUtils.randomAlphanumeric(Const.TRACE_ID_KEY_LENGTH));
//        try {
            return joinPoint.proceed();
//        } finally {
//            MDC.remove(Const.TRACE_ID_KEY);
//        }
    }

}
