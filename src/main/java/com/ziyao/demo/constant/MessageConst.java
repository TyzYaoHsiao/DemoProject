package com.ziyao.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 回傳訊息相關設定
 */
public class MessageConst {

    @Getter
    @AllArgsConstructor
    public enum RtnCode {

        FORBIDDEN         ("403",  "無權限存取"),
        NOT_FOUND         ("404",  "查無資源「{0}」"),
        METHOD_NOT_ALLOWED("405",  "不支援「{0}」方法"),

        SUCCESS           ("0000", "執行成功"),
        DATA_NOT_FOUND    ("0001", "查無資料"),
        FIELD_ERROR       ("9997", "欄位檢核錯誤 : {0}"),
        DATE_FORMAT_ERROR ("9998", "日期格式錯誤 : {0}"),
        SYSTEM_ERROR      ("9999", "系統錯誤"),

        DEMO_API_ERROR    ("9999", "Demo API 連線失敗")
        ;

        private final String code;
        private final String msg;
    }
}
