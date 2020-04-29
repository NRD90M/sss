package com.wfj.bmobstudy.Bean;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class Version extends BmobObject {
    //版本号
    private Integer version;
    //版本简述
    private String description;
    //发布时间
    private BmobDate release;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BmobDate getRelease() {
        return release;
    }

    public void setRelease(BmobDate release) {
        this.release = release;
    }
}
