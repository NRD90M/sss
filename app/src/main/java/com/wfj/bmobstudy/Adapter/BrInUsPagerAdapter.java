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
    private String[] title = {"学院简介", "现任领导", "历任领导","师资队伍","院徽院训","学院风貌"};

    public BrInUsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        BrInUsItemFragment fragment = null;
        switch (position) {
            case 0:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/brinus1.html");//学院简介
                break;
            case 1:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/brinus2.html");//现任领导
                break;
            case 2:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/brinus3.html");//历任领导
                break;
            case 3:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/brinus4.html");//师资队伍
                break;
            case 4:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/brinus5.html");//院徽院训
                break;
//            case 5:
//                fragment = BrInUsItemFragment.newInstance("file:///android_asset/runschool.html");
//                break;
            case 5:
                fragment = BrInUsItemFragment.newInstance("file:///android_asset/culture.html");//学院风貌
                break;
//            case 7:
//                fragment = BrInUsItemFragment.newInstance("file:///android_asset/alumnus.html");
//                break;


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
