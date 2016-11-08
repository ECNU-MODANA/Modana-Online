package com.wls.manage.entity;

import java.math.BigInteger;
/**
 * 学生简历可见实体类
 * @author jiangkaiqiang
 * @version 创建时间：2016-10-24 下午9:15:12 
 *
 */
public class ResumeVisEntity {
	private BigInteger id;
	
	private BigInteger ownerid;//简历所有者id
	
	private BigInteger visiblerid;//可见者id

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(BigInteger ownerid) {
		this.ownerid = ownerid;
	}

	public BigInteger getVisiblerid() {
		return visiblerid;
	}

	public void setVisiblerid(BigInteger visiblerid) {
		this.visiblerid = visiblerid;
	}
	
}
