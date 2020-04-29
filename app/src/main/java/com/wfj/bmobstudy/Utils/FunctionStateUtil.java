package com.wfj.bmobstudy.Utils;

import com.wfj.bmobstudy.Bean.FunctionState;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @description 管理各个子功能是否开启
 * @date: 2020/4/26
 * @author: a */
public class FunctionStateUtil {
    //决定百度文档下载功能是否开放
    public static boolean BaiDuDocumentDownload = true;
    //决定苏科词条功能是否开放
    public static boolean Entry = true;
    //决定获取手机验证码功能是否开放
    public static boolean PhoneVerifyCode = true;

    public static void get_function_state_document_download() {
        BmobQuery<FunctionState> query = new BmobQuery<>();
        query.addWhereEqualTo("name", "BaiDuDocumentDownload");
        query.findObjects(new FindListener<FunctionState>() {
            @Override
            public void done(List<FunctionState> list, BmobException e) {
                if (list!=null){
                    FunctionStateUtil.BaiDuDocumentDownload = list.get(0).getOpen();

                }
            }
        });
    }


    public static void get_function_state_entry() {
        BmobQuery<FunctionState> query = new BmobQuery<>();
        query.addWhereEqualTo("name", "Entry");
        query.findObjects(new FindListener<FunctionState>() {
            @Override
            public void done(List<FunctionState> list, BmobException e) {
                //todo wenfujin
                if (list!=null)
                FunctionStateUtil.Entry = list.get(0).getOpen();
            }
        });
    }

    public static void get_function_state_phone_verify() {
        BmobQuery<FunctionState> query = new BmobQuery<>();
        query.addWhereEqualTo("name", "PhoneVerifyCode");
        query.findObjects(new FindListener<FunctionState>() {
            @Override
            public void done(List<FunctionState> list, BmobException e) {
                //todo wenfujin
                if (list!=null)
                FunctionStateUtil.PhoneVerifyCode = list.get(0).getOpen();
            }
        });
    }


}
