package com.demo.api.model.req.adm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Schema(description = "取得使用者請求")
public class GetAdmUserReq {

    @NotBlank
    @Schema(description = "使用者ID", required = true)
    private String userId;
}
