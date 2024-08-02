package com.demo.api.service;

import com.demo.api.model.req.adm.AddAdmUserReq;
import com.demo.api.model.req.adm.GetAdmUserReq;
import com.demo.api.model.res.adm.AddAdmUserRes;
import com.demo.api.model.res.adm.GetAdmUserRes;

public interface AdmService {

    AddAdmUserRes addAdmUser(AddAdmUserReq req);

    GetAdmUserRes getAdmUser(GetAdmUserReq req);

    GetAdmUserRes getAdmUser2(GetAdmUserReq req);
}
