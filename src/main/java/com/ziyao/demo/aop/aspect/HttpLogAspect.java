package com.ziyao.demo.aop.aspect;

import com.ziyao.demo.api.model.req.RequestEntity;
import com.ziyao.demo.util.HttpContextUtil;
import com.ziyao.demo.util.LogUtil;
import com.ziyao.demo.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;

/**
 * http log aspect
 */
@Slf4j
@Aspect
@Component
@Order(value = 2)
public class HttpLogAspect {

    @Around(value = "com.ziyao.demo.aop.pointcut.PointcutDefinition.restLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        long beginTimeMillis = System.currentTimeMillis();
        Object result = new HashMap<>();

        try {
            result = joinPoint.proceed();
        } finally {
            HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
            Object[] args = joinPoint.getArgs();
            String argsStr = null;

            if (args != null) {
                for (Object object : args) {
                    if (object instanceof RequestEntity requestEntity) {
                        try {
                            argsStr = LogUtil.fileLog(requestEntity);
                        } catch (Exception e) {
                            argsStr = Arrays.toString(args);
                        }
                    }
                }
            }

            log.info("===========================Start=================================================");
            if (request != null) {
                log.info("URL            : {}", request.getRequestURI());
                log.info("HTTP Method    : {}", request.getMethod());
                log.info("IP             : {}", RequestUtil.getIpAddr(request));
            }

            log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            log.info("Request Args   : {}", argsStr);
            log.info("Result         : {}", LogUtil.fileLog(result));
            log.info("costTime       : {}", System.currentTimeMillis() - beginTimeMillis);
            log.info("===========================End==================================================");
        }

        return result;
    }
}