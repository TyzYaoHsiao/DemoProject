package com.demo.constant;

public class DemoConst {

    public enum MsgId {

        GetAdmUser("/adm/getAdmUser");

        MsgId (String url) {
            this.url = url;
        }

        private final String url;

        public String getUrl() {
            return url;
        }
    }
}
