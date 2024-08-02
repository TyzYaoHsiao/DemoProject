package com.demo.aop.aspect;

import com.demo.api.model.req.RequestEntity;
import com.demo.domain.UserProfile;
import com.demo.util.RequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 權限驗證
 */
@Slf4j
@Aspect
@Component
@Order(value = 3)
public class AuthAspect {

    @Value("${jwt.token.secret:ziyaotest123}")
    private String JWT_TOKEN_SECRET;

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
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if (StringUtils.startsWith(httpServletRequest.getRequestURI(), "/swagger")
                || StringUtils.endsWith(httpServletRequest.getRequestURI(), "/v3/api-docs")) {
            return;
        }

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
