package com.wfj.bmobstudy.Adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wfj.bmobstudy.Bean.EducationOptionalCourse;
import com.wfj.bmobstudy.R;

import java.util.List;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class EducationOptionalCourseAdapter extends BaseQuickAdapter<EducationOptionalCourse, BaseViewHolder> {
    public EducationOptionalCourseAdapter(@LayoutRes int layoutResId, @Nullable List<EducationOptionalCourse> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EducationOptionalCourse item) {
        helper.setText(R.id.tv_education_title, item.getTitle())
                .setText(R.id.tv_education_time, item.getTime());
    }

}
