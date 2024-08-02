package com.demo.api.service.impl;

import com.demo.api.model.req.adm.AddAdmUserReq;
import com.demo.api.model.req.adm.GetAdmUserReq;
import com.demo.api.model.res.adm.AddAdmUserRes;
import com.demo.api.model.res.adm.GetAdmUserRes;
import com.demo.api.service.AdmService;
import com.demo.comp.DemoComp;
import com.demo.constant.MessageConst;
import com.demo.domain.UserProfile;
import com.demo.entity.AdmUser;
import com.demo.error.CustomException;
import com.demo.externalapi.demo.req.DemoReq;
import com.demo.externalapi.demo.res.DemoRes;
import com.demo.repository.AdmUserRepository;
import com.demo.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AdmServiceImpl implements AdmService {

    @Autowired
    private UserProfile userProfile;

    @Autowired
    private DemoComp demoComp;

    @Autowired
    private AdmUserRepository admUserRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AddAdmUserRes addAdmUser(AddAdmUserReq req) {

        AdmUser admUser = AdmUser.builder()
                .userId(req.getUserId())
                .userName(req.getUserName())
                .createTime(DateUtil.getNow())
                .createBy(userProfile.getUserId())
                .build();
        admUserRepository.save(admUser);
        return new AddAdmUserRes();
    }

    @Override
    public GetAdmUserRes getAdmUser(GetAdmUserReq req) {

        AdmUser admUser = admUserRepository.findByUserId(req.getUserId());
        if (admUser == null) {
            throw new CustomException(MessageConst.RtnCode.NOT_DATA, MessageConst.NO_DATA);
        }

        return GetAdmUserRes.builder()
                .id(admUser.getId())
                .userId(admUser.getUserId())
                .userName(admUser.getUserName())
                .build();
    }

    @Override
    public GetAdmUserRes getAdmUser2(GetAdmUserReq req) {

        DemoReq demoReq = DemoReq.builder()
                .userId(req.getUserId())
                .build();
        DemoRes demoRes = demoComp.demo(demoReq);

        if (demoRes == null) {
            throw new CustomException(MessageConst.RtnCode.NOT_DATA, MessageConst.NO_DATA);
        }

        return GetAdmUserRes.builder()
                .id(demoRes.getId())
                .userId(demoRes.getUserId())
                .userName(demoRes.getUserName())
                .build();
    }
}
