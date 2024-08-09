package com.ziyao.demo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * date or datetime format tool
 */
public class DateUtil {

    public static final String BASIC_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public static final String API_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String API_DATE_FORMAT = "yyyy-MM-dd";
    public static final String YYMMDD = "yyMMdd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String YYYY_MM_DD = "yyyy/MM/dd";
    public static final String PUSH_MSG_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final ZoneId TW_ZONE = ZoneId.of("Asia/Taipei");

    /**
     * get now LocalDateTime
     *
     * @return
     */
    public static LocalDateTime getNow() {
        return LocalDateTime.now();
    }

    /**
     * dateTime format to string
     *
     * @param dateTime
     * @param format
     * @return
     */
    public static String formatDateToStr(LocalDateTime dateTime, String format) {
        if (dateTime == null) return "";
        return DateTimeFormatter.ofPattern(format).format(dateTime);
    }

    /**
     * date format to string
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDateToStr(LocalDate date, String format) {
        if (date == null) return "";
        return DateTimeFormatter.ofPattern(format).format(date);
    }

    /**
     * string parse to date
     *
     * @param date
     * @param format
     * @return
     */
    public static LocalDate parseStrToDate(String date, String format) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
    }

    /**
     * string parse to dateTime
     *
     * @param date
     * @param format
     * @return
     */
    public static LocalDateTime parseStrToDateTime(String date, String format) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(format));
    }
}
