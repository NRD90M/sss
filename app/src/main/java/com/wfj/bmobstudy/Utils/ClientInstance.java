package com.wfj.bmobstudy.Utils;

import okhttp3.OkHttpClient;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class ClientInstance {
    public static OkHttpClient client;

    public static OkHttpClient getInstance() {
        return client != null ? client : new OkHttpClient();
    }
}
