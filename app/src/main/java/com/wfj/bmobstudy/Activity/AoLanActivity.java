package com.wfj.bmobstudy.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.wfj.bmobstudy.Constant.UstsValue;
import com.wfj.bmobstudy.R;

/**
 * @description aolan
 * @date: 2020/4/26
 * @author:
 */
public class AoLanActivity extends AppBaseActivity {
    private LinearLayout ly_back;
    private WebView mWebView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    /**
     * 返回 Activity Layout
     *
     * @return Layout ID
     */
    @Override
    public int setLayoutView() {
        return R.layout.activity_aolan;
    }

    private void initViews() {
        ly_back = (LinearLayout) findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mWebView = (WebView) findViewById(R.id.wv_aolan);
        //设置WebView支持JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置允许JS弹出Alert对话框
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.loadUrl(UstsValue.stu_aolan);


        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器

                //mWebView.loadUrl(url);
                //使用false使得那些已经重定向过的url不再进行重定向
                return false;
            }

        });
        //设置对话框
        mWebView.setWebChromeClient(new WebChromeClient() {

        });
    }


    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            startActivity(new Intent(AoLanActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            super.onBackPressed();
        }
    }
}
