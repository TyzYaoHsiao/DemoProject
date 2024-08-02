package com.demo.externalapi.demo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NotBlank
public class DemoBaseReq<T> {

    private String txnSeq;
    private T params;
}
