package com.wfj.bmobstudy.Bean;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class Document extends BmobObject{
    private String st_id;
    private Integer number;

    public String getSt_id() {
        return st_id;
    }

    public void setSt_id(String st_id) {
        this.st_id = st_id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
