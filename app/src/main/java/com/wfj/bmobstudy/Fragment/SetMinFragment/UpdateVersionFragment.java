package com.wfj.bmobstudy.Fragment.SetMinFragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.ShowOrHiddenUtil;

/**
 * @description 更新版本
 * @date: 2020/4/26
 * @author: a */
public class UpdateVersionFragment extends Fragment {
    private LinearLayout ly_back;
    private WebView webView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update_version, null, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        ly_back = (LinearLayout) v.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        ShowOrHiddenUtil.hidden_home_bottom(getActivity());
        initWebView(v);
    }


    private void initWebView(View v) {
        webView = (WebView) v.findViewById(R.id.wv_update_version);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //网页地址为http,图片地址为https,需要设置混合模式
        //settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        //settings.setBlockNetworkImage(false);
        webView.loadUrl("http://sukeda.bmob.site/");
    }
}
