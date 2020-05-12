package com.wfj.bmobstudy.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.jju.howe.howeassistant.activity.RobotMainActivity;
import com.wfj.bmobstudy.Activity.AoLanActivity;
import com.wfj.bmobstudy.Activity.AppBaseActivity;
import com.wfj.bmobstudy.Activity.LibraryActivity;

import com.wfj.bmobstudy.Activity.NewsInfoActivity;
import com.wfj.bmobstudy.Activity.NewsInformListActivity;
import com.wfj.bmobstudy.Adapter.NewsInfromPagerAdapter;
import com.wfj.bmobstudy.Bean.SlideShow;
import com.wfj.bmobstudy.Fragment.Entry.EntryCategoryActivity;
import com.wfj.bmobstudy.Fragment.SetMidFragment.CardActivity;
import com.wfj.bmobstudy.Fragment.SetMidFragment.GradeActivity;
import com.wfj.bmobstudy.Fragment.SetMidFragment.KwxfActiviy;

import com.wfj.bmobstudy.Fragment.SetMidFragment.PhoneActivity;
import com.wfj.bmobstudy.Fragment.SetMidFragment.SmallUtilActivity;
import com.wfj.bmobstudy.Fragment.Volunteer.VolunteerLoginActivity;
import com.wfj.bmobstudy.Fragment.activity.brInUsActivity;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.CallBackListener;
import com.wfj.bmobstudy.Utils.FunctionStateUtil;
import com.wfj.bmobstudy.Utils.GlideImageLoader;
import com.wfj.bmobstudy.Utils.ShowDialogUtil;
import com.wfj.bmobstudy.Utils.SlideShowUtil;
import com.wfj.bmobstudy.Utils.Toast;
import com.vondear.rxtools.view.RxToast;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @description 主页
 * @date: 2020/4/26
 * @author: a */
public class SignFragment extends Fragment  {
    private static final String TAG = "SignFragment";
    private String[] name = {
            "学校概况",
            "一卡通",
            "图书馆",
            "成绩查询",
            "学分查询",
            "奥蓝系统",
            "小工具",
            "志愿者时间",
            "办公电话",
            "江理词条"
    };
    private GridView gridView;

    /**
     * 未格式化的日期
     */
    private Date date;
    /**
     * 格式化后的日期,包含年月日时分秒
     */
    private String currentDate0;
    /**
     * 格式化后的日期,包含年月日
     */
    private String currentDate1;
    private TextView tv_title;

    private List<SlideShow> mslideShowList;
    //缓存Fragment View

    private Banner banner;

    private PagerSlidingTabStrip news_inform_tab;
    private ViewPager news_inform_vp;
//    private Fragment brInUsFragment;


    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //避免重复加载
        View v = null;
        if (rootView == null) {
            v = inflater.inflate(R.layout.fragment_sign, container, false);
            rootView = v;
            //轮播图
            initViews(v);
            //网格功能区初始化
            initGrdView(v);
            //导航标题栏初始化
            initNewsInformLayout(v);
        }
//        brInUsFragment=new BrInUsFragment();

        //缓存的rootView需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
//        ViewGroup parent = (ViewGroup) rootView.getParent();
//        if (parent != null) {
//          parent.removeView(rootView);
//            Log.d(TAG, "onCreateView: "+isRemoving());
//         }
        return rootView;
    }

    private void initViews(View v) {
        get_info(v);

    }

    private void initGrdView(View v) {
        gridView = (GridView) v.findViewById(R.id.gv);

        //生成动态数组，并且转入数据
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ItemImage", getObject("university"));//添加图像资源的ID
        map.put("ItemText", name[0]);//按序号做ItemText
        lstImageItem.add(map);
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("ItemImage", getObject("ecard"));//添加图像资源的ID
        map1.put("ItemText", name[1]);//按序号做ItemText
        lstImageItem.add(map1);
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("ItemImage", getObject("library"));//添加图像资源的ID
        map2.put("ItemText", name[2]);//按序号做ItemText
        lstImageItem.add(map2);
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put("ItemImage", getObject("grade"));//添加图像资源的ID
        map3.put("ItemText", name[3]);//按序号做ItemText
        lstImageItem.add(map3);
        HashMap<String, Object> map4 = new HashMap<String, Object>();
        map4.put("ItemImage", getObject("credit"));//添加图像资源的ID
        map4.put("ItemText", name[4]);//按序号做ItemText
        lstImageItem.add(map4);
        HashMap<String, Object> map5 = new HashMap<String, Object>();
        map5.put("ItemImage", getObject("aolan"));//添加图像资源的ID
        map5.put("ItemText", name[5]);//按序号做ItemText
        lstImageItem.add(map5);
        HashMap<String, Object> map6 = new HashMap<String, Object>();
        map6.put("ItemImage", getObject("util"));//添加图像资源的ID
        map6.put("ItemText", name[6]);//按序号做ItemText
        lstImageItem.add(map6);
        HashMap<String, Object> map7 = new HashMap<String, Object>();
        map7.put("ItemImage", getObject("volunteer"));//添加图像资源的ID
        map7.put("ItemText", name[7]);//按序号做ItemText
        lstImageItem.add(map7);
        HashMap<String, Object> map8 = new HashMap<String, Object>();
        map8.put("ItemImage", getObject("phone"));//添加图像资源的ID
        map8.put("ItemText", name[8]);//按序号做ItemText
        lstImageItem.add(map8);
        HashMap<String, Object> map9 = new HashMap<String, Object>();
        map9.put("ItemImage", getObject("entry"));//添加图像资源的ID
        map9.put("ItemText", name[9]);//按序号做ItemText
        lstImageItem.add(map9);
        //生成适配器的ImageItem 与动态数组的元素相对应
        SimpleAdapter saImageItems = new SimpleAdapter(getActivity(),
                lstImageItem,//数据来源
                R.layout.item_grid,//item的XML实现

                //动态数组与ImageItem对应的子项
                new String[]{"ItemImage", "ItemText"},

                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[]{R.id.iv_grid, R.id.tv_grid});

        //添加并且显示
        gridView.setAdapter(saImageItems);
        //添加消息处理
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://学校概况
                        myStartActivity(getActivity(), brInUsActivity.class);
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,brInUsFragment).addToBackStack(null).commit();
                        break;
                    case 1://一卡通
                        myStartActivity(getActivity(),CardActivity.class);
                        break;
                    case 2://图书馆
                        myStartActivity(getActivity(),LibraryActivity.class);
                        break;
                    case 3://成绩查询
                        myStartActivity(getActivity(),GradeActivity.class);
                        break;
                    case 4://学分查询
                        myStartActivity(getActivity(),KwxfActiviy.class);
                        break;
                    case 5://LANAO
                        myStartActivity(getActivity(),AoLanActivity.class);
                        break;
                    case 6:// 小工具
                        myStartActivity(getActivity(),SmallUtilActivity.class);
                        break;
                    case 7://  志愿者时间
                        myStartActivity(getActivity(),VolunteerLoginActivity.class);
                        break;
                    case 8:// 办公电话
                        myStartActivity(getActivity(),PhoneActivity.class);
                        break;
                    case 9://江理词条
                        if (FunctionStateUtil.Entry) {
                            myStartActivity(getActivity(),EntryCategoryActivity.class);
                        } else {
                            RxToast.info("江理词条正在调整中！");
                        }
                        break;

                }
            }
        });
    }

    /*
    * 跳转class
    * */
    private void myStartActivity(Context context, Class className) {
        Intent startIntent = new Intent(context, className);
        context.startActivity(startIntent);
        getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    public Object getObject(String name) {
        Resources res = getResources();
        return res.getIdentifier(name, "drawable", getActivity().getPackageName());
    }

    private void get_info(final View v) {
        //获取轮播图的图片地址，标题与详细内容的url
        SlideShowUtil.get_slideShow_info(new CallBackListener() {
            @Override
            public void onSuccess(List<SlideShow> list) {
                mslideShowList = new ArrayList<>();
                mslideShowList = SlideShowUtil.slideShowsList;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initBanner(v);
                        Toast.show(getActivity(), "加载成功！", 1000);

                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.show(getActivity(), "加载失败！", 1000);
                    }
                });
            }
        });
    }


    private void initBanner(View v) {
        banner = (Banner) v.findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<String> img_url = new ArrayList<>();
        List<String> title = new ArrayList<>();
//        final List<String> detail_url = new ArrayList<>();
        for (SlideShow slideShow : mslideShowList) {
            img_url.add(slideShow.getImg_url());
            title.add(slideShow.getTitle());
//            detail_url.add(slideShow.getDetail_url());
        }
        banner.setImages(img_url);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(title);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //此时需要暂停轮播。否则返回时与当前点击的轮播页面不一致
                banner.stopAutoPlay();
//                Intent i = new Intent(getActivity(), NewsInfoActivity.class);
//                i.putExtra("url", detail_url.get(position));
//                startActivity(i);
//                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();


    }

    @Override
    public void onResume() {
        super.onResume();
        if (banner != null) {
            //返回时，开启轮播
            banner.startAutoPlay();
        }
    }

    /**
     * 初始化PagerSlidingTabStrip以及ViewPager
     */
    private void initNewsInformLayout(View v) {
        news_inform_vp = (ViewPager) v.findViewById(R.id.news_inform_vp);
        news_inform_tab = (PagerSlidingTabStrip) v.findViewById(R.id.news_inform_tab);
        news_inform_vp.setAdapter(new NewsInfromPagerAdapter(getChildFragmentManager()));
        news_inform_tab.setViewPager(news_inform_vp);


        TextView tv_more_news_inform = (TextView) v.findViewById(R.id.tv_more_news_inform);
        tv_more_news_inform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogUtil.showProgressDialog(getActivity(), "正在拉取新闻...");
                myStartActivity(getContext(),NewsInformListActivity.class);
            }
        });
    }

}
