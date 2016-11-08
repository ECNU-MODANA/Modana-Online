package com.wls.manage.entity;

import java.math.BigInteger;
import java.util.Date;
/**
 * 发帖实体类
 * @author jiangkaiqiang
 * @version 创建时间：2016-10-24 下午8:04:07 
 *
 */
public class PublishEntity {
	private BigInteger id;
	
	private BigInteger publisherid;//接收者id
	
	private String title;
	
	private String pubcategory;
	
    private Date pubtime;//发布时间
	
	private String content;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getPublisherid() {
		return publisherid;
	}

	public void setPublisherid(BigInteger publisherid) {
		this.publisherid = publisherid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPubcategory() {
		return pubcategory;
	}

	public void setPubcategory(String pubcategory) {
		this.pubcategory = pubcategory;
	}

	public Date getPubtime() {
		return pubtime;
	}

	public void setPubtime(Date pubtime) {
		this.pubtime = pubtime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
