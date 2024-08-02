package com.demo.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;


@Slf4j
public class RequestUtils {

    public static final String UNKNOWN = "unknown";

    /**
     * 取得ip位置
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(ip, UNKNOWN)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(ip, UNKNOWN)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(ip, UNKNOWN)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(ip, UNKNOWN)) {
            ip = request.getRemoteAddr();
        }

        return getTrueIp(ip);
    }

    /**
     * 有可能把代理服务器的ip一起帶過來 ex: 192.168.1.110,192.168.1.120,192.168.1.130
     *
     * @param ip
     * @return
     */
    public static String getTrueIp(String ip) {

        if (StringUtils.isEmpty(ip)) return ip;

        String trueIp = ip;
        int idx = ip.indexOf(',');

        if (idx > -1) {
            trueIp = StringUtils.substring(trueIp, 0, idx);
        }

        if (trueIp.length() > 40) {
            log.error("RequestUtils getTrueIp length deviant : " + trueIp);
            trueIp = StringUtils.substring(trueIp, 0, 40);
        }

        return trueIp;
    }

}
