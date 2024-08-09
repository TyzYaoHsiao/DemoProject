package com.demo.api.model.res.adm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAdmUserRes {

    private Long id;
    private String userId;
    private String userName;
}
