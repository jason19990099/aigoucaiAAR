package com.example.agc.aigoucai.bean;

import java.util.List;

/**
 *  发送劫持数据
 */
public class DataSynevent {
    private List<String> list;
    private String type;
    public List<String> getList() {
        return list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "DataSynevent{" +
                "list=" + list +
                '}';
    }
}
