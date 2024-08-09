package com.ziyao.demo.api.model.req.adm;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
@Schema(description = "取得使用者請求")
public class GetAdmUserReq {

    @NotBlank
    @Schema(description = "使用者ID", required = true)
    private String userId;
}
