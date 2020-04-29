package com.wfj.bmobstudy.Bean;

import cn.bmob.v3.BmobObject;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class Game2048Rank extends BmobObject {
    private String account;
    private Integer score;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
