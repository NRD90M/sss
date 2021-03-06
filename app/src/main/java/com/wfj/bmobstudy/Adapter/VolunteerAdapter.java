package com.wfj.bmobstudy.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wfj.bmobstudy.Bean.Volunteer;
import com.wfj.bmobstudy.R;

import java.util.List;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class VolunteerAdapter extends BaseQuickAdapter<Volunteer, BaseViewHolder> {
    public VolunteerAdapter(int layoutResId, List<Volunteer> data) {
        super(layoutResId, data);
    }

    protected void convert(BaseViewHolder helper, Volunteer item) {
        helper.setText(R.id.tv_volunteer_item_number, item.getNumber())
                .setText(R.id.tv_volunteer_item_name, item.getActivity())
                .setText(R.id.tv_volunteer_item_time, item.getTime())
                .setText(R.id.tv_volunteer_item_state, item.getState());
    }
}
