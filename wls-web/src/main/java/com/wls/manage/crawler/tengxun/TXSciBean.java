package com.wls.manage.crawler.tengxun;

import java.util.ArrayList;

/**
 * Created by haolidong on 2016/11/21.
 */
public class TXSciBean {
    String title;
    String data;
    public ArrayList<Content> ac;

    public TXSciBean() {
        this.title="IMAX投5000万美元建立VR投资基金 欲助推VR内容创作";
        this.data="11月11日&#160;09:08";
        ac = new ArrayList<Content>();
    }

    public TXSciBean(String title, String data) {

        this.title = title;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
