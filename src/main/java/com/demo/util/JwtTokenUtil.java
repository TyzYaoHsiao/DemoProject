package com.demo.util;

import com.demo.constant.JwtConst;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt util
 *
 * @author jason
 */
public class JwtTokenUtil {

    /**
     * 建立 jwt token
     *
     * @param jwtParams
     * @param secret
     * @return
     */
    public static String createToken(Map<String, Object> jwtParams, String secret) {
        return Jwts.builder()
                .setClaims(jwtParams)
                .signWith(SignatureAlgorithm.HS256, Base64.decodeBase64(secret))
                .compact();
    }

    /**
     * 取得 jwt token 內容
     *
     * @param token
     * @param secret
     * @return
     */
    public static Jws<Claims> claimsParam(String token, String secret) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token);
    }

    /**
     * 建立 jwt 內容
     *
     * @param identity
     * @param loginFlag
     * @return
     */
    public static Map<String, Object> createJwtParams(Object identity, String sessionId, boolean loginFlag) {
        Map<String, Object> jwtParams = new HashMap<>();
        jwtParams.put(JwtConst.JwtParams.IDENTITY.name(), identity);
        jwtParams.put(JwtConst.JwtParams.SESSION_ID.name(), sessionId);
        jwtParams.put(JwtConst.JwtParams.LOGIN_FLAG.name(), loginFlag);
        return jwtParams;
    }

}
