package com.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthyCheckController {

    @GetMapping("/healthyCheck")
    public String healthyCheck() {
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
