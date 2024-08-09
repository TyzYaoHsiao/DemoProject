package com.ziyao.demo.externalapi.demo.core;

import com.ziyao.demo.comp.DemoComp;
import com.ziyao.demo.comp.impl.BaseCompImpl;
import com.ziyao.demo.constant.DemoConst;
import com.ziyao.demo.constant.MessageConst;
import com.ziyao.demo.error.DemoException;
import com.ziyao.demo.externalapi.demo.req.DemoBaseReq;
import com.ziyao.demo.externalapi.demo.req.DemoReq;
import com.ziyao.demo.externalapi.demo.res.DemoBaseRes;
import com.ziyao.demo.externalapi.demo.res.DemoRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Slf4j
@Component
public class DemoCompImpl extends BaseCompImpl implements DemoComp {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public DemoRes demo(DemoReq req) {
        return sendRequest(req, DemoConst.MsgId.GetAdmUser, DemoRes.class);
    }

    public <T> T sendRequest(Object req, DemoConst.MsgId msgId, Class<T> valueType) {
        DemoBaseRes<T> response = null;
        long beginTimeMillis = System.currentTimeMillis();
        String errorMsg = null;

        DemoBaseReq<Object> request = DemoBaseReq.builder()
                .txnSeq(userProfile.getTxnSeq())
                .params(req)
                .build();

        try {
            response = restTemplate.exchange("http://127.0.0.1:8080/demo" + msgId.getUrl(), msgId.getHttpMethod(),
                    new HttpEntity<>(request, getHttpJsonHeader()),
                    new ParameterizedTypeReference<DemoBaseRes<T>>() {
                        public Type getType() {
                            return new MyParameterizedTypeImpl((ParameterizedType) super.getType(), new Type[]{valueType});
                        }
                    }).getBody();
        } catch (Exception e) {
            errorMsg = e.getMessage();
            throw new DemoException(MessageConst.RtnCode.DEMO_API_ERROR);
        } finally {
            long costTime = System.currentTimeMillis() - beginTimeMillis;
            insertLog(msgId.name(), request, response, costTime, errorMsg);
        }
        return response.getResult();
    }
}
