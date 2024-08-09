package com.ziyao.demo.aop.aspect;

import com.ziyao.demo.api.model.req.RequestEntity;
import com.ziyao.demo.domain.UserProfile;
import com.ziyao.demo.util.HttpContextUtil;
import com.ziyao.demo.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 權限驗證
 */
@Slf4j
@Aspect
@Component
@Order(value = 3)
@RequiredArgsConstructor
public class AuthAspect {

    private final UserProfile userProfile;

    /**
     * 攔截點
     *
     * @param joinPoint
     */
    @Before(value = "com.ziyao.demo.aop.pointcut.PointcutDefinition.restLayer()")
    public void authValid(JoinPoint joinPoint) {
        HttpServletRequest httpServletRequest = HttpContextUtil.getHttpServletRequest();
        if (RequestUtil.isSkip()) {
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
                    userProfile.setClientIp(RequestUtil.getIpAddr(httpServletRequest));
                }
            }
        }
    }
}
