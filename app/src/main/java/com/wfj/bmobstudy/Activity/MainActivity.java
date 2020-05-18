package com.wfj.bmobstudy.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.Utils;

import androidx.viewpager.widget.ViewPager;
import cn.droidlover.xrichtext.FloatBallManager;
import cn.droidlover.xrichtext.menu.MenuItem;
import cn.droidlover.xrichtext.utils.dpUtils;

import com.jju.howe.howeassistant.activity.RobotMainActivity;
import com.wfj.bmobstudy.Adapter.FragmentViewPagerAdapter;
import com.wfj.bmobstudy.Fragment.CommunityFragment;
import com.wfj.bmobstudy.Fragment.CourseFragment;
import com.wfj.bmobstudy.Fragment.SettingFragment;
import com.wfj.bmobstudy.Fragment.SignFragment;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.BackHandlerHelper;
import com.wfj.bmobstudy.Utils.Entry.EntryUtil;
import com.wfj.bmobstudy.Utils.FunctionStateUtil;
import com.wfj.bmobstudy.View.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import cn.droidlover.xrichtext.BackGroudSeletor;
import cn.droidlover.xrichtext.floatball.FloatBallCfg;
import cn.droidlover.xrichtext.menu.FloatMenuCfg;
import me.majiajie.pagerbottomtabstrip.MaterialMode;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;


/**
 * @description MainActivity
 * @date: 2020/4/26
 * @author: a */
public class MainActivity extends AppBaseActivity {
    private NavigationController mNavigationController;
    private PageNavigationView pageBottomTabLayout;
    public MyViewPager viewPager;
    private FloatBallManager mFloatballManager;

    private int curPosition = 0;

    //保存 主页 Fragment 的视图
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.init(getApplication());
        boolean showMenu = false;//初始化是否显示悬浮按钮
        //悬浮按钮初始化
        initSinglePageFloatball(showMenu);
        initViews();
        initTab();
        initFunctionState();
        //获取服务器时间
        EntryUtil.get_current_time();
        //5 如果没有添加菜单，可以设置悬浮球点击事件
        if (mFloatballManager.getMenuItemSize() == 0) {
            mFloatballManager.setOnFloatBallClickListener(new FloatBallManager.OnFloatBallClickListener() {
                @Override
                public void onFloatBallClick() {
                    Intent intent = new Intent(MainActivity.this, RobotMainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                }
            });
        }
    }
    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        //只有activity被添加到windowmanager上以后才可以调用show方法。
        mFloatballManager.show();
    }
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mFloatballManager.hide();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }

    private void initSinglePageFloatball(boolean showMenu) {
        //1 初始化悬浮球配置，定义好悬浮球大小和icon的drawable
        int ballSize = dpUtils.dpToPx(120, getResources());
        Drawable ballIcon = BackGroudSeletor.getdrawble("ic_floatball", this);
        FloatBallCfg ballCfg = new FloatBallCfg(ballSize, ballIcon, FloatBallCfg.Gravity.RIGHT_CENTER);
        //设置悬浮球不半隐藏
//        ballCfg.setHideHalfLater(false);
        if (showMenu) {
            //2 需要显示悬浮菜单
            //2.1 初始化悬浮菜单配置，有菜单item的大小和菜单item的个数
            int menuSize = dpUtils.dpToPx(180, getResources());
            int menuItemSize = dpUtils.dpToPx(40, getResources());
            FloatMenuCfg menuCfg = new FloatMenuCfg(menuSize, menuItemSize);
            //3 生成floatballManager
            //必须传入Activity
            mFloatballManager = new FloatBallManager(this, ballCfg, menuCfg);
            addFloatMenuItem();
        } else {
            //必须传入Activity
            mFloatballManager = new FloatBallManager(this, ballCfg);
        }
    }

    //跳转 图标应用
    private void addFloatMenuItem() {
        MenuItem personItem = new MenuItem(BackGroudSeletor.getdrawble("ic_weixin", this)) {
            @Override
            public void action() {
                toast("打开微信");
                mFloatballManager.closeMenu();
            }
        };
        MenuItem walletItem = new MenuItem(BackGroudSeletor.getdrawble("ic_weibo", this)) {
            @Override
            public void action() {
                toast("打开微博");
            }
        };
        MenuItem settingItem = new MenuItem(BackGroudSeletor.getdrawble("ic_email", this)) {
            @Override
            public void action() {
                toast("打开邮箱");
                mFloatballManager.closeMenu();
            }
        };
        mFloatballManager.addMenuItem(personItem)
                .addMenuItem(walletItem)
                .addMenuItem(personItem)
                .addMenuItem(walletItem)
                .addMenuItem(settingItem)
                .buildMenu();
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
        viewPager=findViewById(R.id.viewPager);
        viewPager.setScrollble(false);//ViewPager是否可以滑动
        fragmentList = new ArrayList<>();
        fragmentList.add(new SignFragment());
        fragmentList.add(new CourseFragment());
        fragmentList.add(new CommunityFragment());
        fragmentList.add(new SettingFragment());

        viewPager.setAdapter(new FragmentViewPagerAdapter(getSupportFragmentManager(),viewPager,fragmentList));
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
////                switch (position){
////                    case 0:
////                        mNavigationController.setSelect(0);
////                        break;
////                    case 1:
////                        mNavigationController.setSelect(1);
////                        break;
////                    case 2:
////                        mNavigationController.setSelect(2);
////                        break;
////                    case 3:
////                        mNavigationController.setSelect(3);
////                        break;
////
////                }
//
//            }
//
//            @Override
//            public void onPageSelected(final int position) {
////                setNavChange(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }
    //点击底部导航 同步
    private void setNavChange(int position) {
        int keyAt = mNavigationController.getSelected();
        if (curPosition == keyAt){
            return;
        }
        curPosition = keyAt;
    }

    private void initTab() {
        pageBottomTabLayout = (PageNavigationView) findViewById(R.id.tab);
        mNavigationController = pageBottomTabLayout.material()
                .addItem(R.drawable.home, "主页", getResources().getColor(R.color.light_blue))
                .addItem(R.drawable.education, "学习", getResources().getColor(R.color.tanedu))
                .addItem(R.drawable.community, "知识网", getResources().getColor(R.color.ori_main_color))
                .addItem(R.drawable.setting, "设置", 0xFF455A64)
                .setDefaultColor(0x89FFFFFF)//未选中状态的颜色
                .setMode(MaterialMode.CHANGE_BACKGROUND_COLOR | MaterialMode.HIDE_TEXT)//这里可以设置样式模式，总共可以组合出4种效果
                .build();

        mNavigationController.setSelect(0);


        //设置Item选中事件的监听
        mNavigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                viewPager.setCurrentItem(index);
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
        viewPager.setVisibility(View.VISIBLE);
    }

    private void initFunctionState() {
        FunctionStateUtil.get_function_state_document_download();
        FunctionStateUtil.get_function_state_entry();
        FunctionStateUtil.get_function_state_phone_verify();

    }

    public void onBackPressed() {

        //当fragment点击返回时，显示底部和viewPager
        show_home_bottom();
//        int fragment_count = getSupportFragmentManager().getBackStackEntryCount();
       /* if (fragment_count == 0) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            //moveTaskToBack(false);
        } else */
//       if (!BackHandlerHelper.handleBackPress(this)) {
//            super.onBackPressed();
//        }else {
//            super.onBackPressed();
//        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
