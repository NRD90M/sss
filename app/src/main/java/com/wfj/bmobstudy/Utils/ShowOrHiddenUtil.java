package com.wfj.bmobstudy.Utils;

import android.app.Activity;
import android.view.View;

import com.wfj.bmobstudy.R;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class ShowOrHiddenUtil {
    public static void show_home_bottom(Activity activity) {
        activity.findViewById(R.id.tab).setVisibility(View.VISIBLE);
    }

    public static void hidden_home_bottom(Activity activity) {
        activity.findViewById(R.id.tab).setVisibility(View.GONE);
    }

    public static int get_bottom(Activity activity){
        return activity.findViewById(R.id.tab).getVisibility();
    }

}
