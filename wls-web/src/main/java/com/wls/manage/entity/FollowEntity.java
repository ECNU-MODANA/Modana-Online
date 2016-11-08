package com.wls.manage.entity;

import java.math.BigInteger;
/**
 * 关注实体类
 * @author jiangkaiqiang
 * @version 创建时间：2016-10-24 下午9:04:17 
 *
 */
public class FollowEntity {
	private BigInteger id;
	
	private BigInteger followid;//关注者id
	
	private BigInteger followedid;//被关注者id

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getFollowid() {
		return followid;
	}

	public void setFollowid(BigInteger followid) {
		this.followid = followid;
	}

	public BigInteger getFollowedid() {
		return followedid;
	}

	public void setFollowedid(BigInteger followedid) {
		this.followedid = followedid;
	}
	
}
