package com.demo.constant;

import lombok.Getter;

/**
 * 回傳訊息相關設定
 */
public class MessageConst {

    public static final String NO_DATA = "查無資料";
    public static final String SYSTEM_ERROR = "系統錯誤";
    public static final String PARSE_DATE_ERROR = "日期格式錯誤";
    public static final String ACCESS_DENIED = "無權限存取";

    @Getter
    public enum RtnCode {
        // 成功
        SUCCESS("0000"),


        // 查無資料
        NOT_DATA("0404"),
        // 欄位檢核錯誤
        FIELD_ERROR("9997"),
        //日期格式錯誤
        DATE_FORMAT_ERROR("9998"),
        // 系統錯誤
        SYSTEM_ERROR("9999"),

        // 無權限
        FORBIDDEN("403");

        private final String code;

        RtnCode(String code) {
            this.code = code;
        }
    }
}
