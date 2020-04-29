package com.wfj.bmobstudy.Adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wfj.bmobstudy.Bean.Book_recommend;
import com.wfj.bmobstudy.R;

import java.util.List;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class BookRecommendAdapter extends BaseQuickAdapter<Book_recommend, BaseViewHolder> {
    public BookRecommendAdapter(@LayoutRes int layoutResId, @Nullable List<Book_recommend> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Book_recommend item) {
        helper.setText(R.id.tv_book_name, item.getName())
                .setText(R.id.tv_book_author, item.getAuthor())
                .setText(R.id.tv_book_press, item.getPress())
                .setText(R.id.tv_book_number, "索书号:" + item.getNumber())
                .setText(R.id.tv_book_collection, "馆藏:" + item.getCollection())
                .setText(R.id.tv_book_lend, "借阅次数:" + item.getLend())
                .setText(R.id.tv_book_rate, "借阅比:" + item.getRate());


    }
}
