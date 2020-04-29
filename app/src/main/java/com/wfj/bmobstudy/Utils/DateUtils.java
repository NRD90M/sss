package com.wfj.bmobstudy.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author:
 */
public class DateUtils {
    public static String dateFormat(Date date, int tag) {
        String format = null;
        if (tag == 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format = simpleDateFormat.format(date);
        }
        if (tag == 1) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            format = simpleDateFormat.format(date);
        }
        return format;
    }
}
