package com.wfj.bmobstudy.Fragment.SetMinFragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.NewsReset;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class BrInUsItemFragment extends Fragment {
    private WebView webView;
    private String url;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_brinus, null, false);
        Bundle bundle = getArguments();
        this.url = bundle.getString("url");
        Log.e("tag56", "url" + url);
        initViews(v);
        return v;
    }


    public static BrInUsItemFragment newInstance(String url) {
        BrInUsItemFragment fragment = new BrInUsItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initViews(final View v) {

        webView = (WebView) v.findViewById(R.id.wv_brinus);
        webView.loadUrl(url);
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        //启动缓存
        mWebSettings.setAppCacheEnabled(true);
        //缓存模式，入如果本地有，就加载本地缓存
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);android4.4之后无效
        //mWebSettings.setUseWideViewPort(true);
        //mWebSettings.setLoadWithOverviewMode(true);
        //mWebSettings.setTextSize(WebSettings.TextSize.SMALLER);
        //设置垂直滚动条不显示
        webView.setVerticalScrollBarEnabled(false);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                NewsReset.imgReset(webView);

            }
        });
    }
}
