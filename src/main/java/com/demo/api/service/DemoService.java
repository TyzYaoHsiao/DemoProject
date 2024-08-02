package com.demo.api.service;

import com.demo.api.model.req.demo.FileUploadReq;
import org.springframework.web.multipart.MultipartFile;

public interface DemoService {

    Void fileUpload(MultipartFile file, FileUploadReq req);
}
