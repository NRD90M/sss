package com.wfj.bmobstudy.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.aiuisdk.BaseSpeechCallback;
import com.aiuisdk.SpeechManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wfj.bmobstudy.R;

import io.reactivex.disposables.Disposable;

/**
 * Created by cqm on 2020/4/29.
 * 功能:
 */
public class ChackActivity extends AppBaseActivity {
    private static final String TAG = "ChackActivity";
    private TextView question;
    private TextView answer;
    /**
     * 返回 Activity Layout
     *
     * @return Layout ID
     */
    @Override
    public int setLayoutView() {
        return R.layout.activity_chack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermission();
        initView();
    }

    private void initView() {
        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);
    }
    //语音识别和语义理解的回调
    BaseSpeechCallback speechCallback = new BaseSpeechCallback() {
        @Override
        public void recognizeResult(String text) {
            Log.d(TAG, "recognizeResult::" + text);
            question.setText(text);

        }

        @Override
        public void nlpResult(String text, String json) {
            Log.d(TAG, "nlpResult::" + text);
            answer.setText(text);
            SpeechManager.onSpeaking(text);//语义理解结果
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpeechManager.getInstance().onDestroy();
    }


    @SuppressLint("CheckResult")
    private void initPermission() {
        Disposable subscribe = new RxPermissions(this).request(Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CHANGE_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE)
                .subscribe(granted -> {
                    if (granted) {
//                        Toast.makeText(this, "获取权限成功", Toast.LENGTH_SHORT).show();
                        initChatSDK();
                    } else {
                        Toast.makeText(this, "请先获取权限后使用", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void initChatSDK() {
        SpeechManager.CreateInstance(this);//初始化语音合成、识别模块
        SpeechManager.getInstance().setBaseSpeechCallback(speechCallback);//设置语音识别和语义的回调
    }

}
