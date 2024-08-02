package com.demo.api.service;

import com.demo.api.model.req.demo.DecryptJwtTokenReq;
import com.demo.api.model.req.demo.FileUploadReq;
import com.demo.api.model.req.demo.GetJwtTokenReq;
import com.demo.api.model.req.sys.AdmUserFileUploadRes;
import com.demo.api.model.res.demo.DecryptJwtTokenRes;
import com.demo.api.model.res.demo.GetJwtTokenRes;
import org.springframework.web.multipart.MultipartFile;

public interface DemoService {

    AdmUserFileUploadRes fileUpload(MultipartFile file, FileUploadReq req);

    GetJwtTokenRes getJwtToken(GetJwtTokenReq req);

    DecryptJwtTokenRes decryptJwtToken(DecryptJwtTokenReq req);
}
