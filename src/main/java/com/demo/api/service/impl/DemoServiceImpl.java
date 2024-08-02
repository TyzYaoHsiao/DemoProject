package com.demo.api.service.impl;

import com.demo.api.model.req.demo.DecryptJwtTokenReq;
import com.demo.api.model.req.demo.FileUploadReq;
import com.demo.api.model.req.demo.GetJwtTokenReq;
import com.demo.api.model.req.sys.AdmUserFileUploadRes;
import com.demo.api.model.res.demo.DecryptJwtTokenRes;
import com.demo.api.model.res.demo.GetJwtTokenRes;
import com.demo.api.service.DemoService;
import com.demo.constant.JwtConst;
import com.demo.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${jwt.token.secret:ziyaotest123}")
    private String JWT_TOKEN_SECRET;

    @Override
    public AdmUserFileUploadRes fileUpload(MultipartFile file, FileUploadReq req) {

        try {
            log.info(file.getOriginalFilename());
        } catch (Exception e) {
            log.error("AdmServiceImpl fileUpload error : " + e);
        }
        return new AdmUserFileUploadRes();
    }

    @Override
    public GetJwtTokenRes getJwtToken(GetJwtTokenReq req) {

        String jwtToken = JwtTokenUtil.createToken(JwtTokenUtil.createJwtParams(req, null, false), JWT_TOKEN_SECRET);
        return GetJwtTokenRes.builder()
                .jwtToken(jwtToken)
                .build();
    }

    @Override
    public DecryptJwtTokenRes decryptJwtToken(DecryptJwtTokenReq req) {

        Jws<Claims> jws = JwtTokenUtil.claimsParam(req.getJwtToken(), JWT_TOKEN_SECRET);
//        String sessionId = jws.getBody().get(JwtConst.JwtParams.SESSION_ID.name(), String.class);
        return objectMapper.convertValue(jws.getBody().get(JwtConst.JwtParams.IDENTITY.name()), DecryptJwtTokenRes.class);
    }
}
