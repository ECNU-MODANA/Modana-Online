package com.wls.manage.entity;

import java.math.BigInteger;
/**
 * 点赞实体类
 * @author jiangkaiqiang
 * @version 创建时间：2016-10-24 下午9:05:22 
 *
 */
public class PraiseEntity {
	private BigInteger id;
	
	private BigInteger praiseid;//被点赞的对象的id
	
	private BigInteger praiserid;//点赞者id
	
    private Integer flag;   //区分被点赞的对象，0：information，1：publish，2：comment

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getPraiseid() {
		return praiseid;
	}

	public void setPraiseid(BigInteger praiseid) {
		this.praiseid = praiseid;
	}

	public BigInteger getPraiserid() {
		return praiserid;
	}

	public void setPraiserid(BigInteger praiserid) {
		this.praiserid = praiserid;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
    
}
