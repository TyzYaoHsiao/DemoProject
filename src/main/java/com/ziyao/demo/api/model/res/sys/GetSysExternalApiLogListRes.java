package com.ziyao.demo.api.model.res.sys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetSysExternalApiLogListRes {

    private List<SysExternalApiLog> sysExternalApiLogList;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SysExternalApiLog {

        private Long id;
        private String txnSeq;
        private String msgId;
        private String params;
        private String result;
        private String errorMsg;
        private Long costTime;
        private String createTime;
    }
}
