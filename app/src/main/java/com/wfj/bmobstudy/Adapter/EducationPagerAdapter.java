package com.wfj.bmobstudy.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.wfj.bmobstudy.Fragment.Education.EducationFourFragment;
import com.wfj.bmobstudy.Utils.Education.EducationOptionalCourseListUtil;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class EducationPagerAdapter extends FragmentPagerAdapter {
    private String title[] = {"石湖校区", "江枫校区", "天平校区"};

    public EducationPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        EducationFourFragment fragment = null;
        switch (position) {
            case 0:
                fragment = EducationFourFragment.getInstance(EducationOptionalCourseListUtil.url[0]);
                break;
            case 1:
                fragment = EducationFourFragment.getInstance(EducationOptionalCourseListUtil.url[1]);
                break;
            case 2:
                fragment = EducationFourFragment.getInstance(EducationOptionalCourseListUtil.url[2]);
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
