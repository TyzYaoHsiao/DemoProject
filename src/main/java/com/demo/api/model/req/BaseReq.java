package com.demo.api.model.req;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
public class BaseReq<T> {

    @NotNull
    private String txnSeq;

    @Valid
    private T params;
}
