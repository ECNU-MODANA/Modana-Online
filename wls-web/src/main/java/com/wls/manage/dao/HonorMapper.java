package com.wls.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wls.manage.entity.HonorEntity;



/**
 * 
 *@author Kaiqiang Jiang
 *@date:2016-6-22 上午11:11:51
 *@Description: Honor Mapper
 */
public interface HonorMapper {

	HonorEntity findHonorByID(@Param("honorID") int honorID);
	
	List<HonorEntity> findHonorByUserId(@Param("userID") int userID);

	void insertHonor(HonorEntity honorEntity);
	
	void deleteHonor(@Param("honorID") int honorID);
	
	void updateHonor(HonorEntity honorEntity);
}
