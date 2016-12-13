package com.wls.manage.crawler.tengxun;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by haolidong on 2016/11/14.
 */
public class PageCrawJob {
    private static Logger logger = Logger.getLogger(PageCrawJob.class.getName());
    public static void main(String[] args){
        try{
            Thread.sleep(1000);
            PageParseJob crawler =new PageParseJob();
            //crawler.parse("http://tech.qq.com/a/20161114/041263.htm");
            //crawler.parse("http://tech.qq.com/a/20161114/031873.htm");
            //crawler.parse("http://tech.qq.com/a/20161121/003033.htm");
            //crawler.parse("http://tech.qq.com/a/20161121/002314.htm");
            crawler.parse("http://tech.qq.com/a/20161111/027076.htm");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
