package com.wfj.bmobstudy.Bean;

import cn.bmob.v3.BmobObject;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class FunctionState extends BmobObject{
    private String name;
    private Boolean isOpen;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }
}
