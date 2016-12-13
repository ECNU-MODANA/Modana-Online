package com.wls.manage.crawler.general;
public class Page {
    private int page_Id;
    private int sourceId;
    private String url;
    private String title;
    private String pubTime;
    private String pubSource;
    private String content;
    public Page() {
    }
    public Page(int page_Id,int sourceId,String url,String title, String pubTime, String pubSource, String content) {
        this.page_Id = page_Id;
        this.sourceId = sourceId;
        this.url = url;
        this.title = title;
        this.pubTime = pubTime;
        this.pubSource = pubSource;
        this.content = content;
    }
    public String toString() {
        return "Page{" +
                "pageId=" + page_Id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", pubDate='" + pubTime + '\'' +
                ", pubSource='" + pubSource + '\'' +
                ", content='" + content + '\'' +
                ", sourceId=" + sourceId +
                '}';
    }

    public int getPageId() {
        return page_Id;
    }

    public void setPageId(int pageId) {
        this.page_Id = pageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getPubSource() {
        return pubSource;
    }

    public void setPubSource(String pubSource) {
        this.pubSource = pubSource;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public int getSourceId() {
        return sourceId;
    }
    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
}