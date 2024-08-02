package com.demo.error;

import com.demo.constant.MessageConst;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

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
    public CustomException(MessageConst.RtnCode rtnCode) {
        this.code = rtnCode.getCode();
        this.msg = "";
    }

    /**
     * 自定義錯誤帶錯誤訊息
     *
     * @param rtnCode   自定義錯誤碼
     * @param customMsg 錯誤訊息
     */
    public CustomException(MessageConst.RtnCode rtnCode, String customMsg) {
        super(customMsg);
        this.code = rtnCode.getCode();
        this.msg = customMsg;
    }
}
