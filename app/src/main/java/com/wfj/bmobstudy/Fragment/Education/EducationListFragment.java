package com.wfj.bmobstudy.Fragment.Education;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wfj.bmobstudy.Adapter.EducationAdapter;
import com.wfj.bmobstudy.Bean.Education;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.Education.EducationListUtil;
import com.wfj.bmobstudy.Utils.ShowOrHiddenUtil;
import com.vondear.rxtools.view.RxToast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

/**
 * @description 教务信息--》4个管理规定+4个图标（）
 * @date: 2020/4/26
 * @author: a */
public class EducationListFragment extends Fragment {
    private TextView tv_info;
    private LinearLayout ly_back;
    private RecyclerView rcv_education_list;
    private int current_page = 1;
    private int shown = 0;
    private int all_number = 0;
    private String current_url;
    private String category;
    private View rootView;
    private WebView wv_education;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            View v = inflater.inflate(R.layout.fragment_education_web, null, false);
            current_url = getArguments().getString("url");
            category = getArguments().getString("category");
            rootView = v;
            initWebView(v);

        }
        return rootView;
    }

    private void initWebView(View v) {
        ly_back = (LinearLayout) v.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        tv_info = (TextView) v.findViewById(R.id.tv_info);
        tv_info.setText("" + category);

        wv_education = (WebView) v.findViewById(R.id.wv_education);
        WebSettings settings = wv_education.getSettings();
        settings.setJavaScriptEnabled(true);//设置WebView是否允许执行JavaScript脚本，默认false
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//重写使用缓存的方式，默认值LOAD_DEFAULT
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//让JavaScript自动打开窗口，默认false
        settings.setUseWideViewPort(true);//WebView是否支持HTML的“viewport”标签或者使用wide viewport
        settings.setLoadWithOverviewMode(true);//设置WebView是否在概览模式下加载页面
        settings.setTextZoom(200);
        get_html();
    }
    private void get_html(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(current_url)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String html = new String(response.body().bytes(), "utf-8");
                Document document = Jsoup.parse(html);
//                Elements elements = document.select("div[class=contain");
                //删除下一条
//                elements.select("div[class=content-sxt fl]").remove();
                final String content = document.toString();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wv_education.loadDataWithBaseURL("http://jw.asc.jx.cn/", content, "text/html", "utf-8", null);
                    }
                });
            }
        });
    }
}
