package com.wfj.bmobstudy.Fragment.SetMidFragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wfj.bmobstudy.Activity.AppBaseActivity;
import com.wfj.bmobstudy.Fragment.SetMinFragment.DocumentDownloadFragment;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.FunctionStateUtil;
import com.wfj.bmobstudy.Utils.ShowOrHiddenUtil;
import com.vondear.rxtools.view.RxToast;

/**
 * @description 小工具
 * @date: 2020/4/26
 * @author: a */
public class SmallUtilActivity extends AppBaseActivity implements View.OnClickListener {
    private LinearLayout ly_back;
    private LinearLayout ly_document;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    /**
     * 返回 Activity Layout
     *
     * @return Layout ID
     */
    @Override
    public int setLayoutView() {
        return R.layout.fragment_small_util;
    }

    private void initView() {
        ly_document = (LinearLayout) findViewById(R.id.ly_document);
        ly_document.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ly_document:
                if (FunctionStateUtil.BaiDuDocumentDownload) {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left);
                    transaction.replace(R.id.fl_content, new DocumentDownloadFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    RxToast.info("该功能正在调整中！");
                }
        }

    }
}
