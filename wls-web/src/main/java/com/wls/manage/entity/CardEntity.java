package com.wls.manage.entity;

import java.math.BigInteger;
import java.util.Date;
/**
 * 其他人员名片实体类
 * @author jiangkaiqiang
 * @version 创建时间：2016-10-24 下午6:46:29 
 *
 */
public class CardEntity {
	private BigInteger id;
	
	private BigInteger userid;
	
	private String educate;//教育经历
	
	private String job;//工作经历
	
	private String position;//职位
	
	private String introduction;//个人描述
	
	private Date addtime;//添加时间
	
	private Date updatetime;//更新时间
	
	private String company;//公司

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getUserid() {
		return userid;
	}

	public void setUserid(BigInteger userid) {
		this.userid = userid;
	}

	public String getEducate() {
		return educate;
	}

	public void setEducate(String educate) {
		this.educate = educate;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	
}
