package com.wls.manage.entity;

import java.math.BigInteger;
import java.util.Date;
/**
 * 资讯实体类
 * @author jiangkaiqiang
 * @version 创建时间：2016-10-21 下午1:52:57 
 *
 */
public class InformationEntity {
	private BigInteger id;
	
	private String title;//标题
	
	private String infocategory;//分类，关联查询分类名称
	
	private String content;//内容
	
	private Date time;//发布时间
	
	private String imglist;//图片地址列表
	
	private String source;
	
	private String coverpiclist;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInfocategory() {
		return infocategory;
	}

	public void setInfocategory(String infocategory) {
		this.infocategory = infocategory;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getImglist() {
		return imglist;
	}

	public void setImglist(String imglist) {
		this.imglist = imglist;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCoverpiclist() {
		return coverpiclist;
	}

	public void setCoverpiclist(String coverpiclist) {
		this.coverpiclist = coverpiclist;
	}
	
}
