package com.wfj.bmobstudy.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.wfj.bmobstudy.Fragment.SetMinFragment.BrInUsItemFragment;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class BrInUsPagerAdapter extends FragmentPagerAdapter {
    private String[] title = {"学校简介", "校区介绍", "现任领导", "校歌校徽","历史沿革","办学条件","文化传统","知名校友"};

    public BrInUsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        BrInUsItemFragment fragment = null;
        switch (position) {
            case 0:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/brinus1.html");
                break;
            case 1:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/brinus2.html");
                break;
            case 2:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/brinus3.html");
                break;
            case 3:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/brinus4.html");
                break;
            case 4:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/history.html");
                break;
            case 5:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/runschool.html");
                break;
            case 6:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/culture.html");
                break;
            case 7:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/alumnus.html");
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
