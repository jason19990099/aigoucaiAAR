package com.aigoucai.lottery.checkaar;

import java.util.List;

public class Appdata {

    private List<Appdatas> datas;

    public static class Appdatas{
        private String extend_1_title;
        private String extend_1_url;

        public String getExtend_1_title() {
            return extend_1_title;
        }

        public void setExtend_1_title(String extend_1_title) {
            this.extend_1_title = extend_1_title;
        }

        public String getExtend_1_url() {
            return extend_1_url;
        }

        public void setExtend_1_url(String extend_1_url) {
            this.extend_1_url = extend_1_url;
        }
    }
}


