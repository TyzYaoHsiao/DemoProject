package com.demo.constant;

/**
 * jwt 相關設定
 */
public class JwtConst {

    public static final String JWT_HEADER_NAME = "Authorization";
    public static final String JWT_TOKEN_PREFIX = "Bearer";

    public enum JwtParams {
        IDENTITY, LOGIN_FLAG, SESSION_ID;
    }

}
