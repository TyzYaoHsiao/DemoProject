package com.demo.comp.impl;

import com.demo.constant.Const;
import com.demo.domain.UserProfile;
import com.demo.entity.SysExternalApiLog;
import com.demo.repository.SysExternalApiLogRepository;
import com.demo.util.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * 電文共用
 */
public class BaseCompImpl {

    @Autowired
    private UserProfile userProfile;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SysExternalApiLogRepository sysExternalApiLogRepository;

    protected void insertExternalApiLog(Object request, Object response, String msgId) {
        SysExternalApiLog log = SysExternalApiLog.builder()
                .msgId(msgId)
                .txnSeq(userProfile.getTxnSeq())
                .request(getMsgContent(request))
                .response(getMsgContent(response))
                .createTime(DateUtil.getNow())
                .build();
        sysExternalApiLogRepository.save(log);
    }

    /**
     * json 物件 to string
     *
     * @param obj content
     * @return
     */
    private String getMsgContent(Object obj) {
        String msg;
        try {
            msg = objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            msg = obj.toString();
        }

        if (msg.length() > Const.DB_LOG_LIMIT_MSG_LENGTH) {
            msg = StringUtils.substring(msg, 0, Const.DB_LOG_LIMIT_MSG_LENGTH);
        }
        return msg;
    }

    /**
     * http header json
     *
     * @return
     */
    public HttpHeaders getHttpJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * http header form data
     *
     * @return
     */
    public HttpHeaders getHttpMutipartHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
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
