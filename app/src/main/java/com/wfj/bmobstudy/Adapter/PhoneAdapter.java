package com.wfj.bmobstudy.Adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wfj.bmobstudy.Bean.DepartmentPhone;
import com.wfj.bmobstudy.R;

import java.util.List;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author:
 */
public class PhoneAdapter extends BaseQuickAdapter<DepartmentPhone, BaseViewHolder> {

    public PhoneAdapter(@LayoutRes int layoutResId, @Nullable List<DepartmentPhone> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DepartmentPhone item) {
        helper.setText(R.id.tv_position, item.getPosition())
                .setText(R.id.tv_phone, item.getPhone());
    }
}
