package com.wls.manage.entity;

import java.math.BigInteger;
/**
 * 学生教育经历实体类
 * @author jiangkaiqiang
 * @version 创建时间：2016-10-21 下午2:22:21 
 *
 */
public class EducateEntity {
	private Integer id;
	
	private BigInteger userid;
	
	private String starttime;
	
	private String endtime;
	
	private String school;
	
	private String degree;
	
	private Integer fulltime;
	
	private String major;
	
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

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public Integer getFulltime() {
		return fulltime;
	}

	public void setFulltime(Integer fulltime) {
		this.fulltime = fulltime;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
}
