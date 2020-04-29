package com.wfj.bmobstudy.Utils;

import org.jsoup.nodes.Document;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public interface Call {
    void onSuccess(Document document);
    void onFailure(Exception e);
}
