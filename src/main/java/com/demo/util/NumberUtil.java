package com.demo.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Slf4j
public class NumberUtil {

    public static final String BASIC_FORMAT = "#.00";

    public static String formatNumberToStr(BigDecimal number, String format) {
        DecimalFormat df = new DecimalFormat(format);
        df.setRoundingMode(RoundingMode.DOWN);

        if(number == null) {
            return df.format(0);
        }

        return df.format(number);
    }

}
