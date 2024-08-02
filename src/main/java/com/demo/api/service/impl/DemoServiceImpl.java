package com.demo.api.service.impl;

import com.demo.api.model.req.demo.FileUploadReq;
import com.demo.api.service.DemoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private ObjectMapper objectMapper;

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
