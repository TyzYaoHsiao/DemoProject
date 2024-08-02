package com.demo.aop.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.demo.constant.Const;
import com.demo.util.HttpContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * http log
 */
@Slf4j
@Aspect
@Component
@Order(value = 2)
public class HttpLogAspect {

    @Autowired
    private ObjectMapper objectMapper;

    @Around(value = "com.demo.aop.pointcut.PointcutDefinition.restLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        long beginTimeMillis = System.currentTimeMillis();

        Object[] args = joinPoint.getArgs();
        String argsStr;
        try {
            argsStr = objectMapper.writeValueAsString(args);
        } catch (Exception e) {
            argsStr = Arrays.toString(args);
        }

        Map<String, Object> outputParamMap = new HashMap<>();

        Object result;
        try {
            result = joinPoint.proceed();
            outputParamMap.put("result", result);
        } finally {
            try {
                long endTimeMillis = System.currentTimeMillis(); // 記錄方法執行完成的時間
                long costTime = endTimeMillis - beginTimeMillis;

                String response = objectMapper.writeValueAsString(outputParamMap);
                if (response.length() > Const.FILE_LOG_LIMIT_MSG_LENGTH) {
                    response = response.substring(0, Const.FILE_LOG_LIMIT_MSG_LENGTH);
                }

                log.info("=======Start=======");
                if (request != null) {
                    log.info("URL            : {}", request.getRequestURI());
                    log.info("HTTP Method    : {}", request.getMethod());
                    log.info("IP             : {}", request.getRemoteHost());
                }

                log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
                log.info("Request Args   : {}", argsStr);
                log.info("Result         : {}", response);
                log.info("costTime       : {}", costTime);
                log.info("=======End=========" + System.lineSeparator());
            } catch (Exception e) {
                //不處理
            }
        }

        return result;
    }
}
