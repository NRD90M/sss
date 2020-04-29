package com.wfj.bmobstudy.Utils;

import android.graphics.Bitmap;

import okhttp3.OkHttpClient;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author:
 */
public class KwxfUtil {
    public static OkHttpClient okHttpClient;

    public static OkHttpClient getOkHttpClientInstance() {
        if (okHttpClient == null) {
            return new OkHttpClient();
        } else {
            return okHttpClient;
        }
    }

    public Bitmap get_kwxf_code_img() {
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
        return null;
    }
}
