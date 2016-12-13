package com.wls.manage.crawler.tengxun;

import com.wls.manage.crawler.general.BasicCrawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.logging.Logger;

/**
 * Created by haolidong on 2016/11/12.
 */
public class ListCrawlJob {
    private static Logger logger = Logger.getLogger(TXListCrawler.class.getName());
    public void getUrlList(String linkUrl) {
        //爬取列表页面
        logger.info("Start crawl list page:" + linkUrl);
        String content = "";
        try {
            content=BasicCrawler.crawlPage(linkUrl,"gb2312");
            //content=BasicCrawler.crawlPage(linkUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
        //TODO  解析文章列表页面，获取需要爬取页面URL
        Document doc = Jsoup.parse(content);
        processListType(doc,linkUrl);
    }

    private void processListType(Document doc, String linkUrl){
        Elements eles=doc.select("div[class=mod newslist] li");
        for (Element ele:eles){
           Element href = ele.select("a[href]").first();
           /* if(href!=null && !ele.toString().contains("<h2 class")){
                String title = href.text();
                String url =href.attr("href");
                System.out.println(title);
                System.out.println(url);
            }*/
            String title =href.text();
            System.out.println(href.attr("href"));
            System.out.println(title);

        }

        //System.out.println(doc);
    }
}
