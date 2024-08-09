package com.ziyao.demo.util;

public class MessageUtil {

    /**
     * 字串轉換 {0} {1}... 轉換
     *
     * @param pattern
     * @param arguments
     * @return
     */
    public static String messageFormat(String pattern, Object... arguments) {

        String result = pattern;
        if (arguments != null) {
            int i = 0;
            for (Object obj : arguments) {
                String replaceStr = "";
                if (obj != null) {
                    replaceStr = obj.toString();
                }
                result = result.replace("{"+ i +"}", replaceStr);
                i++;
            }
        }

        return result;
    }
}
