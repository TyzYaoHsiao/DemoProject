package com.demo.api.controller;

import com.demo.api.model.req.RequestEntity;
import com.demo.api.model.req.adm.AddAdmUserReq;
import com.demo.api.model.req.adm.GetAdmUserReq;
import com.demo.api.model.req.demo.GetAdmUserExcelReq;
import com.demo.api.model.res.ResponseEntity;
import com.demo.api.model.res.adm.GetAdmUserRes;
import com.demo.api.service.AdmService;
import com.demo.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/adm")
@RequiredArgsConstructor
public class AdmController extends BaseController {

    private final AdmService admService;

    @PostMapping("/addAdmUser")
    @Operation(summary = "新增使用者", description = "新增使用者")
    public ResponseEntity<Void> addAdmUser(@RequestBody @Valid RequestEntity<AddAdmUserReq> req) {
        return success(admService.addAdmUser(req.getParams()));
    }

    @PostMapping("/getAdmUser")
    @Operation(summary = "取得使用者", description = "取得使用者")
    public ResponseEntity<GetAdmUserRes> getAdmUser(@RequestBody @Valid RequestEntity<GetAdmUserReq> req) {
        return success(admService.getAdmUser(req.getParams()));
    }

    @PostMapping("/getAdmUser2")
    @Operation(summary = "取得使用者-外部", description = "取得使用者-外部")
    public ResponseEntity<GetAdmUserRes> getAdmUser2(@RequestBody @Valid RequestEntity<GetAdmUserReq> req) {
        return success(admService.getAdmUser2(req.getParams()));
    }

    @PostMapping("/getAdmUserExcel")
    @Operation(summary = "取得使用者資料-Excel", description = "取得使用者資料-Excel")
    public void getAdmUserExcel(@RequestBody @Valid RequestEntity<GetAdmUserExcelReq> req) {
        admService.getAdmUserExcel(req.getParams());
    }
}
