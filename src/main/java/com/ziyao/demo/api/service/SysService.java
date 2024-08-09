package com.demo.api.service;

import com.demo.api.model.req.sys.GetSysApiLogListReq;
import com.demo.api.model.req.sys.GetSysExternalApiLogListReq;
import com.demo.api.model.res.sys.GetSysApiLogListRes;
import com.demo.api.model.res.sys.GetSysExternalApiLogListRes;

public interface SysService {

    GetSysApiLogListRes getSysApiLogList(GetSysApiLogListReq req);

    GetSysExternalApiLogListRes getSysExternalApiLogList(GetSysExternalApiLogListReq req);
}
