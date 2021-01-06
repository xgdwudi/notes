package com.styudy.WebService;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName newweb
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/1/6 10:53
 **/
public class newweb extends BreadthCrawler {
    /**
     * @param crawlPath crawlPath is the path of the directory which maintains
     *                  information of this crawler
     * @param autoParse if autoParse is true,BreadthCrawler will auto extract
     *                  links which match regex rules from pag
     */
    public newweb(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        /*start pages*/
        this.addSeed("https://blog.github.com/");
        for(int pageIndex = 2; pageIndex <= 5; pageIndex++) {
            String seedUrl = String.format("https://blog.github.com/page/%d/", pageIndex);
            this.addSeed(seedUrl);
        }

        /*fetch url like "https://blog.github.com/2018-07-13-graphql-for-octokit/" */
        this.addRegex("https://blog.github.com/[0-9]{4}-[0-9]{2}-[0-9]{2}-[^/]+/");
        /*do not fetch jpg|png|gif*/
        //this.addRegex("-.*\\.(jpg|png|gif).*");
        /*do not fetch url contains #*/
        //this.addRegex("-.*#.*");

        setThreads(50);
        getConf().setTopN(100);

        //enable resumable mode
        //setResumable(true);
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        String url = page.url();
        /*if page is news page*/
        if (page.matchUrl("https://blog.github.com/[0-9]{4}-[0-9]{2}-[0-9]{2}[^/]+/")) {

            /*extract title and content of news by css selector*/
            String title = page.select("h1[class=lh-condensed]").first().text();
            String content = page.selectText("div.content.markdown-body");

            System.out.println("URL:\n" + url);
            System.out.println("title:\n" + title);
            System.out.println("content:\n" + content);

            /*If you want to add urls to crawl,add them to nextLink*/
            /*WebCollector automatically filters links that have been fetched before*/
            /*If autoParse is true and the link you add to nextLinks does not match the
              regex rules,the link will also been filtered.*/
            //next.add("http://xxxxxx.com");
        }
    }

    public static void main(String[] args) throws Exception {
        newweb crawler = new newweb("crawl", true);
        /*start crawl with depth of 4*/
        crawler.start(4);
    }
}
