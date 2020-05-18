package com.wfj.bmobstudy.Fragment.SetMinFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.DocumentUtil;
import com.wfj.bmobstudy.Utils.ShowDialogUtil;
import com.wfj.bmobstudy.Utils.Toast;

/**
 * @description 百度文档下载界面
 * @date: 2020/4/26
 * @author: a */
public class DocumentDownloadFragment extends Fragment implements View.OnClickListener {
    private LinearLayout ly_back;
    private WebView webView;
    private EditText et_document;
    private Button btn_clear;
    private Button btn_download;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_document_download, null, false);
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
        et_document = (EditText) v.findViewById(R.id.et_document);
        btn_clear = (Button) v.findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        btn_download = (Button) v.findViewById(R.id.btn_download);
        btn_download.setOnClickListener(this);
        initWebView(v);

    }

    private void initWebView(View v) {
        webView = (WebView) v.findViewById(R.id.wv_document);
        WebSettings settings = webView.getSettings();
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setTextZoom(200);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http")) {
                    webView.loadUrl(url);
                    et_document.setText(url);
                    return false;
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return false;
                }

            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("https://wk.baidu.com/?pcf=2");
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                et_document.setText("");
                break;
            case R.id.btn_download:
                if (TextUtils.isEmpty(et_document.getText().toString())) {
                    Toast.show_info(getActivity(), "当前地址栏中无地址！");
                    return;
                }
                ShowDialogUtil.showProgressDialog(getActivity(), "正在下载");
                DocumentUtil.documentDownload(et_document.getText().toString().trim(), getActivity());
                break;
        }
    }


}
