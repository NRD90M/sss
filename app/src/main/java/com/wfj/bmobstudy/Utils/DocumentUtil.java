package com.wfj.bmobstudy.Utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;

import java.io.IOException;

import okhttp3.*;
import okhttp3.Call;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class DocumentUtil {
    private static OkHttpClient client = new OkHttpClient();
    private static String wenkubao_post_url = "https://wenkubao.cc/default.aspx";

    public static void documentDownload(final String document_url, final Activity activity) {
        RequestBody body = new FormBody.Builder()
                .add("username", "byprf")
                .add("txtUrl", document_url)
                .add("rememberUser", "on")
                .add("password", "69859")
                .build();
        final Request request = new Request.Builder()
                .url(wenkubao_post_url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String html = response.body().string();
                if (html.contains("http")) {
                    Log.e("tag", "html" + html);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShowDialogUtil.closeProgressDialog();
                            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(html)));
                        }
                    });
                } else {
                    ToastUtils.showShort(html);
                }
            }
        });

    }
}
