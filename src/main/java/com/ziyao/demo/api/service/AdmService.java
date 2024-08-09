package com.ziyao.demo.api.service;

import com.ziyao.demo.api.model.req.adm.AddAdmUserReq;
import com.ziyao.demo.api.model.req.adm.GetAdmUserReq;
import com.ziyao.demo.api.model.req.demo.GetAdmUserExcelReq;
import com.ziyao.demo.api.model.res.adm.GetAdmUserListRes;
import com.ziyao.demo.api.model.res.adm.GetAdmUserRes;

public interface AdmService {

    Void addAdmUser(AddAdmUserReq req);

    GetAdmUserListRes getAdmUserList();

    GetAdmUserRes getAdmUser(GetAdmUserReq req);

    GetAdmUserRes getAdmUser2(GetAdmUserReq req);

    void getAdmUserExcel(GetAdmUserExcelReq req);
}
