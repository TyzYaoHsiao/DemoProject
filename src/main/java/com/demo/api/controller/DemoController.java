package com.demo.api.controller;

import com.demo.api.model.req.BaseReq;
import com.demo.api.model.req.demo.DecryptJwtTokenReq;
import com.demo.api.model.req.demo.FileUploadReq;
import com.demo.api.model.req.demo.GetJwtTokenReq;
import com.demo.api.model.req.sys.AdmUserFileUploadRes;
import com.demo.api.model.res.BaseRes;
import com.demo.api.model.res.demo.DecryptJwtTokenRes;
import com.demo.api.model.res.demo.GetJwtTokenRes;
import com.demo.api.service.DemoService;
import com.demo.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/demo")
public class DemoController extends BaseController {

    @Autowired
    private DemoService demoService;

    @PostMapping("/fileUpload")
    @Operation(summary = "檔案上傳", description = "檔案上傳")
    public BaseRes<AdmUserFileUploadRes> fileUpload(@RequestPart("file") MultipartFile file, @RequestPart("request") BaseReq<FileUploadReq> req) {
        return result(demoService.fileUpload(file, req.getParams()));
    }

    @PostMapping("/getJwtToken")
    @Operation(summary = "取得JWT", description = "取得JWT")
    public BaseRes<GetJwtTokenRes> getJwtToken(@RequestBody @Valid BaseReq<GetJwtTokenReq> req) {
        return result(demoService.getJwtToken(req.getParams()));
    }

    @PostMapping("/decryptJwtToken")
    @Operation(summary = "解密JWT", description = "解密JWT")
    public BaseRes<DecryptJwtTokenRes> decryptJwtToken(@RequestBody @Valid BaseReq<DecryptJwtTokenReq> req) {
        return result(demoService.decryptJwtToken(req.getParams()));
    }
}
