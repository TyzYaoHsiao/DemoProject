package com.ziyao.demo.externalapi.demo.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@NotBlank
public class DemoReq {

    private String userId;
}
