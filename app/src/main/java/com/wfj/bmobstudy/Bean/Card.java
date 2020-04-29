package com.wfj.bmobstudy.Bean;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class Card {
    private String time;
    private String type;
    private String subSystem;
    private String price;
    private String remain;
    private String times;
    private String state;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubSystem() {
        return subSystem;
    }

    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRemain() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Card{" +
                "time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", subSystem='" + subSystem + '\'' +
                ", price='" + price + '\'' +
                ", remain='" + remain + '\'' +
                ", times='" + times + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
