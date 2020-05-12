package com.wfj.bmobstudy.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.wfj.bmobstudy.Fragment.SetMidFragment.NewsInformFourFragment;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class NewsInfromPagerAdapter extends FragmentPagerAdapter {
    private String title[] = {"江理要闻", "通知公告", "学术动态", "校园快讯"};

    public NewsInfromPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        NewsInformFourFragment fragment = null;
        switch (position) {
            case 0:
                fragment = NewsInformFourFragment.getInstance("http://news.usts.edu.cn/news/news_more.asp?lm2=1");
                break;
            case 1:
                fragment = NewsInformFourFragment.getInstance("http://notify.usts.edu.cn/news/news_more.asp?lm2=1");
                break;
            case 2:
                fragment = NewsInformFourFragment.getInstance("http://notify.usts.edu.cn/news/news_more.asp?lm2=2");
                break;
            case 3:
                fragment = NewsInformFourFragment.getInstance("http://news.usts.edu.cn/news/news_more.asp?lm2=2");
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
