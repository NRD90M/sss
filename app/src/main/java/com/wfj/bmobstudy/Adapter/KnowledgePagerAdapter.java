package com.wfj.bmobstudy.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.wfj.bmobstudy.Bean.KnowledgeTab;
import com.wfj.bmobstudy.Fragment.Knowledge.KnowledgeListFragment;
import com.wfj.bmobstudy.Utils.Knowledge.KnowledgeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class KnowledgePagerAdapter extends FragmentPagerAdapter {
    private List<KnowledgeTab> tabList = new ArrayList<>();

    public KnowledgePagerAdapter(FragmentManager fm) {
        super(fm);
        delete_two();
    }

    //去除校花与经验标签
    public void delete_two() {
        for (int i = 0; i < KnowledgeUtil.tabList.size(); i++) {
            if (i != 10 && i != 13) {
                tabList.add(KnowledgeUtil.tabList.get(i));
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        KnowledgeListFragment fragment = KnowledgeListFragment.getInstance(tabList.get(position).getUrl());
        return fragment;
    }

    @Override
    public int getCount() {
        return tabList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position).getName();
    }


}
