package com.styudy.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName demo1
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/1/6 11:07
 **/
public class Demo1 {
    public static void main(String[] args) {
        try
        {
            Document document = Jsoup.connect("https://image.baidu.com/search/index?tn=baiduimage&ps=1&ct=201326592&lm=-1&cl=2&nc=1&ie=utf-8&word=123").get();
            System.out.println(document);
//            Element feedlist_id = document.getElementById("feedlist_id");
//            Elements a = feedlist_id.getElementsByTag("a");
//            Elements links = document.select("img");
//            for (Element image : a)
//            {
//                System.out.println("src : " + image.attr("href"));
//                System.out.println("height : " + image.attr("height"));
//                System.out.println("width : " + image.attr("width"));
//                System.out.println("alt : " + image.attr("alt"));
//                System.out.println("link : " + link.attr("href"));
//                System.out.println("text : " + image.text());
//            }
//            System.out.println(document.title());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }
}
