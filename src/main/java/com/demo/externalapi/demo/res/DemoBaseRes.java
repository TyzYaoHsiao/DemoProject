package com.demo.externalapi.demo.res;

import lombok.Getter;

@Getter
public class DemoBaseRes<T> {

    private String code;
    private String msg;
    private T result;
}
