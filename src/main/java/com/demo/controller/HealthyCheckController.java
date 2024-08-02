package com.demo.controller;

import com.demo.dto.TestDto;
import com.demo.util.SQLUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HealthyCheckController {

    private final SQLUtil sqlUtil;

    @GetMapping("/healthyCheck")
    public String healthyCheck() {
        return "OK";
    }

    @GetMapping("/test")
    public String test() {

        String sqlStr = "SELECT " +
                " '123' as TEST_INT, " +
                " '123' as TEST_SHORT, " +
                " '123' as TEST_LONG, " +
                " '123' as TEST_FLOAT, " +
                " '123' as TEST_DOUBLE, " +
                " '123' as TEST_NUMBER, " +
                " '123' as TEST_DECIMAL, " +
                " '123' as TEST_BIGDECIMAL, " +
                " '123' as TEST_STRING " +
                " FROM ADM_USER a ";

        List<TestDto> dtoList = sqlUtil.findList(sqlStr, TestDto.class);
        if (CollectionUtils.isNotEmpty(dtoList)) {
            for (TestDto testDto : dtoList) {
                log.info("testInt           = " + testDto.getTestInt());
                log.info("testShort         = " + testDto.getTestShort());
                log.info("testLong          = " + testDto.getTestLong());
                log.info("testFloat         = " + testDto.getTestFloat());
                log.info("testDouble        = " + testDto.getTestDouble());
                log.info("testNumber        = " + testDto.getTestNumber());
                log.info("testDecimal       = " + testDto.getTestDecimal());
                log.info("testBigDecimal    = " + testDto.getTestBigDecimal());
                log.info("testBoolean       = " + testDto.getTestBoolean());
                log.info("testString        = " + testDto.getTestString());
                log.info("testTimestamp     = " + testDto.getTestTimestamp());
                log.info("testSqlDate       = " + testDto.getTestSqlDate());
                log.info("testJavaDate      = " + testDto.getTestJavaDate());
                log.info("testLocalDate     = " + testDto.getTestLocalDate());
                log.info("testLocalDateTime = " + testDto.getTestLocalDateTime());
            }
        }

        return "OK";
    }



    public static void main(String[] args) {


    }

//    public static String turnListToString(List<String> list, boolean addSingleQuote) {
//
//        if (CollectionUtils.isEmpty(list)) {
//            return null;
//        }
//
//        List<String> stringList = list.stream()
//                .filter(StringUtils::isNotBlank)
//                .filter(x -> StringUtils.isNotBlank(x))
//                .map(x -> addSingleQuote ? "'" + x + "'" : x)
//                .toList();
//
//        return String.join(",", stringList);
//    }
//
//    public static String turnListToString(List<?> list, String fieldName, boolean addSingleQuote) {
//
//        if (CollectionUtils.isEmpty(list)) {
//            return null;
//        }
//
//        List<String> stringList = list.stream()
//                .filter(Objects::nonNull)
//                .map(x -> {
//                    String propertyValue = "";
//                    if (StringUtils.isBlank(propertyValue)) {
//                        return null;
//                    }
//                    return addSingleQuote ? "'" + propertyValue + "'" : propertyValue;
//                })
//                .filter(StringUtils::isNotBlank)
//                .toList();
//
//        return String.join(",", stringList);
//    }
}
