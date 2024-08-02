package com.demo.api.model.req.demo;

import com.demo.domain.JwtToken;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetJwtTokenReq extends JwtToken {

    @Schema(description = "使用者ID", required = true, example = "admin")
    private String userId;
}
