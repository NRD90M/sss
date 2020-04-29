package com.wfj.bmobstudy.Fragment.SetMidFragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wfj.bmobstudy.Fragment.SetMinFragment.UpdatePasswordFragment;
import com.wfj.bmobstudy.Fragment.SetMinFragment.UpdateUsernameFragment;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.ShowOrHiddenUtil;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class SetAccountFragment extends Fragment {
    private LinearLayout ly_back;
    private FragmentManager fragmentManager;
    private LinearLayout ly_update_username;
    private LinearLayout ly_update_password;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_set_account, container, false);
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
        ShowOrHiddenUtil.hidden_home_bottom(getActivity());
        ly_update_username = (LinearLayout) v.findViewById(R.id.ly_update_username);
        ly_update_username.setOnClickListener(new UpdateUsernameOnClickListener());
        ly_update_password = (LinearLayout) v.findViewById(R.id.ly_update_password);
        ly_update_password.setOnClickListener(new updatePasswordOnClickListener());
    }


    private void changeFragment(int content, Fragment fragment) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left)
                .replace(content, fragment)
                .addToBackStack(null)
                .commit();
    }

    private class UpdateUsernameOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            changeFragment(R.id.fl_content, new UpdateUsernameFragment());
        }
    }

    private class updatePasswordOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            changeFragment(R.id.fl_content, new UpdatePasswordFragment());

        }
    }
}
