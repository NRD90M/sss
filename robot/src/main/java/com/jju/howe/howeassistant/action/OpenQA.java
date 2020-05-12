package com.jju.howe.howeassistant.action;


import com.jju.howe.howeassistant.activity.RobotMainActivity;

public class OpenQA {

	private String mText;
	RobotMainActivity mActivity;
	
	public OpenQA(String text, RobotMainActivity activity){
		mText=text;
		mActivity=activity;
	}
	
	public void start(){
		mActivity.speakAnswer(mText);
	}
	
}
