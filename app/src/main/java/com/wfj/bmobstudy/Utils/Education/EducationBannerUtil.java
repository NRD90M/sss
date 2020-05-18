package com.wfj.bmobstudy.Utils.Education;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.*;
import okhttp3.Call;

/**
 * @description 教务信息的轮播图
 * @date: 2020/4/26
 * @author: a */
public class EducationBannerUtil {
    private static OkHttpClient client = new OkHttpClient();
    //教务系统基地址
    public static String url = "http://jwb.asc.jx.cn/";

    //获取轮播图地址
    public static void get_banner_list(final get_bannerCall bannerCall) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<String> list = new ArrayList<String>();
                Document document = Jsoup.parse(response.body().string());
                Elements elements = document.select("div[class=bd]").select("ul").select("li").select("a").select("img");
                for (Element element : elements) {
                    list.add(url + element.attr("src"));
                }
                bannerCall.success(list);
            }
        });
    }

    public interface get_bannerCall {
        void success(List<String> list);
    }
}
