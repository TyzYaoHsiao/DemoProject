package com.ziyao.demo.controller;

import com.ziyao.demo.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.GitProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HealthyCheckController {

    @Autowired(required = false)
    private GitProperties gitProperties;

    @GetMapping("/healthyCheck")
    public String healthyCheck() {
        return "OK";
    }

    @GetMapping("/version")
    public Object version() {
        Map<String, Object> gitInfo = new LinkedHashMap<>();

        if (gitProperties != null) {
            String buildTime = null;
            String s = gitProperties.get("build.time");
            if (StringUtils.isNotBlank(s)) {
                LocalDateTime time = Instant.ofEpochMilli(Long.parseLong(s))
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();

                DateTimeFormatter df = DateTimeFormatter.ofPattern(DateUtil.API_TIME_FORMAT);
                buildTime = df.format(time);
            }

            gitInfo.put("branch", gitProperties.getBranch());
            gitInfo.put("commitId", gitProperties.getCommitId());
            gitInfo.put("buildTime", buildTime);
        }
        return gitInfo;
    }
}
