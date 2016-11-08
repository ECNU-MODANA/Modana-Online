package com.wls.manage.entity;

import java.math.BigInteger;
import java.util.Date;
/**
 * 学生简历实体类
 * @author jiangkaiqiang
 * @version 创建时间：2016-10-21 下午2:22:21 
 *
 */
public class ResumeEntity {
	private BigInteger id;
	
	private BigInteger userid;
	
	private String educate;//教育经历
	
	private String practice;//实习经历
	
	private String skill;//专长
	
	private String certificate;//证书
	
	private String duty;//校内职务
	
	private String reward;//所受奖励
	
	private String introduction;//个人简介
	
	private Date addtime;//添加时间
	
	private Date updatetime;//更新时间
	
	private String photo;//个人简介

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getEducate() {
		return educate;
	}
	
	public BigInteger getUserid() {
		return userid;
	}

	public void setUserid(BigInteger userid) {
		this.userid = userid;
	}

	public void setEducate(String educate) {
		this.educate = educate;
	}

	public String getPractice() {
		return practice;
	}

	public void setPractice(String practice) {
		this.practice = practice;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	
}
