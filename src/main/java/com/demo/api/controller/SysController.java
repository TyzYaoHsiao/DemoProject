package com.demo.api.controller;

import com.demo.api.model.req.RequestEntity;
import com.demo.api.model.req.sys.GetSysApiLogListReq;
import com.demo.api.model.req.sys.GetSysExternalApiLogListReq;
import com.demo.api.model.res.ResponseEntity;
import com.demo.api.model.res.sys.GetSysApiLogListRes;
import com.demo.api.model.res.sys.GetSysExternalApiLogListRes;
import com.demo.api.service.SysService;
import com.demo.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/sys")
public class SysController extends BaseController {

    @Autowired
    private SysService sysService;

    @PostMapping("/getSysApiLogList")
    @Operation(summary = "查詢系統API LOG", description = "查詢系統API LOG")
    public ResponseEntity<GetSysApiLogListRes> getSysApiLogList(@RequestBody @Valid RequestEntity<GetSysApiLogListReq> req) {
        return success(sysService.getSysApiLogList(req.getParams()));
    }

    @PostMapping("/getSysExternalApiLogList")
    @Operation(summary = "查詢呼叫外部系統API LOG", description = "查詢呼叫外部系統API LOG")
    public ResponseEntity<GetSysExternalApiLogListRes> getSysExternalApiLogList(@RequestBody @Valid RequestEntity<GetSysExternalApiLogListReq> req) {
        return success(sysService.getSysExternalApiLogList(req.getParams()));
    }
}
