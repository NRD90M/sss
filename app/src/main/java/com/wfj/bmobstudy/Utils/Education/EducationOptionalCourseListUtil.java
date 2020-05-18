package com.wfj.bmobstudy.Utils.Education;


import com.wfj.bmobstudy.Bean.EducationOptionalCourse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @description 教务新闻、考试通告、信息公开的通知公告获取
 * @date: 2020/4/26
 * @author: a */
public class EducationOptionalCourseListUtil {
    public static OkHttpClient client = new OkHttpClient();
    public static String[] url = {
            "http://jwb.asc.jx.cn/xwzx/jwxw.htm",//教务新闻
            "http://jwb.asc.jx.cn/xwzx/kstg.htm",//考试通告
            "http://jwb.asc.jx.cn/xwzx/xxgk.htm"};//信息公开
//    private static int all = 0;

    //根据url获取对应的校区选修课简介
    public static void get_list(String url, final get_ListCall listCall) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listCall.fail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String html = new String(response.body().bytes(), "utf-8");
                Document document = Jsoup.parse(html);
                //获取总数
                Elements elements = document.select("ul[class=n_listxx1");
//                String number = elements.select("span[class=p_t]").text().split("共")[1].split("条")[0];
//                int all = Integer.parseInt(number);
                Elements elements1 = elements.select("li").select("h2");
                EducationOptionalCourse education = null;
                List<EducationOptionalCourse> list = new ArrayList<>();
                for (Element element : elements1) {
                    education = new EducationOptionalCourse();
                    education.setUrl("http://jwb.asc.jx.cn/" + element.select("a[href]").attr("href"));
                    education.setTitle("" + element.select("a[href]").text());
                    education.setTime("" + element.select("span").text());
                    list.add(education);

                }
//                all = list.size();
                listCall.success(list);
            }
        });

    }

    public interface get_ListCall {
        void success(List<EducationOptionalCourse> list);

        void fail();
    }
}
