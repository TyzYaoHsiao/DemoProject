package com.demo.api.controller;

import com.demo.api.model.req.RequestEntity;
import com.demo.api.model.req.demo.FileUploadReq;
import com.demo.api.model.res.ResponseEntity;
import com.demo.api.service.DemoService;
import com.demo.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController extends BaseController {

    private final DemoService demoService;

    @PostMapping("/fileUpload")
    @Operation(summary = "檔案上傳", description = "檔案上傳")
    public ResponseEntity<Void> fileUpload(@RequestPart("file") MultipartFile file, @RequestPart("request") RequestEntity<FileUploadReq> req) {
        return success(demoService.fileUpload(file, req.getParams()));
    }
}
