package com.wls.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wls.manage.entity.EducateEntity;


/**
 * 
 *@author Kaiqiang Jiang
 *@date:2016-6-22 上午11:11:51
 *@Description: Educate Mapper
 */
public interface EducateMapper {

	EducateEntity findEducateByID(@Param("educateID") int educateID);
	
	List<EducateEntity> findEducateByUserId(@Param("userID") int userID);

	void insertEducate(EducateEntity educateEntity);
	
	void deleteEducate(@Param("educateID") int educateID);
	
	void updateEducate(EducateEntity educateEntity);
}
