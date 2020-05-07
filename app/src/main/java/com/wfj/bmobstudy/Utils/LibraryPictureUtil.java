package com.wfj.bmobstudy.Utils;

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
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author: a */
public class LibraryPictureUtil {
    public static int choose = 1;
    public static List<SlideShow> libraryShowsList;

    public static List<String> title ;
    public static List<String> url;

    public static void get_library_pic_info(final CallBackListener listener){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(UstsValue.library_website).timeout(5000).get();
                    Elements elements = document.getElementsByAttributeValue("class", "bd");
                    Elements elements0 = elements.get(0).select("ul").select("li").select("a");
                    libraryShowsList = new ArrayList<SlideShow>();

//                    title = new ArrayList<>();
                    for (Element element:elements0){
                        String title = element.attr("title");
                        String img_url = element.select("img").attr("src");
                        String detail_url = element.attr("href");

                        SlideShow slideShowLibrary = new SlideShow();
                        slideShowLibrary.setTitle(title);
                        slideShowLibrary.setDetail_url(UstsValue.library_website+detail_url);
                        slideShowLibrary.setImg_url(UstsValue.library_website+img_url);//拼接url

                        libraryShowsList.add(slideShowLibrary);
                    }

                    listener.onSuccess(libraryShowsList);

                }catch (IOException e) {
                    e.printStackTrace();
                    listener.onFailure(e);
                }
            }
        }).start();

    }

    public static List<String> get_library_pic_title() {
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(UstsValue.official_jl).timeout(5000).get();
                    Elements elements = document.getElementsByAttributeValue("class", "bd");
                    Elements elements0 = elements.get(0).select("ul").select("li").select("a");

                    for (Element element:elements0){
                        title.add(element.attr("href"));

                        url.add(element.select("img").attr("src"));
                    }

                }catch (Exception e){}
            }
        });


        return title;
    }

    public static List<String> get_library_picture_url() {
        if (choose == 0) {
            return get_library_pic_url_dim();
        } else {
            return get_library_pic_url_clear();
        }

    }
    public static List<String> get_library_pic_url_dim() {
        List<String> url = new ArrayList<>();
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/088.jpg");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/IMG_6386.jpg");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC_0084.jpg");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01160.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01165.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01171.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01170.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01175.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01183.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01184.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01194.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01197.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01199.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01138.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01144.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01147.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01148.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01145_0.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01152.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01153.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01159.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01158.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01154.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01136.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01137.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01134.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01122.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01125.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01126.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01127.JPG");

        return url;
    }

    public static List<String> get_library_pic_url_clear() {
        List<String> url = new ArrayList<>();
        url.add("http://library.usts.edu.cn/sites/default/files/slideshow/088.jpg");
        url.add("http://library.usts.edu.cn/sites/default/files/slideshow/img_6386.jpg");
        url.add("http://library.usts.edu.cn/sites/default/files/slideshow/DSC_0084.jpg");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01160.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01165.JPG");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01171.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01170.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01175.JPG");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01183.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01184.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01194.JPG");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01197.JPG");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01199.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01138.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01144.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01147.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01148.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/slideshow/DSC01145.jpg");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01152.JPG");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01153.JPG");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01159.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01158.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01154.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01136.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01137.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01134.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01122.JPG");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01125.JPG");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01126.JPG");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01127.JPG");

        return url;
    }

}
