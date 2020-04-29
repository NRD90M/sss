package com.wfj.bmobstudy.Adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wfj.bmobstudy.Bean.Book;
import com.wfj.bmobstudy.R;

import java.util.List;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author:
 */
public class BookAdapter extends BaseQuickAdapter<Book, BaseViewHolder> {
    public BookAdapter(@LayoutRes int layoutResId, @Nullable List<Book> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Book item) {
        helper.setText(R.id.tv_book_name, item.getName())
                .setText(R.id.tv_book_author, item.getAuthor())
                .setText(R.id.tv_book_press, item.getPress())
                .setText(R.id.tv_book_language, item.getLanguage())
                .setText(R.id.tv_book_museum_collection, item.getMuseum_collection())
                .setText(R.id.tv_book_available, item.getAvailable());

    }
}
