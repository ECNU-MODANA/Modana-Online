package com.wls.manage.dao;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.wls.manage.entity.ResumeEntity;
/**
 * 
 *@author Kaiqiang Jiang
 *@date:2016-6-22 上午11:11:51
 *@Description: Resume Mapper
 */
public interface ResumeMapper {

	ResumeEntity findResumeByID(@Param("resumeID") int resumeID);
	
	Page<ResumeEntity> findAllResume(@Param("keyword")String keyword);
	
	Page<ResumeEntity> findResumeByUserId(@Param("userID") int userID);

	void insertResume(ResumeEntity resumeEntity);
	
	void deleteResume(@Param("resumeID") int resumeID);
	
	void updateResume(ResumeEntity resumeEntity);
}
