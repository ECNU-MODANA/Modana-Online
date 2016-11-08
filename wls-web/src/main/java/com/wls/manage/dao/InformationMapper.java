package com.wls.manage.dao;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.wls.manage.entity.InformationEntity;
/**
 * 
 *@author Kaiqiang Jiang
 *@date:2016-6-22 上午11:11:51
 *@Description: Information Mapper
 */
public interface InformationMapper {

	InformationEntity findInformationByID(@Param("inforID") int inforID);
	
	Page<InformationEntity> findAllInformation(@Param("keyword")String keyword);
	
	Page<InformationEntity> findInformationByCategory(@Param("infocategory") String infocategory);

	void insertInformation(InformationEntity informationEntity);
	
	void deleteInformation(@Param("inforID") int inforID);
	
	void updateInformation(InformationEntity informationEntity);
}
