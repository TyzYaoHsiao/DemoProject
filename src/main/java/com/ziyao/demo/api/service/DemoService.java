package com.ziyao.demo.api.service;

import com.ziyao.demo.api.model.req.demo.FileUploadReq;
import org.springframework.web.multipart.MultipartFile;

public interface DemoService {

    Void fileUpload(MultipartFile file, FileUploadReq req);
}
