package com.wfj.bmobstudy.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.Utils;
import com.wfj.bmobstudy.Fragment.CommunityFragment;
import com.wfj.bmobstudy.Fragment.EducationFragment;
import com.wfj.bmobstudy.Fragment.SettingFragment;
import com.wfj.bmobstudy.Fragment.SignFragment;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.BackHandlerHelper;
import com.wfj.bmobstudy.Utils.Entry.EntryUtil;
import com.wfj.bmobstudy.Utils.FunctionStateUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import me.majiajie.pagerbottomtabstrip.MaterialMode;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;


/**
 * @description MainActivity
 * @date: 2020/4/26
 * @author: a */
public class MainActivity extends AppBaseActivity {
    private Fragment signFragment;
    private Fragment educationFragment;
    private Fragment communityFragment;
    private Fragment settingFragment;
    private FragmentManager fragmentManager;
    private NavigationController mNavigationController;
    private PageNavigationView pageBottomTabLayout;
    public FrameLayout fl_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.init(getApplication());
        initViews();
        initTab();
        initFunctionState();
        //获取服务器时间
        EntryUtil.get_current_time();

    }

    /**
     * 返回 Activity Layout
     *
     * @return Layout ID
     */
    @Override
    public int setLayoutView() {
        return R.layout.activity_home;
    }

    private void initViews() {
        signFragment = new SignFragment();
        educationFragment = new EducationFragment();
        communityFragment = new CommunityFragment();
        settingFragment = new SettingFragment();
        fragmentManager = getSupportFragmentManager();
        fl_content=findViewById(R.id.fl_content);
        //设置默认显示的Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_content,  signFragment)
                .commit();

        //tv_home_top.setText("我要签到");

    }

    private void initTab() {
        pageBottomTabLayout = (PageNavigationView) findViewById(R.id.tab);
        mNavigationController = pageBottomTabLayout.material()
                .addItem(R.drawable.home, "主页", getResources().getColor(R.color.light_blue))
                .addItem(R.drawable.education, "教务", getResources().getColor(R.color.tanedu))
                .addItem(R.drawable.community, "交流", getResources().getColor(R.color.ori_main_color))
                .addItem(R.drawable.setting, "设置", 0xFF455A64)
                .setDefaultColor(0x89FFFFFF)//未选中状态的颜色
                .setMode(MaterialMode.CHANGE_BACKGROUND_COLOR | MaterialMode.HIDE_TEXT)//这里可以设置样式模式，总共可以组合出4种效果
                .build();

        mNavigationController.setSelect(0);
        //设置Item选中事件的监听
        mNavigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
                switch (index) {
                    case 0:
                        fragmentTransaction.replace(R.id.fl_content, signFragment).commit();

                        break;
                    case 1:
                        fragmentTransaction.replace(R.id.fl_content, educationFragment).commit();

                        break;
                    case 2:
                        fragmentTransaction.replace(R.id.fl_content, communityFragment).commit();

                        break;
                    case 3:
                        fragmentTransaction.replace(R.id.fl_content, settingFragment).commit();

                        break;
                }
            }

            @Override
            public void onRepeat(int index) {
            }
        });

    }


    public void hidden_home_bottom() {
        pageBottomTabLayout.setVisibility(View.GONE);
    }

    public void show_home_bottom() {
        pageBottomTabLayout.setVisibility(View.VISIBLE);
    }

    private void initFunctionState() {
        FunctionStateUtil.get_function_state_document_download();
        FunctionStateUtil.get_function_state_entry();
        FunctionStateUtil.get_function_state_phone_verify();

    }

    public void onBackPressed() {
        show_home_bottom();
        int fragment_count = getSupportFragmentManager().getBackStackEntryCount();
        if (fragment_count == 1) {

            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            //moveTaskToBack(false);
        } else if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed();
        }else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }
}
