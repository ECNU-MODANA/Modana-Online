package com.wls.manage.entity;

import java.math.BigInteger;
/**
 * 学生工作经历实体类
 * @author jiangkaiqiang
 * @version 创建时间：2016-10-21 下午2:22:21 
 *
 */
public class JobEntity {
	private Integer id;
	
	private BigInteger userid;
	
	private String starttime;
	
	private String endtime;
	
	private String company;
	
	private String companysize;
	
	private String position;
	
	private String industry;
	
	private String companynature;
	
	private String describe;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigInteger getUserid() {
		return userid;
	}

	public void setUserid(BigInteger userid) {
		this.userid = userid;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanysize() {
		return companysize;
	}

	public void setCompanysize(String companysize) {
		this.companysize = companysize;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getCompanynature() {
		return companynature;
	}

	public void setCompanynature(String companynature) {
		this.companynature = companynature;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
}
