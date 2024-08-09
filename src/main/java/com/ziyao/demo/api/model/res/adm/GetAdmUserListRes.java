package com.ziyao.demo.api.model.res.adm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAdmUserListRes {

    private List<AdmUser> admUserList;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdmUser {

        private Long id;
        private String userId;
        private String userName;
    }
}
