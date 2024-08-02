package com.demo.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 系統參數相關設定
 */
public class SysConst {

    public static final String TRACE_ID_KEY = "traceId";
    public static final String SYSTEM = "system";
    public static final int TRACE_ID_KEY_LENGTH = 8;
    public static final int DB_LOG_MAX_LENGTH = 3000;
    public static final int FILE_LOG_MAX_LENGTH = 10000;
    public static final String TOKEN_HEADER_NAME = "token";

    public static final List<String> WHITE_LIST = new ArrayList<>();
    static {
        WHITE_LIST.add("/healthyCheck");
        WHITE_LIST.add("/swagger-ui/*");
        WHITE_LIST.add("/v3/api-docs");
        WHITE_LIST.add("/v3/api-docs/*");
        WHITE_LIST.add("/adm/addAdmUser");
//        WHITE_LIST.add("/**");
    }
}