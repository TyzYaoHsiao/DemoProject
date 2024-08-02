package com.demo.constant;

import lombok.Getter;

public class ApiConst {

    /**
     * log 訊息類別
     */
    @Getter
    public enum MsgType {
        /**
         * 請求
         */
        REQ("1"),
        /**
         * 回應
         */
        RES("2");

        private final String code;

        MsgType(String code) {
            this.code = code;
        }
    }
}
