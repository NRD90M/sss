package com.wfj.bmobstudy;

import junit.framework.Assert;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class CalculaterTest {
    Calculater calculater=new Calculater();
    @org.junit.Test
    public void addTest(){
        int a=1;
        int b=2;
        int result=calculater.add(a,b);
        Assert.assertEquals(result,3);
    }
}
