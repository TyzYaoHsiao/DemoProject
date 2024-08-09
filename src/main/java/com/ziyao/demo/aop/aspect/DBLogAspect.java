package com.demo.aop.aspect;

import com.demo.api.model.req.RequestEntity;
import com.demo.domain.UserProfile;
import com.demo.entity.SysApiLog;
import com.demo.repository.SysApiLogRepository;
import com.demo.util.LogUtil;
import com.demo.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 記錄 log
 */
@Slf4j
@Aspect
@Component
@Order(value = 4)
@RequiredArgsConstructor
public class DBLogAspect {

    private final UserProfile userProfile;
    private final SysApiLogRepository sysApiLogRepository;

    @Around(value = "com.demo.aop.pointcut.PointcutDefinition.restLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (RequestUtil.isSkip()) {
            return joinPoint.proceed();
        }

        boolean hasException = false;
        Exception exception = null;
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            log.error("LogAspect hasException : {}", e.getMessage());
            hasException = true;
            exception = e;
        }

        saveSysApiLog(joinPoint, result, hasException, exception);

        if (hasException) {
            throw exception;
        }

        return result;
    }

    private void saveSysApiLog(ProceedingJoinPoint joinPoint, Object result, boolean hasException, Exception exception) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();

        Object[] args = joinPoint.getArgs();
        String params = null;

        if (args != null) {
            for (Object object : args) {
                if (object instanceof RequestEntity requestEntity) {
                    try {
                        params = LogUtil.fileLog(requestEntity);
                    } catch (Exception e) {
                        params = Arrays.toString(args);
                    }
                }
            }
        }

        SysApiLog sysApiLog = new SysApiLog();
        sysApiLog.setUserId(userProfile.getUserId());
        sysApiLog.setTxnSeq(userProfile.getTxnSeq());
        sysApiLog.setParams(params);
        sysApiLog.setResult(LogUtil.dbLog(result));
        sysApiLog.setMethod(className + "." + methodName + "()");
        sysApiLog.setCreateTime(LocalDateTime.now());

        if (hasException) {
            sysApiLog.setErrorMsg(LogUtil.dbLog(exception.getMessage()));
        }
        sysApiLogRepository.save(sysApiLog);
    }

    private static void reqExclude(Map<String, Object> map) {
        List<String> excludeKeyList = Arrays.asList("imageData");
        removeOrSetEmptyInMap(map, excludeKeyList);
    }

    private static void respExclude(Map<String, Object> map) {
        List<String> excludeKeyList = Arrays.asList("dataFile");
        removeOrSetEmptyInMap(map, excludeKeyList);
    }

    /**
     * 排除特定資料
     *
     * @param input
     * @param excludeKeyList
     */
    private static void removeOrSetEmptyInMap(Map<String, Object> input, List<String> excludeKeyList) {

        for (String key : input.keySet()) {
            if (excludeKeyList.contains(key)) {
                if (input.get(key) != null && StringUtils.isNotBlank(String.valueOf(input.get(key)))){
                    input.put(key, "此欄位已被過濾");
                }
            } else {
                Object value = input.get(key);
                if (value instanceof Map) {
                    removeOrSetEmptyInMap((Map<String, Object>) value, excludeKeyList);
                } else if (value instanceof List) {
                    removeOrSetEmptyInList((List<Map<String, Object>>) value, excludeKeyList);
                }
            }
        }
    }

    private static void removeOrSetEmptyInList(List<Map<String, Object>> input, List<String> excludeKeyList) {
        if (CollectionUtils.isNotEmpty(input)) {
            for (Map<String, Object> map : input) {
                removeOrSetEmptyInMap(map, excludeKeyList);
            }
        }
    }
}
