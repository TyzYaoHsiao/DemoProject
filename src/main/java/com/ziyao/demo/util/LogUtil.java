package com.demo.util;

import com.demo.constant.SysConst;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class LogUtil {

    public static final ObjectMapper objectMapper;
    static {
        objectMapper = new ObjectMapper();
    }

    /**
     * FILE LOG 長度處理
     *
     * @param s
     * @return
     */
    public static String fileLog(Object s) {
        return getMsgContent(s, SysConst.FILE_LOG_MAX_LENGTH);
    }

    /**
     * DB LOG 長度處理
     *
     * @param s
     * @return
     */
    public static String dbLog(Object s) {
        return getMsgContent(s, SysConst.DB_LOG_MAX_LENGTH);
    }

    /**
     * 內容長度處理
     *
     * @param obj
     * @return
     */
    private static String getMsgContent(Object obj, int maxLength) {
        if (obj == null) {
            return "";
        }

        try {
            String msg = objectMapper.writeValueAsString(obj);
            if (msg.length() > maxLength) {
                msg = StringUtils.substring(msg, 0, maxLength);
            }
            return msg;
        } catch (Exception e) {
            return "";
        }
    }
}
