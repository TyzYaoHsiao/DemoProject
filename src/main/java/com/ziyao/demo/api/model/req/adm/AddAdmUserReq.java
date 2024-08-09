package com.demo.api.model.req.adm;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
@Schema(description = "新增使用者請求")
public class AddAdmUserReq {

    @NotBlank
    @Schema(description = "使用者ID", required = true, example = "admin")
    private String userId;

    @NotBlank
    @Schema(description = "使用者名稱", required = true, example = "admin")
    private String userName;
}
