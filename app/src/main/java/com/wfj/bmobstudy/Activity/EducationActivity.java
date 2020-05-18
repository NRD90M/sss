package com.wfj.bmobstudy.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.material.appbar.AppBarLayout;
import com.wfj.bmobstudy.Activity.AppBaseActivity;
import com.wfj.bmobstudy.Adapter.EducationPagerAdapter;
import com.wfj.bmobstudy.Fragment.Education.EducationListFragment;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.Education.EducationBannerUtil;
import com.wfj.bmobstudy.Utils.GlideImageLoader;
import com.wfj.bmobstudy.Utils.Toast;
import com.wfj.bmobstudy.View.MyGridView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @description 教务界面
 * @date: 2020/4/26
 * @author: a */
public class EducationActivity extends AppBaseActivity {
    private TextView tv_more_education;
    private Banner banner;
    private MyGridView gv_gonggao;
    private MyGridView gv_info_gongkai;
    private PagerSlidingTabStrip tab_education;
    private ViewPager vp_education;
    private STATE abl_state;
    private Context context;
    //展开、折叠、中间
    private enum STATE {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    private AppBarLayout abl_education;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        return R.layout.fragment_education;
    }

    public void initViews() {
        EducationBannerUtil.get_banner_list(new EducationBannerUtil.get_bannerCall() {
            @Override
            public void success(final List<String> list) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initBanner(list);
                    }
                });
            }
        });
        initGrdView_gonggao();
        initGrdView_info_gongkai();
        initEducationLayout();
        initAppBarLayout();
        tv_more_education = (TextView) findViewById(R.id.tv_more_education);
        tv_more_education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = tv_more_education.getText().toString();
                if (text.equals("回到顶部")) {
                    setAblExpanded(true);
                } else {
                    setAblExpanded(false);
                }
            }
        });


    }

    //初始化banner
    private void initBanner( List<String> list) {
        banner = (Banner) findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.show_info(context, "该图片不包括内容！");
            }
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    //初始化公告GridView
    private void initGrdView_gonggao() {
        gv_gonggao = (MyGridView) findViewById(R.id.gv_gonggao);
        String img[] = new String[]{"transaction_guide","exam_know", "table_download" , "school_manage"};
        final String name[] = new String[]{ "质量评价","考试管理", "学籍管理", "选课规定"};
        //生成动态数组，并且转入数据

        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < img.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", getObject(img[i]));//添加图像资源的ID
            map.put("ItemText", name[i]);//按序号做ItemText
            lstImageItem.add(map);
        }
        //生成适配器的ImageItem 与动态数组的元素相对应
        SimpleAdapter saImageItems = new SimpleAdapter(context,
                lstImageItem,//数据来源
                R.layout.item_grid,//item的XML实现

                //动态数组与ImageItem对应的子项
                new String[]{"ItemImage", "ItemText"},

                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[]{R.id.iv_grid, R.id.tv_grid});

        //添加并且显示
        gv_gonggao.setAdapter(saImageItems);
        //添加消息处理
        final String url[] = {
                "http://jw.asc.jx.cn/_data/read_detail_glgd.aspx?rid=2",//质量管理
                "http://jw.asc.jx.cn/_data/read_detail_glgd.aspx?rid=3",//考试
                "http://jw.asc.jx.cn/_data/read_detail_glgd.aspx?rid=4",//学籍
                "http://jw.asc.jx.cn/_data/read_detail_glgd.aspx?rid=5"};//选课
        gv_gonggao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                EducationListFragment fragment = new EducationListFragment();
                Bundle bundle = new Bundle();
                switch (position) {
                    case 0:
                        bundle.putString("url", url[0]);
                        bundle.putString("category", name[0]);
                        break;
                    case 1:
                        bundle.putString("url", url[1]);
                        bundle.putString("category", name[1]);
                        break;
                    case 2:
                        bundle.putString("url", url[2]);
                        bundle.putString("category", name[2]);
                        break;
                    case 3:
                        bundle.putString("url", url[3]);
                        bundle.putString("category", name[3]);
                        break;
                }
                fragment.setArguments(bundle);
                transaction
                        .setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left)
                        .replace(R.id.fl_content, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


    //初始化信息公开GridView
    private void initGrdView_info_gongkai() {
        gv_info_gongkai = (MyGridView) findViewById(R.id.gv_info_gongkai);
        String img[] = new String[]{"service_guide", "calendar","external_exam", "course_query"};
        final String name[] = new String[]{"作息时间", "查看校历", "教师课表","班级课表"};
        //生成动态数组，并且转入数据

        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < img.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", getObject(img[i]));//添加图像资源的ID
            map.put("ItemText", name[i]);//按序号做ItemText
            lstImageItem.add(map);
        }
        //生成适配器的ImageItem 与动态数组的元素相对应
        SimpleAdapter saImageItems = new SimpleAdapter(context,
                lstImageItem,//数据来源
                R.layout.item_grid,//item的XML实现

                //动态数组与ImageItem对应的子项
                new String[]{"ItemImage", "ItemText"},

                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[]{R.id.iv_grid, R.id.tv_grid});

        //添加并且显示
        gv_info_gongkai.setAdapter(saImageItems);
        //添加消息处理
        final String url[] = {
                "http://jw.asc.jx.cn/_data/index_zxsj.aspx",//作息时间
                "http://jw.asc.jx.cn/_data/index_lookxl.aspx",//查看校历
                "http://jw.asc.jx.cn/ZNPK/TeacherKBFB.aspx",//教师课表
                "http://jw.asc.jx.cn/_data/index_GLRY.aspx"};//班级课表
        gv_info_gongkai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                EducationListFragment fragment = new EducationListFragment();
                Bundle bundle = new Bundle();
                switch (position) {
                    case 0:
                        bundle.putString("url", url[0]);
                        bundle.putString("category", name[0]);
                        break;
                    case 1:
                        bundle.putString("url", url[1]);
                        bundle.putString("category", name[1]);
                        break;
                    case 2:
                        bundle.putString("url", url[2]);
                        bundle.putString("category", name[2]);
                        break;
                    case 3:
                        bundle.putString("url", url[3]);
                        bundle.putString("category", name[3]);
                        break;
                }
                fragment.setArguments(bundle);
                transaction
                        .setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left)
                        .replace(R.id.fl_content, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    /**
     * 初始化PagerSlidingTabStrip以及ViewPager
     */
    private void initEducationLayout() {
        vp_education = (ViewPager) findViewById(R.id.vp_education);
        tab_education = (PagerSlidingTabStrip) findViewById(R.id.tab_education);
        vp_education.setAdapter(new EducationPagerAdapter(getSupportFragmentManager()));
        tab_education.setViewPager(vp_education);
    }

    private void initAppBarLayout() {
        abl_education = (AppBarLayout) findViewById(R.id.abl_education);
        abl_education.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (abl_state != STATE.EXPANDED) {
                        abl_state = STATE.EXPANDED;//修改状态标记为展开
                        tv_more_education.setText("展开");
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (abl_state != STATE.COLLAPSED) {
                        abl_state = STATE.COLLAPSED;//修改状态标记为折叠
                        tv_more_education.setText("回到顶部");
                    }
                } else {
                    if (abl_state != STATE.INTERNEDIATE) {
                        abl_state = STATE.INTERNEDIATE;//修改状态标记为中间

                    }
                }
            }
        });

    }

    //设置展开或伸缩
    public void setAblExpanded(Boolean b) {
        if (b) {
            abl_education.setExpanded(true);
            abl_state = STATE.EXPANDED;
        } else {
            abl_education.setExpanded(false);
            abl_state = STATE.COLLAPSED;
        }

    }

    public Object getObject(String name) {
        Resources res = getResources();
        return res.getIdentifier(name, "drawable", getPackageName());
    }


    @Override
    public void onResume() {
        super.onResume();
        if (banner != null) {
            banner.startAutoPlay();
        }
    }
}
