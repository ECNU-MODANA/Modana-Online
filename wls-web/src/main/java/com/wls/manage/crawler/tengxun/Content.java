package com.wls.manage.crawler.tengxun;

/**
 * Created by haolidong on 2016/11/21.
 */
public class Content {
    boolean isImg;
    String con;

    public boolean isImg() {
        return isImg;
    }

    public void setImg(boolean img) {
        isImg = img;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getCon() {
        return con;
    }

    public Content() {
    }

    public Content(boolean isImg, String con) {
        this.isImg = isImg;
        this.con = con;
    }
}
