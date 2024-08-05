package com.demo.aop.aspect;

import com.demo.constant.SysConst;
import com.demo.util.HttpContextUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class HttpLogAspect {

    private final ObjectMapper objectMapper;

    @Around(value = "com.demo.aop.pointcut.PointcutDefinition.restLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        long beginTimeMillis = System.currentTimeMillis();
        Object result = new HashMap<>();

        try {
            result = joinPoint.proceed();
        } finally {
            HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
            String response = objectMapper.writeValueAsString(result);
            if (response.length() > SysConst.FILE_LOG_MAX_LENGTH) {
                response = response.substring(0, SysConst.FILE_LOG_MAX_LENGTH);
            }

            Object[] args = joinPoint.getArgs();
            String argsStr;
            try {
                argsStr = objectMapper.writeValueAsString(args);
            } catch (Exception e) {
                argsStr = Arrays.toString(args);
            }

            log.info("===========================Start=================================================");
            if (request != null) {
                log.info("URL            : {}", request.getRequestURI());
                log.info("HTTP Method    : {}", request.getMethod());
                log.info("IP             : {}", request.getRemoteHost());
            }

            log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            log.info("Request Args   : {}", argsStr);
            log.info("Result         : {}", response);
            log.info("costTime       : {}", System.currentTimeMillis() - beginTimeMillis);
            log.info("===========================End==================================================");
        }

        return result;
    }
}