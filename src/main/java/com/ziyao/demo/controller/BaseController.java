package com.ziyao.demo.controller;

import com.ziyao.demo.api.model.res.ResponseEntity;
import com.ziyao.demo.constant.MessageConst;

public class BaseController {

    /**
     * success response
     *
     * @param result
     * @return
     */
    public <T> ResponseEntity<T> success(T result) {
        MessageConst.RtnCode rtnCode = MessageConst.RtnCode.SUCCESS;
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setCode(rtnCode.getCode());
        responseEntity.setMsg(rtnCode.name());
        responseEntity.setResult(result);
        return responseEntity;
    }
}
