package com.smartcold.manage.cold.dao.olddb;

import com.smartcold.manage.cold.entity.olddb.RoleUser;

public interface RoleUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleUser record);

    int insertSelective(RoleUser record);

    RoleUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleUser record);

    int updateByPrimaryKey(RoleUser record);
    
    RoleUser getRoleUserByUserId(Integer userId);
}