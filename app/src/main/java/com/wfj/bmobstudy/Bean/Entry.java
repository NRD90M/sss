package com.wfj.bmobstudy.Bean;

import cn.bmob.v3.BmobObject;

/**
 * @description 具体词条，包括所属的词条分类，作者，词条内容
 * @date: 2020/4/26
 * @author:
 */
public class Entry extends BmobObject {
    private EntryCategory category;
    private String title;
    private String content;
    private User author;
    private Integer stars;

    public EntryCategory getCategory() {
        return category;
    }

    public void setCategory(EntryCategory category) {
        this.category = category;
    }

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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }
}
