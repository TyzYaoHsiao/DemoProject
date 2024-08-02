package com.demo.error;

import com.demo.constant.MessageConst;
import com.demo.util.MessageUtil;
import lombok.Getter;

@Getter
public class DemoException extends RuntimeException {

    /**
     * 錯誤代碼
     */
    private final String code;
    /**
     * 錯誤訊息
     */
    private final String msg;

    /**
     * 自定義錯誤
     *
     * @param rtnCode 自定義錯誤碼
     */
    public DemoException(MessageConst.RtnCode rtnCode) {
        super(rtnCode.getMsg());
        this.code = rtnCode.getCode();
        this.msg = rtnCode.getMsg();
    }

    /**
     * 自定義錯誤帶錯誤訊息
     *
     * @param rtnCode 自定義錯誤碼
     * @param str     錯誤訊息
     */
    public DemoException(MessageConst.RtnCode rtnCode, Object... str) {
        super(rtnCode.getMsg());
        this.code = rtnCode.getCode();
        this.msg = MessageUtil.messageFormat(rtnCode.getMsg(), str);
    }
}
