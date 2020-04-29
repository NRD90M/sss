package com.wfj.bmobstudy.Adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wfj.bmobstudy.Bean.Knowledge;
import com.wfj.bmobstudy.R;

import java.util.List;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class KnowledgeAdapter extends BaseQuickAdapter<Knowledge, BaseViewHolder> {
    public KnowledgeAdapter(@LayoutRes int layoutResId, @Nullable List<Knowledge> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Knowledge item) {
        helper.setText(R.id.tv_knowledge_title, item.getTitle())
                .setText(R.id.tv_knowledge_time, item.getTime())
                .setText(R.id.tv_knowledge_category, item.getCategory());
        ImageView iv = helper.getView(R.id.iv_knowledge);
        if (TextUtils.isEmpty(item.getPic_url())) {
            iv.setVisibility(View.GONE);
        } else {
            iv.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getPic_url()).centerCrop().into(iv);
        }
    }
}
