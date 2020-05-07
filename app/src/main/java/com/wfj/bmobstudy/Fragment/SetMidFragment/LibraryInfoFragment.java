package com.wfj.bmobstudy.Fragment.SetMidFragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.wfj.bmobstudy.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class LibraryInfoFragment extends Fragment {
    private WebView webView;
    private String url;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_library_info, null, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        webView = (WebView) v.findViewById(R.id.wv_library_info);
        url = getArguments().getString("url");
        initWebView(url);
    }

    private void initWebView(String url) {

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setDefaultTextEncodingName("uft-8");
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                tableReset();
            }
        });
        get_html();
    }

    private void get_html() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(url).timeout(6000).get();
                    Elements elements = document.select("div[class=main_conR]");
//                    Elements elements1 = document.select("div[class=links]");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadDataWithBaseURL(null, elements.toString(), "text/html", "utf-8", null);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    public void tableReset() {
        webView.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName('table'); "
                + "for(var i=0;i<objs.length;i++)  " + "{"
                + "var img = objs[i];   "
                + "    img.style.width = '100%';   "
                + "    img.style.height = 'auto';   "
                + "}" + "})();");
    }

}
