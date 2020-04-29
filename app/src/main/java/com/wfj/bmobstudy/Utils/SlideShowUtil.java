package com.wfj.bmobstudy.Utils;

import android.util.Log;

import com.wfj.bmobstudy.Bean.SlideShow;
import com.wfj.bmobstudy.Constant.UstsValue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @date: 2020/4/26
 * @author: a */
public class SlideShowUtil {
    public static List<SlideShow> slideShowsList;
    private static final String TAG = "SlideShowUtil";
    public static void get_slideShow_info(final CallBackListener listener) {

        new Thread(new Runnable() {
            public void run() {
                try {
                    Document document = Jsoup.connect(UstsValue.official_website).timeout(5000).get();
                    Elements elements = document.getElementsByAttributeValue("class", "banner");
                    String big_url = "https://www.baidu.com/";
                    Log.d(TAG, "run:wenfujin "+elements);

//                    for (Element e : elements) {
                        Elements elements0 = elements.select("script[language]");
//                        for (Element elements2 : elements0) {
                            if (elements0.attr("language").equals("javascript1.1")) {
                                big_url = elements0.attr("src");
                            }
//                        }
//                    }
                    
                    Document document1 = Jsoup.connect(big_url).timeout(6000).get();
                    Elements elements1 = document1.getElementsByAttributeValue("class", "item");
                    Elements element = elements1.select("img[alt]");
                    slideShowsList = new ArrayList<SlideShow>();
                    for (Element element1 : element) {
                        String title = element1.attr("alt");
                        String img_url = element1.attr("src");
                        //Log.e("a", img_url);
                        SlideShow slideShow = new SlideShow();
                        slideShow.setTitle(title);
                        slideShow.setImg_url(img_url);
                        slideShowsList.add(slideShow);
                    }
                    slideShowsList = removeSame(slideShowsList);
                    Elements elements2 = elements1.select("a[title]");
                    for (Element element1 : elements2) {
                        for (int i = 0; i <= slideShowsList.size() - 1; i++) {
                            if (slideShowsList.get(i).getTitle().equals(element1.attr("title"))) {
                                slideShowsList.get(i).setDetail_url(element1.attr("href"));
                            }
                        }
                    }
                    listener.onSuccess(slideShowsList);

                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onFailure(e);
                }
            }
        }).start();
    }

    /**
     * 去除重复值
     *
     * @param list
     * @return
     */
    public static List<SlideShow> removeSame(List<SlideShow> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(i).getTitle().equals(list.get(j).getTitle())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

}
