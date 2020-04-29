package com.wfj.bmobstudy.Utils;

import com.wfj.bmobstudy.Bean.SlideShow;

import java.util.List;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public interface CallBackListener {
    void onSuccess(List<SlideShow> list);
    void onFailure(Exception e);


}
