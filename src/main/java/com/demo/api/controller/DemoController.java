package com.demo.api.controller;

import com.demo.api.model.req.RequestEntity;
import com.demo.api.model.req.demo.FileUploadReq;
import com.demo.api.model.res.ResponseEntity;
import com.demo.api.service.DemoService;
import com.demo.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequestMapping("/demo")
public class DemoController extends BaseController {

    @Autowired
    private DemoService demoService;

    @PostMapping("/fileUpload")
    @Operation(summary = "檔案上傳", description = "檔案上傳")
    public ResponseEntity<Void> fileUpload(@RequestPart("file") MultipartFile file, @RequestPart("request") RequestEntity<FileUploadReq> req) {
        return success(demoService.fileUpload(file, req.getParams()));
    }
}
