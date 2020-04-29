package com.wfj.bmobstudy.Bean;


import java.util.Date;

import cn.bmob.v3.BmobObject;


/**
 * @description 签到
 * @date: 2020/4/26
 * @author:
 */
public class Sign extends BmobObject {
    private String account;
    private Date signDate;
    private String generalsignDate;

    public Sign(String account, Date signDate, String generalsignDate) {
        this.account = account;
        this.signDate = signDate;
        this.generalsignDate = generalsignDate;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getGeneralsignDate() {
        return generalsignDate;
    }

    public void setGeneralsignDate(String generalsignDate) {
        this.generalsignDate = generalsignDate;
    }
}
