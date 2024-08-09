package com.ziyao.demo.constant;

import java.util.ArrayList;
import java.util.List;

public class ApiConst {

    public static final String TOKEN_HEADER_NAME = "token";

    public static List<String> IGNORE_LIST = new ArrayList<>();
    static {
        IGNORE_LIST.add("/demo/swagger/*");
        IGNORE_LIST.add("/demo/swagger-ui/*");
        IGNORE_LIST.add("/demo/v3/api-docs");
        IGNORE_LIST.add("/demo/v3/api-docs/*");
        IGNORE_LIST.add("/demo/healthyCheck");
        IGNORE_LIST.add("/demo/version");
    }

    public static final List<String> WHITE_LIST = new ArrayList<>();
    static {
        WHITE_LIST.addAll(IGNORE_LIST);
        WHITE_LIST.add("/demo/adm/addAdmUser");
        WHITE_LIST.add("/**"); // TODO 白名單
    }
}
