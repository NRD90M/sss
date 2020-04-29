package com.wfj.bmobstudy.Utils;

import android.app.Activity;
import android.content.res.Resources;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class ResoruceObjectUtil {
    public static Object getObject(Activity activity, String name) {
        Resources res = activity.getResources();
        return res.getIdentifier(name, "drawable", activity.getPackageName());
    }
}
