package com.wfj.bmobstudy.Fragment.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.bumptech.glide.Glide;
import com.wfj.bmobstudy.Activity.AppBaseActivity;
import com.wfj.bmobstudy.Adapter.BrInUsPagerAdapter;
import com.wfj.bmobstudy.Constant.UstsValue;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.ShowOrHiddenUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

/**
 * @description 学校概况
 * @date: 2020/4/27
 * @author: a */
public class brInUsActivity extends AppBaseActivity {
    private Context context;
    private LinearLayout ly_back;
    private ViewPager vp;
    private PagerSlidingTabStrip tabs;
    private ImageView iv_brinus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        initViews();
    }

    /**
     * 返回 Activity Layout
     *
     * @return Layout ID
     */
    @Override
    public int setLayoutView() {
        return R.layout.fragment_brinus;
    }

    protected void initViews() {
        // 初始化ViewPager并且添加适配器
        vp = (ViewPager) findViewById(R.id.vp);
        //避免第二次进入时，WebView不加载页面的问题
        vp.setAdapter(new BrInUsPagerAdapter(getSupportFragmentManager()));
        //向ViewPager绑定PagerSlidingTabStrip
        tabs = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        tabs.setViewPager(vp);

        iv_brinus = (ImageView) findViewById(R.id.iv_brinus);
        Glide.with(this).load(UstsValue.official_jl+"__local/8/25/0E/0BD48AC7F022A1389998DD0C47A_8B9C4A64_F605.jpg").centerCrop().into(iv_brinus);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                String url = "";
//                if (position <= 3) {
//                    url = "http://www.usts.edu.cn/static/images/ggtu" + (position + 1) + ".jpg";
//                } else if (position == 4) {
//                    url = "https://gss3.bdstatic.com/-Po3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=2248b54a69061d95694b3f6a1a9d61b4/e4dde71190ef76c62ca637599a16fdfaaf516748.jpg";
//                } else if (position == 5) {
//                    url = "https://gss0.bdstatic.com/-4o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=fd6657c4ce177f3e0439f45f11a650a2/1c950a7b02087bf4267f929bf5d3572c11dfcf35.jpg";
//                } else if (position == 6) {
//                    url = "https://gss3.bdstatic.com/-Po3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=a758f2d78a0a19d8df0e8c575293e9ee/2f738bd4b31c8701ca6c19802d7f9e2f0608fffa.jpg";
//                } else if (position == 7) {
//                    url = "https://gss2.bdstatic.com/9fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=c0a0fcb1d0ca7bcb6976cf7ddf600006/b2de9c82d158ccbfde2f70481ed8bc3eb1354135.jpg";
//                }
//                Glide.with(context).load(url).centerCrop().into(iv_brinus);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
