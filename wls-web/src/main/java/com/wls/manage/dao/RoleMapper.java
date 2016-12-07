package com.wls.manage.dao;

import com.wls.manage.entity.RoleEntity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author jiangkaiqiang
 * @version 创建时间：2016-11-7 下午1:56:40 
 *
 */
public interface RoleMapper {
    List<RoleEntity> findRoles();

	RoleEntity findRoleById(@Param("roleID") int roleID);
}
