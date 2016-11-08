package com.wls.manage.entity;

import java.math.BigInteger;
import java.util.Date;
/**
 * 回复实体类
 * @author jiangkaiqiang
 * @version 创建时间：2016-10-24 下午8:37:53 
 *
 */
public class ResponseEntity {
	private BigInteger id;

	private BigInteger responserid;//回复者id
	
	private BigInteger responseid;//回复对象的id
	
	private Integer flag; //区分被回复的对象，0：response，1：comment
	
	private String content;
	
	private Date responsetime;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getResponserid() {
		return responserid;
	}

	public void setResponserid(BigInteger responserid) {
		this.responserid = responserid;
	}

	public BigInteger getResponseid() {
		return responseid;
	}

	public void setResponseid(BigInteger responseid) {
		this.responseid = responseid;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getResponsetime() {
		return responsetime;
	}

	public void setResponsetime(Date responsetime) {
		this.responsetime = responsetime;
	}
	
}
