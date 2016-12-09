package com.wls.manage.dto;

import java.math.BigInteger;
import java.util.Date;

public class PublishDto {
    private BigInteger id;
	
	private BigInteger publisherid;//接收者id
	
	private String title;
	
	private Integer pubcategory;
	
    private Date pubtime;//发布时间
	
	private String content;
	
	private Integer schoolid;
	
	private Integer commentnum;
	
	private Integer praiseNum;
	
	private String publishername;
	
	private String publisheravatar;

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

	public Integer getPubcategory() {
		return pubcategory;
	}

	public void setPubcategory(Integer pubcategory) {
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

	public Integer getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(Integer schoolid) {
		this.schoolid = schoolid;
	}

	public Integer getCommentnum() {
		return commentnum;
	}

	public void setCommentnum(Integer commentnum) {
		this.commentnum = commentnum;
	}

	public Integer getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(Integer praiseNum) {
		this.praiseNum = praiseNum;
	}

	public String getPublishername() {
		return publishername;
	}

	public void setPublishername(String publishername) {
		this.publishername = publishername;
	}

	public String getPublisheravatar() {
		return publisheravatar;
	}

	public void setPublisheravatar(String publisheravatar) {
		this.publisheravatar = publisheravatar;
	}
	
}
