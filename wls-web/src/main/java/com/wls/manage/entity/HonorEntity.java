package com.wls.manage.entity;

import java.math.BigInteger;
/**
 * 学生所获荣誉实体类
 * @author jiangkaiqiang
 * @version 创建时间：2016-10-21 下午2:22:21 
 *
 */
public class HonorEntity {
	private Integer id;
	
	private BigInteger userid;
	
	private String starttime;
	
	private String endtime;
	
	private String honor;

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

	public String getHonor() {
		return honor;
	}

	public void setHonor(String honor) {
		this.honor = honor;
	}
	
	
}
