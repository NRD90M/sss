package com.wfj.bmobstudy.Adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wfj.bmobstudy.Bean.Message;
import com.wfj.bmobstudy.R;

import java.util.List;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class MyMessageAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {
    public MyMessageAdapter(@LayoutRes int layoutResId, @Nullable List<Message> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        helper.setText(R.id.tv_message_title, item.getTitle())
                .setText(R.id.tv_message_time, item.getCreatedAt())
                .setText(R.id.tv_message_author, item.getAuthor());

    }


}
