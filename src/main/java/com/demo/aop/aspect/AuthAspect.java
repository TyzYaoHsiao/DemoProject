package com.demo.aop.aspect;

import com.demo.api.model.req.RequestEntity;
import com.demo.domain.UserProfile;
import com.demo.util.HttpContextUtil;
import com.demo.util.RequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 權限驗證
 */
@Slf4j
@Aspect
@Component
@Order(value = 3)
public class AuthAspect {

    @Autowired
    private UserProfile userProfile;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 攔截點
     *
     * @param joinPoint
     */
    @Before(value = "com.demo.aop.pointcut.PointcutDefinition.restLayer()")
    public void authValid(JoinPoint joinPoint) {
        HttpServletRequest httpServletRequest = HttpContextUtil.getHttpServletRequest();
        if (StringUtils.startsWith(httpServletRequest.getRequestURI(), "/swagger")
                || StringUtils.endsWith(httpServletRequest.getRequestURI(), "/v3/api-docs")) {
            return;
        }

        // TODO FOR DEMO
        userProfile.setUserId("demo");
        apiInfo(joinPoint, httpServletRequest);
    }

    /**
     * api 資訊
     *
     * @param joinPoint
     */
    private void apiInfo(JoinPoint joinPoint, HttpServletRequest httpServletRequest) {

        Object[] args = joinPoint.getArgs();
        if (args != null) {
            for (Object object : args) {
                if (object instanceof RequestEntity requestEntity) {
                    userProfile.setTxnSeq(requestEntity.getTxnSeq());
                    userProfile.setClientIp(RequestUtils.getIpAddr(httpServletRequest));
                }
            }
        }
    }
}
