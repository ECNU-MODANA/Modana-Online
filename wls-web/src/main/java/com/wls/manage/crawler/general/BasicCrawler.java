package com.wls.manage.crawler.general;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by leyi on 14/12/7.
 */
public class BasicCrawler {

    private static Logger logger = Logger.getLogger(BasicCrawler.class.getName());

    private static BasicCrawler instance = null;

    private static Crawler crawler =null;

    private BasicCrawler() {
        crawler = new Crawler();
    }

    public static String crawlPage(String url) {
        if(instance==null) {
            instance = new BasicCrawler();
        }
        crawler.setUrlnoCheck(url.trim());

        String content = "";
        int trycount=6;
        try {
            content = crawler.getContent();
            while(content.length()<50 && trycount>0){
                sleep();
                trycount--;
                content = crawler.getContent();
            }
            sleep();
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Crawl the page failed!"+url);
        }

        return content;
    }

    public static String crawlPage(String url,String encoding) {
        if(instance==null) {
            instance = new BasicCrawler();
        }
        crawler.setUrlnoCheck(url.trim());

        String content = "";
        int trycount=5;
        try {
            content = crawler.getContent(encoding);
            while(content.length()<50 && trycount>0){
                sleep();
                trycount--;
                content = crawler.getContent();
            }
            sleep();
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Crawl the page failed!"+url);
        }

        return content;
    }

    public static void sleep() {
        int chance = new Random().nextInt(20);
        if(chance==0){
            long sleepTime = System.currentTimeMillis() % 3500 + 1000;
            System.out.println("Sleep " + sleepTime + "ms");
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                logger.info("Thread sleep interrupted!");
            }
        }
    }

    public static void main(String[] args) throws IOException{
        BasicCrawler crawler1 = new BasicCrawler();
        String content = crawlPage("http://baike.baidu.com/view/1.htm");
        System.out.println(content);

        System.out.println("=============================================================");

        Document doc = Jsoup.connect("http://baike.baidu.com/view/1.htm").get();
        System.out.println(doc);
    }
}
