package com.styudy.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.net.www.http.HttpClient;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName demo1
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/1/6 15:16
 **/
public class demo1 {
    public static void main(String[] args) throws IOException {
//        创建http请求
        CloseableHttpClient httpclient = HttpClients.createDefault();
//          输入网址，发起get请求
        HttpGet httpGet = new HttpGet("https://book.douban.com/");
//  发起响应
        CloseableHttpResponse execute = httpclient.execute(httpGet);
//        判断状态码
        System.out.println(execute.getStatusLine().getStatusCode());
        if(execute.getStatusLine().getStatusCode()==200){
            HttpEntity entity = execute.getEntity();
            String utf8 = EntityUtils.toString(entity, "utf8");
            System.out.println(utf8);
//            Document parse = Jsoup.parse(utf8);
//            Elements div = parse.getElementsByTag("div");
//            for (Element element : div) {
//                System.out.println(element);
//            }
        }
    }
}
