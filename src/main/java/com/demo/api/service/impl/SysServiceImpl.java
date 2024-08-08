package com.demo.api.service.impl;

import com.demo.api.model.req.sys.GetSysApiLogListReq;
import com.demo.api.model.req.sys.GetSysExternalApiLogListReq;
import com.demo.api.model.res.sys.GetSysApiLogListRes;
import com.demo.api.model.res.sys.GetSysExternalApiLogListRes;
import com.demo.api.service.SysService;
import com.demo.constant.MessageConst;
import com.demo.entity.SysApiLog;
import com.demo.entity.SysExternalApiLog;
import com.demo.error.DemoException;
import com.demo.repository.SysApiLogRepository;
import com.demo.repository.SysExternalApiLogRepository;
import com.demo.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SysServiceImpl implements SysService {

    private final SysApiLogRepository sysApiLogRepository;
    private final SysExternalApiLogRepository sysExternalApiLogRepository;

    @Override
    public GetSysApiLogListRes getSysApiLogList(GetSysApiLogListReq req) {

        List<SysApiLog> sysApiLogList =  sysApiLogRepository.findAllByOrderByIdDesc();
        if (CollectionUtils.isEmpty(sysApiLogList)){
            throw new DemoException(MessageConst.RtnCode.DATA_NOT_FOUND);
        }

        return GetSysApiLogListRes.builder()
                .sysApiLogList(sysApiLogList.stream()
                        .map(r -> {
                            GetSysApiLogListRes.SysApiLog sysApiLog = new GetSysApiLogListRes.SysApiLog();
                            BeanUtils.copyProperties(r, sysApiLog);
                            sysApiLog.setCreateTime(DateUtil.formatDateToStr(r.getCreateTime(), DateUtil.BASIC_FORMAT));
                            return sysApiLog;
                        })
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public GetSysExternalApiLogListRes getSysExternalApiLogList(GetSysExternalApiLogListReq req) {

        List<SysExternalApiLog> sysExternalApiLogList =  sysExternalApiLogRepository.findAllByOrderByIdDesc();
        if (CollectionUtils.isEmpty(sysExternalApiLogList)){
            throw new DemoException(MessageConst.RtnCode.DATA_NOT_FOUND);
        }

        return GetSysExternalApiLogListRes.builder()
                .sysExternalApiLogList(sysExternalApiLogList.stream()
                        .map(r -> {
                            GetSysExternalApiLogListRes.SysExternalApiLog sysExternalApiLog = new GetSysExternalApiLogListRes.SysExternalApiLog();
                            BeanUtils.copyProperties(r, sysExternalApiLog);
                            sysExternalApiLog.setCreateTime(DateUtil.formatDateToStr(r.getCreateTime(), DateUtil.BASIC_FORMAT));
                            return sysExternalApiLog;
                        })
                        .collect(Collectors.toList()))
                .build();
    }
}
