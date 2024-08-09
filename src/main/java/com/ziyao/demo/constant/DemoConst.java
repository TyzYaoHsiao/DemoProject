package com.demo.constant;

import org.springframework.http.HttpMethod;

public class DemoConst {

    public static final String DEMO_URL_KEY = "";

    public enum MsgId {

        GetAdmUser("/adm/getAdmUser", "FNS12345", HttpMethod.POST),
        FNS12345("/adm/get123", "FNS12345", HttpMethod.POST);

        MsgId (String url, String code, HttpMethod httpMethod) {
            this.url = url;
            this.code = code;
            this.httpMethod = httpMethod;
        }

        private final String url;
        private final String code;
        private final HttpMethod httpMethod;

        public String getUrl() {
            return url;
        }

        public String getCode() {
            return code;
        }

        public HttpMethod getHttpMethod() {
            return httpMethod;
        }
    }
}
