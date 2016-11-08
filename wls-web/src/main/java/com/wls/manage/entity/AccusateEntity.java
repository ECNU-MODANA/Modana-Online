package com.wls.manage.entity;

import java.math.BigInteger;
/**
 * 举报实体类
 * @author jiangkaiqiang
 * @version 创建时间：2016-10-24 下午9:03:54 
 *
 */
public class AccusateEntity {
	private BigInteger id;
	
	private BigInteger accusateid;
	
	private BigInteger accusaterid;
	
	private Integer flag; //区分被举报的对象，0：information，1：publish，2：comment

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getAccusateid() {
		return accusateid;
	}

	public void setAccusateid(BigInteger accusateid) {
		this.accusateid = accusateid;
	}

	public BigInteger getAccusaterid() {
		return accusaterid;
	}

	public void setAccusaterid(BigInteger accusaterid) {
		this.accusaterid = accusaterid;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
