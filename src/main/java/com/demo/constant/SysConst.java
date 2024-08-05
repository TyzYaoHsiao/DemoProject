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

    public static List<String> SWAGGER_LIST = new ArrayList<>();
    static {
        SWAGGER_LIST.add("/demo/swagger/*");
        SWAGGER_LIST.add("/demo/swagger-ui/*");
        SWAGGER_LIST.add("/demo/v3/api-docs");
        SWAGGER_LIST.add("/demo/v3/api-docs/*");
    }

    public static List<String> HEALTHY_LIST = new ArrayList<>();
    static {
        HEALTHY_LIST.add("/demo/healthyCheck");
        HEALTHY_LIST.add("/demo/version");
    }

    public static final List<String> WHITE_LIST = new ArrayList<>();
    static {
        WHITE_LIST.addAll(HEALTHY_LIST);
        WHITE_LIST.addAll(SWAGGER_LIST);
        WHITE_LIST.add("/demo/adm/addAdmUser");
//        WHITE_LIST.add("/**");
    }
}