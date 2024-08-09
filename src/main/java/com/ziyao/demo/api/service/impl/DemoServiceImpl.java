package com.ziyao.demo.api.service.impl;

import com.ziyao.demo.api.model.req.demo.FileUploadReq;
import com.ziyao.demo.api.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public Void fileUpload(MultipartFile file, FileUploadReq req) {

        try {
            log.info(file.getOriginalFilename());
        } catch (Exception e) {
            log.error("AdmServiceImpl fileUpload error : {}", e);
        }
        return null;
    }
}
