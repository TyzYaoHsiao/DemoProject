package com.demo.controller;

import com.demo.api.model.res.BaseRes;
import com.demo.constant.MessageConst;

public class BaseController {

    /**
     * success response
     *
     * @param result
     * @return
     */
    public <T> BaseRes<T> result(T result) {
        MessageConst.RtnCode rtnCode = MessageConst.RtnCode.SUCCESS;
        BaseRes<T> baseRes = new BaseRes<>();
        baseRes.setCode(rtnCode.getCode());
        baseRes.setMsg(rtnCode.name());
        baseRes.setResult(result);
        return baseRes;
    }
}
