package com.wfj.bmobstudy.Adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wfj.bmobstudy.Bean.Entry;
import com.wfj.bmobstudy.R;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class EntryAdapter extends BaseQuickAdapter<Entry, BaseViewHolder> {
    public EntryAdapter(@LayoutRes int layoutResId, @Nullable List<Entry> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Entry item) {
        helper.setText(R.id.tv_entry_title, item.getTitle())
                .setText(R.id.tv_entry_content, item.getContent())
                .setText(R.id.tv_entry_author, item.getAuthor().getUsername())
                .setText(R.id.tv_entry_time, item.getCreatedAt())
                .setText(R.id.tv_entry_stars, item.getStars() + "");
        try {
            BmobFile file = item.getAuthor().getFile();
            String url = file.getFileUrl();

            Glide.with(mContext).load(url).into((ImageView) helper.getView(R.id.civ_entry_head));
        } catch (NullPointerException e) {
            //当前用户无头像
            helper.setImageResource(R.id.civ_entry_head, R.mipmap.ic_launcher_round);
        }
    }


}
