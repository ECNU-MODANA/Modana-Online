package com.wls.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wls.manage.entity.FollowEntity;




/**
 * 
 *@author Kaiqiang Jiang
 *@date:2016-6-22 上午11:11:51
 *@Description: Follow Mapper
 */
public interface FollowMapper {

	FollowEntity findFollowByID(@Param("followID") int followID);
	
	List<FollowEntity> findFollowByUserId(@Param("userID") int userID);

	void insertFollow(FollowEntity followEntity);
	
	void deleteFollow(@Param("followID") int followID);
}
