package com.demo.api.service.impl;

import com.demo.api.model.req.adm.AddAdmUserReq;
import com.demo.api.model.req.adm.GetAdmUserReq;
import com.demo.api.model.req.demo.GetAdmUserExcelReq;
import com.demo.api.model.res.adm.GetAdmUserListRes;
import com.demo.api.model.res.adm.GetAdmUserRes;
import com.demo.api.service.AdmService;
import com.demo.comp.DemoComp;
import com.demo.constant.AdmConst;
import com.demo.constant.MessageConst;
import com.demo.constant.SysConst;
import com.demo.domain.UserProfile;
import com.demo.entity.AdmUser;
import com.demo.error.DemoException;
import com.demo.excel.ExcelExportData;
import com.demo.excel.dto.AdmUserExcel;
import com.ziyao.demo.externalapi.demo.req.DemoReq;
import com.ziyao.demo.externalapi.demo.res.DemoRes;
import com.demo.repository.AdmUserRepository;
import com.demo.util.DateUtil;
import com.demo.util.ExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdmServiceImpl implements AdmService {

    private final UserProfile userProfile;
    private final DemoComp demoComp;
    private final AdmUserRepository admUserRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Void addAdmUser(AddAdmUserReq req) {

        AdmUser admUser = AdmUser.builder()
                .userId(req.getUserId())
                .userName(req.getUserName())
                .createTime(DateUtil.getNow())
                .createBy(userProfile.getUserId())
                .createBy(SysConst.SYSTEM)
                .build();
        admUserRepository.save(admUser);
        return null;
    }

    @Override
    public GetAdmUserRes getAdmUser(GetAdmUserReq req) {

        AdmUser admUser = admUserRepository.findByUserId(req.getUserId());
        if (admUser == null) {
            throw new DemoException(MessageConst.RtnCode.DATA_NOT_FOUND);
        }

        GetAdmUserRes getAdmUserRes = new GetAdmUserRes();
        BeanUtils.copyProperties(admUser, getAdmUserRes);
        return getAdmUserRes;
    }

    @Override
    public GetAdmUserListRes getAdmUserList() {

        List<AdmUser> sourceList = admUserRepository.findAll();
        if (CollectionUtils.isEmpty(sourceList)) {
            throw new DemoException(MessageConst.RtnCode.DATA_NOT_FOUND);
        }

        List<GetAdmUserListRes.AdmUser> admUserList = sourceList.stream()
                .map(r -> {
                    GetAdmUserListRes.AdmUser admUser = new GetAdmUserListRes.AdmUser();
                    BeanUtils.copyProperties(r, admUser);
                    return admUser;
                })
                .toList();

        return GetAdmUserListRes.builder()
                .admUserList(admUserList)
                .build();
    }

    @Override
    public GetAdmUserRes getAdmUser2(GetAdmUserReq req) {

        DemoReq demoReq = DemoReq.builder()
                .userId(req.getUserId())
                .build();
        DemoRes demoRes = demoComp.demo(demoReq);

        if (demoRes == null) {
            throw new DemoException(MessageConst.RtnCode.DATA_NOT_FOUND);
        }

        return GetAdmUserRes.builder()
                .id(demoRes.getId())
                .userId(demoRes.getUserId())
                .userName(demoRes.getUserName())
                .build();
    }

    @Override
    public void getAdmUserExcel(GetAdmUserExcelReq req) {

        List<AdmUser> admUserList = admUserRepository.findAll();
        if (CollectionUtils.isEmpty(admUserList)) {
            throw new DemoException(MessageConst.RtnCode.DATA_NOT_FOUND);
        }

        List<AdmUserExcel> admUserExcelList = admUserList.stream()
                .map(r -> AdmUserExcel.builder()
                        .id(String.valueOf(r.getId()))
                        .userId(r.getUserId())
                        .userName(r.getUserName())
                        .createBy(r.getCreateBy())
                        .createTime(DateUtil.formatDateToStr(r.getCreateTime(), DateUtil.BASIC_FORMAT))
                        .build())
                .toList();

        List<ExcelExportData> excelExportDataList = new ArrayList<>();
        excelExportDataList.add(ExcelExportData.builder()
                .clazz(AdmUserExcel.class)
                .sheetName("使用者資料")
                .dataList(admUserExcelList)
                .build());

        try {
            ExcelUtil.export(AdmConst.ADM_USER_EXCEL_FILE_NAME, excelExportDataList);
        } catch (Exception e) {
            log.error("AdmServiceImpl getAdmUserExcel error : " + e);
        }
    }
}
