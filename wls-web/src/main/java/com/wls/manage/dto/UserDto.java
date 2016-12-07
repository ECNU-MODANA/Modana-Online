package com.wls.manage.dto;

import java.math.BigInteger;
import java.util.List;

import com.wls.manage.entity.SkillEntity;

public class UserDto {
	private BigInteger id;
	
	private String nickname;
	
	private String signature;
	
	private List<SkillEntity> skillEntities;
	
	private Integer level;
	
	private String avatar;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public List<SkillEntity> getSkillEntities() {
		return skillEntities;
	}

	public void setSkillEntities(List<SkillEntity> skillEntities) {
		this.skillEntities = skillEntities;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
}
