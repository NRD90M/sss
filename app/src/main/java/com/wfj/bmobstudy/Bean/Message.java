package com.wfj.bmobstudy.Bean;

import cn.bmob.v3.BmobObject;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class Message extends BmobObject {
    private String title;
    private String content;
    private String author;
    private String phone;
    private String qq;
    private String weChat;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }
}
