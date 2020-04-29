package com.wfj.bmobstudy.Bean;

import cn.bmob.v3.BmobObject;


/**
 * @description 词条分类
 * @date: 2020/4/26
 * @author:
 */
public class EntryCategory extends BmobObject {
    private String name;
    private Integer number;

    public String getName() {
        return name;
    }

    public void setName(String category) {
        this.name = category;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
