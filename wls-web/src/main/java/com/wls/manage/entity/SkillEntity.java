package com.wls.manage.entity;

import java.math.BigInteger;
/**
 * 学生技实体类
 * @author jiangkaiqiang
 * @version 创建时间：2016-10-21 下午2:22:21 
 *
 */
public class SkillEntity {
	private Integer id;
	
	private BigInteger userid;
	
	private String skill;
	
	private Integer degree;

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

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}
	
}
