package com.wfj.bmobstudy.Adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wfj.bmobstudy.Bean.NewsInform;
import com.wfj.bmobstudy.R;

import java.util.List;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class NewsInformAdapter extends BaseQuickAdapter<NewsInform, BaseViewHolder> {
    public NewsInformAdapter(@LayoutRes int layoutResId, @Nullable List<NewsInform> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsInform item) {
        helper.setText(R.id.tv_news_inform_title, item.getTitle())
                .setText(R.id.tv_news_inform_time, item.getTime().substring(1, item.getTime().length() - 1))
                .setText(R.id.tv_news_inform_read, item.getRead());
        //.setImageResource(R.id.iv_news_inform, R.mipmap.ic_launcher);
        if (item.getFirst_pic_url() == "") {
            helper.getView(R.id.iv_news_inform).setVisibility(View.GONE);
        } else {
            Glide.with(mContext).load(item.getFirst_pic_url()).crossFade().into((ImageView) helper.getView(R.id.iv_news_inform));
        }


    }

}
