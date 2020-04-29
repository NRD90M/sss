package com.wfj.bmobstudy.Utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class FragmentUtil {
    public static void switchFragment(FragmentManager fragmentManager, int from_content, Fragment to, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(from_content);
        Log.e("tag","现存"+fragment.toString());
        if (to.isAdded()) {
            fragmentTransaction.hide(fragment).show(to);
            Log.e("tag","已经添加"+to.toString());
        } else {
            fragmentTransaction.hide(fragment).add(from_content, to);
            Log.e("tag","未添加"+to.toString());
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null).commit();
        } else {
            fragmentTransaction.commit();
        }


    }
}
