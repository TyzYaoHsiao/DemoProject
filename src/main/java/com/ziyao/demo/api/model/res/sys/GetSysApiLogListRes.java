package com.demo.api.model.res.sys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetSysApiLogListRes {

    private List<SysApiLog> sysApiLogList;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SysApiLog {

        private Long id;
        private String txnSeq;
        private String userId;
        private String method;
        private String params;
        private String result;
        private String errorMsg;
        private String createTime;
    }
}
