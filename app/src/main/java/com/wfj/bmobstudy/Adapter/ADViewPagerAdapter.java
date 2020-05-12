package com.wfj.bmobstudy.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

public class ADViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<ImageView> list;

    public ADViewPagerAdapter(Context context, List<ImageView> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView(list.get(position));
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position){
        container.addView(list.get(position));
        return list.get(position);
    }
}
