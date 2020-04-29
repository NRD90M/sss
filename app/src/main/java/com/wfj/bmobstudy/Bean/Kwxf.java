package com.wfj.bmobstudy.Bean;


/**
 * @description 课外学分的实体类
 * @date: 2020/4/26
 * @author:
 */
public class Kwxf {
    private String time;
    private String name;
    private double grade;
    private String pass;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "Kwxf{" +
                "time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", pass='" + pass + '\'' +
                '}';
    }
}
