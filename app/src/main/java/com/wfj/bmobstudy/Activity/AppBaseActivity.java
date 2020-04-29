package com.wfj.bmobstudy.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wfj.bmobstudy.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * @description AppBaseActivity
 * @date: 2020/4/27
 * @author: a */
public abstract class AppBaseActivity extends AppCompatActivity implements OnClickListener {


    public TextView tv_left;
    //Log 日志的TAG
    private static String TAG = "AppBaseActivity";
    //运行时权限的请求码
    private static final int REQUEST_CODE = 100;
    /*
       运行时权限
       CALL_PHONE：拨打电话
       CAMERA：照相机
       READ_EXTERNAL_STORAGE：读SD卡
       WRITE_EXTERNAL_STORAGE 写SD卡
    */
    private static final String[] PERMISSION = new String[]{
            Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置标题栏的颜色
        }
        super.onCreate(savedInstanceState);
        TAG = getClass().getName();
        setContentView(setLayoutView());
    }
    /**
     * APP启动时检查是否有未授权的权限，如果有则进行请求获取权限
     */
    @Override
    protected void onStart() {
        super.onStart();
        //APP启动时获取权限
        checkPermission(PERMISSION);

    }
    /**
     * 利用反射机制，实现动态跳转
     *
     * @param context   跳转Context
     * @param className 需要跳转到哪一个类
     */
    public static void myStartActivity(Context context, Class className) {
        Intent startIntent = new Intent(context, className);
        context.startActivity(startIntent);
    }

    /**
     * 返回 Activity Layout
     *
     * @return Layout ID
     */
    public abstract int setLayoutView();
    /**
     * 隐藏软键盘
     * hideSoftInputView
     *
     * @param
     * @return void
     * @throws
     * @Title: hideSoftInputView
     */
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 弹出输入法窗口
     */
    public void showSoftInputView(final EditText et) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((InputMethodManager) et.getContext().getSystemService(Service.INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 0);
    }

    /**
     * 初始化titlebar，该方法只有在标题栏布局符合此规则时才能调用
     * @param left titlebar左按钮
     * @param title titlebar标题
     * @param right titlebar 右按钮
     * @param onClickListener 左右按钮点击事件
     */
    public void initTitleBar(String left, String title, String right, OnClickListener onClickListener){
        tv_left=(TextView) findViewById(R.id.tv_left);//返回按钮

        if(!TextUtils.isEmpty(left)){
            tv_left.setText(left);
            tv_left.setVisibility(View.VISIBLE);
            tv_left.setOnClickListener(onClickListener);
        }
        
    }

    /**
     * 检查是否有未授权的危险权限，如果有则进行请求权限
     *
     * @param manifests 运行时权限，接收来自 PERMISSION
     */
    public void checkPermission(String[] manifests) {
        //保存未授权的权限
        ArrayList<String> manifestAl = new ArrayList<>();
        //遍历权限集合，如果有未权限的权限，添加到集合中
        for (String manifest :
                manifests) {

            if (ContextCompat.checkSelfPermission(this, manifest) != PackageManager.PERMISSION_GRANTED) {
                manifestAl.add(manifest);
            }


        }
        //如果集合中有未权限的权限，则进行再次授权
        if (manifestAl.size() > 0)
            ActivityCompat.requestPermissions(this, manifests, REQUEST_CODE);

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 如果子类支持点击左上角返回按钮返回，则在子类的onClick方法中需添加super.onClick(View view);
     */
    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.tv_left:
                finish();
                break;
        }
    }

}
