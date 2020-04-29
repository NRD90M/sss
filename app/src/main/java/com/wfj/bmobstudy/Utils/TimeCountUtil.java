package com.wfj.bmobstudy.Utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Button;

import com.wfj.bmobstudy.R;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class TimeCountUtil extends CountDownTimer {
    private Button btn;
    private Context context;

    //第一个参数是总时长，第二个参数是多长时间执行一次回调
    public TimeCountUtil(Button btn, Context context, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
        this.context = context;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        //不可用时
        btn.setClickable(false);
        btn.setBackgroundColor(context.getResources().getColor(R.color.border_clo));
        String text = millisUntilFinished / 1000 + "秒后可重新获取";
        btn.setText(text);

    }

    @Override
    public void onFinish() {
        //倒计时结束，按钮可用
        btn.setClickable(true);
        btn.setBackgroundResource(R.drawable.shape);
        btn.setText("获取验证码");
    }
}
