package com.wls.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wls.manage.entity.ResumeVisEntity;


/**
 * 
 *@author Kaiqiang Jiang
 *@date:2016-6-22 上午11:11:51
 *@Description: ResumeVis  Mapper
 */
public interface ResumeVisMapper {

	List<ResumeVisEntity> findvisibleridByOwnerId(@Param("userID") int userID);

	void insertResumeVis(ResumeVisEntity resumeVisEntity);
	
}
