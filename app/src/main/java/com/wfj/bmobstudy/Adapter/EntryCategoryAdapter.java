package com.wfj.bmobstudy.Adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wfj.bmobstudy.Bean.EntryCategory;
import com.wfj.bmobstudy.R;

import java.util.List;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class EntryCategoryAdapter extends BaseQuickAdapter<EntryCategory, BaseViewHolder> {
    public EntryCategoryAdapter(@LayoutRes int layoutResId, @Nullable List<EntryCategory> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EntryCategory item) {
        helper.setText(R.id.tv_entry_category, item.getName())
                .setText(R.id.tv_entry_category_number, item.getNumber() + "条");

    }
}
