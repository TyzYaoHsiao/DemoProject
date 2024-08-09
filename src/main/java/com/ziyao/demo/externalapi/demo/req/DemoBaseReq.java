package com.ziyao.demo.externalapi.demo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DemoBaseReq<T> {

    private String txnSeq;
    private T params;
}
