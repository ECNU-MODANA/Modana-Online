package com.wls.manage.entity;

/**
 * 二级角色实体类
 * @author jiangkaiqiang
 * @version 创建时间：2016-10-24 下午9:20:16 
 *
 */
public class RoleEntity {
	private Integer id;
	
	private String name;//二级角色名
	
	private Integer suproleid;//所属一级角色id

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSuproleid() {
		return suproleid;
	}

	public void setSuproleid(Integer suproleid) {
		this.suproleid = suproleid;
	}
	
	
}
