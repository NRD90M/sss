package com.wfj.bmobstudy.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.NewsReset;
import com.wfj.bmobstudy.Utils.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;


public class NewsInfoActivity extends AppBaseActivity {
    private LinearLayout ly_back;
    private WebView mWebView;
    private TextView tv_title;
    private TextView tv_date;
    private TextView tv_click_rate;
    private String url;
    private WebSettings mWebSettings;

    //新闻类news,直接选用content，不用做屏幕适配，但需要图片宽为100%，高为auto
    //通知类notify,直接选用content，需要做屏幕适配与文字增大


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
        return R.layout.activity_news_info;
    }

    private void initViews() {
        ly_back = (LinearLayout) findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mWebView = (WebView) findViewById(R.id.wv_news_info);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_click_rate = (TextView) findViewById(R.id.tv_click_rate);
        Intent i = getIntent();
        url = i.getStringExtra("url");
        //由于官网的设置，有些图片点击没有具体内容，在这里捕获异常
        try {
            initWebView();
        } catch (Exception e) {
            Toast.show_info(NewsInfoActivity.this, "当前图片不包含新闻！");
        }
    }

    private void initWebView() {

        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        //启动缓存
        mWebSettings.setAppCacheEnabled(true);
        //缓存模式，入如果本地有，就加载本地缓存
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);android4.4之后无效

        get_html();
        //mWebSettings.setUseWideViewPort(true);
        //mWebSettings.setLoadWithOverviewMode(true);
        //mWebSettings.setTextSize(WebSettings.TextSize.LARGEST);
        // if (!url.contains("notify")) {
        //mWebView.loadUrl(url);

        //  }

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

//                if (url.startsWith("http://news")) {
                    NewsReset.imgReset(mWebView);
                    Log.e("tag", "news");

//                } else {
//                    Log.e("tag", "notify");
//                }

            }
        });

    }


    private void get_html() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(url).timeout(6000).get();
//                    int length = document.select("div[class=main_content]").size();
//                    final String title = document.select("div[class=main_contit]").text();
//                    String un_date = "";
//                    if (length == 2) {
//                        un_date = document.select("div[class=main_content]").text().split("信")[0];
//                    } else {
//                        un_date = document.select("div[class=main_content]").select("p").text();
//                    }
//                    final String date = un_date.substring(0, 20);
//                    final String times = document.select("span[id=hits]").text();
                    final Elements elements = document.select("div[class=main_content]");
//                    if (url.contains("http://news.usts.edu.cn/")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mWebView.loadDataWithBaseURL("http://tsg.asc.jx.cn/info/", elements.toString(), "text/html", "utf-8", null);
                            }
                        });
//                    } else {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mWebSettings.setUseWideViewPort(true);
//                                mWebSettings.setLoadWithOverviewMode(true);
//                                // mWebSettings.setTextSize(WebSettings.TextSize.LARGEST);
//                                mWebSettings.setTextZoom(230);
//                                mWebView.loadDataWithBaseURL("http://notify.usts.edu.cn/", elements.toString(), "text/html", "utf-8", null);
//                            }
//                        });
//                    }


                    // final String title = document.select("div[class=title]").text();
                    // String un_date = document.select("div[class=author]").text().split("信")[0];
                    //final String date = un_date.substring(3, un_date.length() - 2);
                    // //String init_times = document.select("div[class=author]").eq(1).text();
                    // String un_times = init_times.split("编")[0];
                    // final String times = un_times.substring(0, un_times.length() - 1);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            tv_title.setText("" + title);
//                            tv_date.setText("" + date);
//                            tv_click_rate.setText("浏览次数 : " + times);
//                        }
//                    });
                } catch (Exception e) {
                    Toast.show_info(NewsInfoActivity.this, "当前图片不包含新闻！");
                }
            }
        }).start();

    }


    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            mWebView.stopLoading();
            //backHome();
            super.onBackPressed();
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }
    }

}

