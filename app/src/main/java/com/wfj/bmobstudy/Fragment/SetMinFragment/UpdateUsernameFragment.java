package com.wfj.bmobstudy.Fragment.SetMinFragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wfj.bmobstudy.Bean.User;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.Toast;


import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class UpdateUsernameFragment extends Fragment {
    private LinearLayout ly_back;
    private EditText et_update_username;
    private Button btn_sure_to;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update_username, null, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        ly_back = (LinearLayout) v.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        et_update_username = (EditText) v.findViewById(R.id.et_update_username);
        btn_sure_to = (Button) v.findViewById(R.id.btn_sure_to);
        btn_sure_to.setOnClickListener(new sureToOnClickListener());

    }

    class sureToOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String input_username = et_update_username.getText().toString();
            if (TextUtils.isEmpty(input_username)) {
                Toast.show(getActivity(), "输入的用户名为空！", 1);
                return;
            } else {
                BmobUser user = new BmobUser();
                user.setUsername(input_username);
                User u = BmobUser.getCurrentUser(User.class);
                user.update(u.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.show(getActivity(), "用户名修改成功！", 0);
                            getActivity().onBackPressed();
                        } else {
                            Toast.show(getActivity(), "用户名修改失败！" + e.getMessage(), 0);
                        }
                    }
                });
            }
        }
    }//点击返回键返回桌面而不是退出程序


}
