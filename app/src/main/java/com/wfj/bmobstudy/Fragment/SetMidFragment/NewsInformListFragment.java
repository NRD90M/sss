package com.wfj.bmobstudy.Fragment.SetMidFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.wfj.bmobstudy.Bean.NewsInform;

import com.wfj.bmobstudy.R;

import java.util.List;


/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class NewsInformListFragment extends Fragment {
    private String categories_url[] = {
            "http://news.usts.edu.cn/news/news_more.asp?lm2=1",
            "http://notify.usts.edu.cn/news/news_more.asp?lm2=1",
            "http://notify.usts.edu.cn/news/news_more.asp?lm2=2",
            "http://news.usts.edu.cn/news/news_more.asp?lm2=2"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_inform_list, container, false);
        initViews(v);
        return v;
    }


    private void initViews(View v) {

    }


    private void initDatas() {

    }

    private void initBanner(View v, List<NewsInform> list) {
//        banner = (Banner) v.findViewById(R.id.banner_news_inform);
//        //设置banner样式
//        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
//        //设置图片加载器
//        banner.setImageLoader(new GlideImageLoader());
//        //设置图片集合
//        List<String> title = new ArrayList<>();
//        title.add(list.get(0).getTitle());
//        List<String> pic_url = new ArrayList<>();
//        pic_url.add("http://news.usts.edu.cn/news/uploadfiles/201801/20180128115535668.jpg");
//
//        banner.setImages(pic_url);
//        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.Default);
//        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(title);
//        //设置自动轮播，默认为true
//        banner.isAutoPlay(true);
//        //设置轮播时间
//        banner.setDelayTime(3000);
//        //设置指示器位置（当banner模式中有指示器时）
//        banner.setIndicatorGravity(BannerConfig.CENTER);
//
//        //banner设置方法全部调用完毕时最后调用
//        banner.start();

    }


}
