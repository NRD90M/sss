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
import android.widget.TextView;

import com.wfj.bmobstudy.Bean.User;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.IsEmptyUtils;
import com.wfj.bmobstudy.Utils.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class UpdateQQFragment extends Fragment {
    private LinearLayout ly_back;
    private TextView tv_info;
    private EditText et_qq;
    private Button btn_sure_to;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update_qq, null, false);
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
        tv_info = (TextView) v.findViewById(R.id.tv_info);
        et_qq = (EditText) v.findViewById(R.id.et_qq);
        IsEmptyUtils.judeg_is_null(tv_info, "qq", "绑定QQ号", "修改绑定的QQ号", et_qq);
        btn_sure_to = (Button) v.findViewById(R.id.btn_sure_to);
        btn_sure_to.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String qq = et_qq.getText().toString().trim();
                if (TextUtils.isEmpty(qq)) {
                    Toast.show(getActivity(), "输入QQ号为空！", 0);
                    return;
                } else {
                    BmobUser user = BmobUser.getCurrentUser();
                    String id = user.getObjectId();
                    User u = new User();
                    u.setQq(qq);
                    u.update(id, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.show(getActivity(), "QQ号保存成功！", 0);
                                getActivity().onBackPressed();
                            } else {
                                Toast.show(getActivity(), "系统错误！" + e.getMessage(), 0);
                            }
                        }
                    });
                }
            }
        });
    }
}
