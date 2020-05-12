package com.jju.howe.howeassistant.action;

import android.content.Intent;

import com.jju.howe.howeassistant.activity.RobotMainActivity;


public class MessageView {
	private RobotMainActivity mActivity;
	
	public MessageView(RobotMainActivity activity){
		mActivity=activity;
	}
	
	public void start(){
		Intent intent=new Intent();
		intent.setClassName("com.android.mms","com.android.mms.ui.ConversationList");
		mActivity.startActivity(intent);
	}
}
