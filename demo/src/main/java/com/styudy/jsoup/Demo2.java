package com.styudy.jsoup;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.logging.Level;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName Demo2
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/1/6 14:11
 **/
public class Demo2 {
    public static void main(String[] args) throws IOException {
        String url ="https://book.douban.com/";
        //构造一个webClient 模拟Chrome 浏览器
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
//屏蔽日志信息
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
//支持JavaScript
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setTimeout(5000);
        HtmlPage rootPage = webClient.getPage(url);
//设置一个运行JavaScript的时间
        webClient.waitForBackgroundJavaScript(5000);
        String html = rootPage.asXml();
        Document document = Jsoup.parse(html);
        Elements select = document.select("div.book-info a.name");
        for (Element imgBox : select) {
            String href = imgBox.attr("href");
            String hrefs = imgBox.text();
            System.out.println(hrefs+"连接："+href);
        }
    }
}
