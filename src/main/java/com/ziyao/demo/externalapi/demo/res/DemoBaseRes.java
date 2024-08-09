package com.ziyao.demo.externalapi.demo.res;

import lombok.Getter;

@Getter
public class DemoBaseRes<T> {

    private String code;
    private String msg;
    private T result;
}
