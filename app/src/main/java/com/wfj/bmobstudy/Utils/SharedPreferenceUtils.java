package com.wfj.bmobstudy.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class SharedPreferenceUtils {
    public static void spPutString(Context context, String xmlName, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String spGetString(Context context, String xmlName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "-1");
        return value;
    }

    public static void spDeleteString(Context context, String xmlName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();

    }

    //登陆后，默认存储相关的账号与密码
    public static void save(String account, String password, Context context) {
        spPutString(context, "avoid_login", "account", account);
        spPutString(context, "avoid_login", "password", password);

    }

    //退出登录时，删除相关数据
    public static void delete(Context context) {
        spDeleteString(context, "avoid_login", "account");
        spDeleteString(context, "avoid_login", "password");

    }


}
