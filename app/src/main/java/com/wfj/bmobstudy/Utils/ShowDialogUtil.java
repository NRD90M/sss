package com.wfj.bmobstudy.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class ShowDialogUtil {
    private static ProgressDialog progressDialog;

    public static void showProgressDialog(Activity activity, String msg) {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(msg);
        //点击屏幕不消失，点击返回键消失
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    public static void showProgressDialog(Context context, String msg) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        //点击屏幕不消失，点击返回键消失
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    public static void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
