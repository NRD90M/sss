package com.wfj.bmobstudy.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class EducationOptionalCourse implements Serializable {
    private String time;
    private String title;
    private String url;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "EducationOptionalCourse{" +
                "time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
