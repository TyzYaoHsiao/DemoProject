package com.ziyao.demo.api.model.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RequestEntity<T> {

    @NotNull
    private String txnSeq;

    @Valid
    private T params;
}
