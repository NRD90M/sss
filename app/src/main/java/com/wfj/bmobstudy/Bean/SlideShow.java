package com.wfj.bmobstudy.Bean;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class SlideShow {
    private String title;
    private String img_url;
    private String detail_url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }

    @Override
    public String toString() {
        return "SlideShow{" +
                "title='" + title + '\'' +
                ", img_url='" + img_url + '\'' +
                ", detail_url='" + detail_url + '\'' +
                '}';
    }
}
