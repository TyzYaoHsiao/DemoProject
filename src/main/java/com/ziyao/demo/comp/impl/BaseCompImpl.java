package com.ziyao.demo.comp.impl;

import com.ziyao.demo.domain.UserProfile;
import com.ziyao.demo.entity.SysExternalApiLog;
import com.ziyao.demo.repository.SysExternalApiLogRepository;
import com.ziyao.demo.util.DateUtil;
import com.ziyao.demo.util.LogUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 電文共用
 */
public class BaseCompImpl {

    @Autowired
    protected UserProfile userProfile;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SysExternalApiLogRepository sysExternalApiLogRepository;

    protected String getUrl(String key) {
        return "";
    }

    /**
     * 寫入 LOG
     *
     * @param msgId
     * @param req      上行
     * @param res      下行
     * @param costTime 花費時間
     * @param errorMsg 錯誤訊息
     */
    protected void insertLog(String msgId, Object req, Object res, Long costTime, String errorMsg) {
        SysExternalApiLog log = SysExternalApiLog.builder()
                .txnSeq(userProfile.getTxnSeq())
                .msgId(msgId)
                .params(LogUtil.dbLog(req))
                .result(LogUtil.dbLog(res))
                .errorMsg(errorMsg)
                .costTime(costTime)
                .createTime(DateUtil.getNow())
                .build();
        sysExternalApiLogRepository.save(log);
    }

    /**
     * http header json
     *
     * @return
     */
    public HttpHeaders getHttpJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * 泛型處理
     */
    public static class MyParameterizedTypeImpl implements ParameterizedType {
        private final ParameterizedType delegate;
        private final Type[] actualTypeArguments;

        public MyParameterizedTypeImpl(ParameterizedType delegate, Type[] actualTypeArguments) {
            this.delegate = delegate;
            this.actualTypeArguments = actualTypeArguments;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return actualTypeArguments;
        }

        @Override
        public Type getRawType() {
            return delegate.getRawType();
        }

        @Override
        public Type getOwnerType() {
            return delegate.getOwnerType();
        }
    }

}
