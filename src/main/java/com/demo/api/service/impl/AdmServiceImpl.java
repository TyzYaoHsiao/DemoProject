package com.demo.api.service.impl;

import com.demo.api.model.req.adm.AddAdmUserReq;
import com.demo.api.model.req.adm.GetAdmUserReq;
import com.demo.api.model.req.demo.GetAdmUserExcelReq;
import com.demo.api.model.res.adm.AddAdmUserRes;
import com.demo.api.model.res.adm.GetAdmUserRes;
import com.demo.api.service.AdmService;
import com.demo.comp.DemoComp;
import com.demo.constant.AdmConst;
import com.demo.constant.MessageConst;
import com.demo.domain.UserProfile;
import com.demo.entity.AdmUser;
import com.demo.error.CustomException;
import com.demo.excel.CustomExcelExportData;
import com.demo.excel.dto.AdmUserExcel;
import com.demo.externalapi.demo.req.DemoReq;
import com.demo.externalapi.demo.res.DemoRes;
import com.demo.repository.AdmUserRepository;
import com.demo.util.DateUtil;
import com.demo.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
                // TODO FOR TEST
                //.createBy(userProfile.getUserId())
                .createBy("system")
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

    @Override
    public void getAdmUserExcel(GetAdmUserExcelReq req) {

        getAdmUserExcel2(req);
//        List<AdmUser> admUserList = admUserRepository.findAll();
//
//        List<AdmUserExcel> admUserExcelList = admUserList.stream()
//                .map(r -> AdmUserExcel.builder()
//                        .id(String.valueOf(r.getId()))
//                        .userId(r.getUserId())
//                        .userName(r.getUserName())
//                        .createBy(r.getCreateBy())
//                        .createTime(DateUtil.formatDateToStr(r.getCreateTime(), DateUtil.BASIC_FORMAT))
//                        .build())
//                .toList();
//
//        List<ExcelExportData> excelExportDataList = new ArrayList<>();
//        excelExportDataList.add(ExcelExportData.builder()
//                .clazz(AdmUserExcel.class)
//                .sheetName("使用者資料")
//                .dataList(admUserExcelList)
//                .build());
//
//        try {
//            ExcelUtil.export(AdmConst.ADM_USER_EXCEL_FILE_NAME, excelExportDataList);
//        } catch (Exception e) {
//            log.error("AdmServiceImpl getAdmUserExcel error : " + e);
//        }
    }

    public void getAdmUserExcel2(GetAdmUserExcelReq req) {

        List<AdmUser> admUserList = admUserRepository.findAll();

        List<AdmUserExcel> admUserExcelList = admUserList.stream()
                .map(r -> AdmUserExcel.builder()
                        .id(String.valueOf(r.getId()))
                        .userId(r.getUserId())
                        .userName(r.getUserName())
                        .createBy(r.getCreateBy())
                        .createTime(DateUtil.formatDateToStr(r.getCreateTime(), DateUtil.BASIC_FORMAT))
                        .build())
                .toList();

        List<CustomExcelExportData> customExcelExportDataList = new ArrayList<>();
        List<CustomExcelExportData.CustomRow> customRowList = new ArrayList<>();

        int rowIdx = 0;
        // 第一區塊
        List<List<CustomExcelExportData.CustomRow.CustomCell>> headerList1 = new ArrayList<>();
        List<Object> dataList1 = new ArrayList<>();
        List<CustomExcelExportData.CustomRow.CustomCell> headers1 = new ArrayList<>();
        List<CustomExcelExportData.CustomRow.CustomCell> data1 = new ArrayList<>();
        headers1.add(ExcelUtil.createCustomCell("報表日期"));
        headers1.add(ExcelUtil.createCustomCell("dataDt", 0, 0, 1, 12));
        headerList1.add(headers1);

        data1.add(ExcelUtil.createCustomCell("註記: 1.", 1, 1, 0, 12));
        dataList1.add(data1);
        dataList1.add("字串測試");

        customRowList.add(CustomExcelExportData.CustomRow.builder()
                .startRowIdx(0)
                .headerList(headerList1)
                .dataList(dataList1)
                .build());

        rowIdx = rowIdx + dataList1.size();
        // 第二區塊
        List<List<CustomExcelExportData.CustomRow.CustomCell>> headerList2 = new ArrayList<>();
        List<CustomExcelExportData.CustomRow.CustomCell> headers2 = new ArrayList<>();
        headers2.add(ExcelUtil.createCustomCell("理專姓名"));
        headers2.add(ExcelUtil.createCustomCell("理專職級"));
        headers2.add(ExcelUtil.createCustomCell("商機客戶"));
        headers2.add(ExcelUtil.createCustomCell("預估理財手收"));
        headers2.add(ExcelUtil.createCustomCell("預估保險佣收"));
        headers2.add(ExcelUtil.createCustomCell("上次聯繫日期"));
        headers2.add(ExcelUtil.createCustomCell("下次約訪日期"));
        headers2.add(ExcelUtil.createCustomCell("已追蹤天數"));
        headerList2.add(headers2);

        customRowList.add(CustomExcelExportData.CustomRow.builder()
                .startRowIdx(3)
                .headerList(headerList2)
                .dataList(admUserExcelList)
                .build());

        // 第三區塊
        List<List<CustomExcelExportData.CustomRow.CustomCell>> headerList3 = new ArrayList<>();
        List<CustomExcelExportData.CustomRow.CustomCell> headers3 = new ArrayList<>();
        List<CustomExcelExportData.CustomRow.CustomCell> headers3_2 = new ArrayList<>();
        headers3.add(ExcelUtil.createCustomCell(null, 8, 9, 0, 0));
        headers3.add(ExcelUtil.createCustomCell("財管業績進度", 8, 8, 1, 3));
        headers3.add(ExcelUtil.createCustomCell("商機預估手佣收金額", 8, 8, 4, 5));
        headerList3.add(headers3);

        headers3_2.add(ExcelUtil.createCustomCell(null));
        headers3_2.add(ExcelUtil.createCustomCell("月生產力目標"));
        headers3_2.add(ExcelUtil.createCustomCell("月生產力總計"));
        headers3_2.add(ExcelUtil.createCustomCell("月生產力(含在途)總計"));
        headers3_2.add(ExcelUtil.createCustomCell("理財"));
        headers3_2.add(ExcelUtil.createCustomCell("保險"));
        headerList3.add(headers3_2);

        customRowList.add(CustomExcelExportData.CustomRow.builder()
                .startRowIdx(8)
                .headerList(headerList3)
                .dataList(admUserExcelList)
                .build());

        customExcelExportDataList.add(CustomExcelExportData.builder()
                .sheetName("使用者資料")
                .customRowList(customRowList)
                .build());

        try {
            ExcelUtil.export2(AdmConst.ADM_USER_EXCEL_FILE_NAME, customExcelExportDataList);
        } catch (Exception e) {
            log.error("AdmServiceImpl getAdmUserExcel error : " + e);
        }
    }

}
