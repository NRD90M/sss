package com.wfj.bmobstudy.Utils;

import android.util.Log;

import com.wfj.bmobstudy.Bean.NewsInform;
import com.wfj.bmobstudy.Constant.UstsValue;

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
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author:
 */
public class NewsInformUtil {
    private static OkHttpClient client = new OkHttpClient();
    public static List<NewsInform> list;
    public static List<String> pic_list;

    //获取新闻列表
    public static void get_list(final String url, final NewsinformCall newsinformCall) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("test", "onFailure: 失败");
            }

            public void onResponse(Call call, Response response) throws IOException {
                Document document = Jsoup.parse(response.body().string());
                Elements elements = document.select("ul[class=list]").select("li");
                list = new ArrayList<NewsInform>();
                NewsInform newsInform;
                for (Element element : elements) {
                    newsInform = new NewsInform();
                    String str = element.select("a").text();
                    String title=str.substring(10, str.length());//标题
                    String time = str.substring(0, 10);//日期
//                    String read = element.select("font").text();
                    String true_url = "";
                    true_url = element.select("a[href]").attr("href");

                    newsInform.setTitle(title);
                    newsInform.setTime(time);
//                    newsInform.setRead(read);
                    newsInform.setUrl(true_url);
                    newsInform.setFirst_pic_url("");
                    list.add(newsInform);

                }
                newsinformCall.success(list);

            }
        });

    }

    /**
     * 获取第一张图片的url
     */
    public static void get_first_pic_url(final PicUrlCall picUrlCall) {
        pic_list = new ArrayList<>();
        for (int a = 0; a < 4; a++) {
            Request request = new Request.Builder()
                    .url(list.get(a).getUrl())
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Document document = Jsoup.parse(response.body().string());
                    String pic_url = document.select("div[class=content]").select("a[href]").first().attr("href");
                    String url = "";

                    if (pic_url == null) {
                        pic_list.add("");
                        url = "nullnullnull";
                    } else if (pic_url.endsWith("jpg")) {
                        if (pic_url.contains("http")) {
                            pic_list.add(pic_url);
                            url = pic_url;
                        } else {
                            pic_list.add("http://news.usts.edu.cn/" + pic_url);
                            url = "http://news.usts.edu.cn/" + pic_url;
                        }
                    }
                    Log.e("tag", "---" + url + "---");
                    if (pic_list.size() == 4) {
                        picUrlCall.success(pic_list);
                    }
                }
            });
        }
    }


    public interface NewsinformCall {
        void success(List<NewsInform> list);
    }

    public interface PicUrlCall {
        void success(List<String> list);
    }

    public static String get_pic_url(String url) {
        String pic_url = null;
        try {

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            Document document = Jsoup.parse(response.body().string());
            String pic_url1 = null;
            try {
                pic_url1 = document.select("div[class=content]").select("a[href]").first().attr("href");
            } catch (NullPointerException e) {
                pic_url = "";
            }
            if (pic_url1 == null) {
                pic_url = "";
            } else if (pic_url1.endsWith("jpg")) {
                if (pic_url1.contains("http")) {
                    pic_url = pic_url1;
                } else {
                    pic_url = "http://news.usts.edu.cn/" + pic_url1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pic_url;

    }
}
