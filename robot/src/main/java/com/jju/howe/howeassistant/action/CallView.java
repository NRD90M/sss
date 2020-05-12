package com.jju.howe.howeassistant.action;

import android.content.Intent;

import com.jju.howe.howeassistant.activity.RobotMainActivity;

public class CallView {

    private RobotMainActivity mActivity;

    public CallView(RobotMainActivity activity) {
        mActivity = activity;
    }

    public void start() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL_BUTTON);
        mActivity.startActivity(intent);
    }
}
