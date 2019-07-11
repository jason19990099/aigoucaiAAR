package com.example.agc.aigoucai.bean;

import java.util.List;

public class BottomBean {


    /**
     * status : 200
     * message : ok
     * date : [{"url":"/","name":"首页","icon_hide":"/images/icon/home.png","icon_show":"/images/icon/home_show.png","has_site":true},{"url":"/fixed.php","name":"在线视频","icon_hide":"/images/icon/jiemu.png","icon_show":"/images/icon/jiemu_show.png","has_site":true},{"url":"./theme.php","name":"专题视频","icon_hide":"/images/icon/movie.png","icon_show":"/images/icon/movie_show.png","has_site":true},{"url":"https://www.agc20.com/?att=10002","name":"在线购彩","icon_hide":"/images/icon/wangzhuan.png","icon_show":"/images/icon/wangzhuan_show.png","has_site":false},{"url":"./ucenter","name":"个人中心","icon_hide":"/images/icon/nav.png","icon_show":"/images/icon/nav_show.png","has_site":true}]
     */

    private int status;
    private String message;
    private List<DateBean> date;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DateBean> getDate() {
        return date;
    }

    public void setDate(List<DateBean> date) {
        this.date = date;
    }

    public static class DateBean {
        /**
         * url : /
         * name : 首页
         * icon_hide : /images/icon/home.png
         * icon_show : /images/icon/home_show.png
         * has_site : true
         */

        private String url;
        private String name;
        private String icon_hide;
        private String icon_show;
        private boolean has_site;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon_hide() {
            return icon_hide;
        }

        public void setIcon_hide(String icon_hide) {
            this.icon_hide = icon_hide;
        }

        public String getIcon_show() {
            return icon_show;
        }

        public void setIcon_show(String icon_show) {
            this.icon_show = icon_show;
        }

        public boolean isHas_site() {
            return has_site;
        }

        public void setHas_site(boolean has_site) {
            this.has_site = has_site;
        }
    }
}
