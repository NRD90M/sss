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
    private String title[] = {"学院新闻", "媒体关注", "系部动态","通知公告"};

    public NewsInfromPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        NewsInformFourFragment fragment = null;
        switch (position) {
            case 0://学院新闻
                fragment = NewsInformFourFragment.getInstance("http://www.asc.jx.cn/ykxw/xyxw.htm");
                break;
            case 1://媒体关注
                fragment = NewsInformFourFragment.getInstance("http://www.asc.jx.cn/ykxw/mtgz.htm");
                break;
            case 2://系部动态
                fragment = NewsInformFourFragment.getInstance("http://www.asc.jx.cn/ykxw/xbdt.htm");
                break;
            case 3://通知公告
                fragment = NewsInformFourFragment.getInstance("http://www.asc.jx.cn/ykxw/tzgg.htm");
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
