package com.wfj.bmobstudy.Fragment.Advice;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wfj.bmobstudy.Bean.Advice;
import com.wfj.bmobstudy.Bean.User;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.ShowOrHiddenUtil;
import com.vondear.rxtools.view.RxToast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * @description 反馈意见
 * @date: 2020/4/26
 * @author: a */
public class AdviceFragment extends Fragment {
    private LinearLayout ly_back;
    private EditText et_advice_title;
    private EditText et_advice_content;
    private Button btn_advice_submit;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_advice, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        ly_back = (LinearLayout) v.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        ShowOrHiddenUtil.hidden_home_bottom(getActivity());
        et_advice_title = (EditText) v.findViewById(R.id.et_advice_title);
        et_advice_content = (EditText) v.findViewById(R.id.et_advice_content);
        btn_advice_submit = (Button) v.findViewById(R.id.btn_advice_submit);
        btn_advice_submit.setOnClickListener(new OnSubmitClickListener());

    }

    private class OnSubmitClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String title = et_advice_title.getText().toString();
            String content = et_advice_content.getText().toString();
            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
                RxToast.warning("反馈标题或内容不能为空！");
                return;
            }
            Advice advice = new Advice();
            User user = BmobUser.getCurrentUser(User.class);
            advice.setUser(user);
            advice.setTitle(title);
            advice.setContent(content);
            advice.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        RxToast.success("反馈成功！");
                        getActivity().onBackPressed();
                    } else {
                        RxToast.error("反馈失败！");
                    }
                }
            });
        }
    }
}
