package com.wls.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wls.manage.entity.JobEntity;

/**
 * 
 *@author Kaiqiang Jiang
 *@date:2016-6-22 上午11:11:51
 *@Description: Job Mapper
 */
public interface JobMapper {

	JobEntity findJobByID(@Param("jobID") int jobID);
	
	List<JobEntity> findJobByUserId(@Param("userID") int userID);

	void insertJob(JobEntity jobEntity);
	
	void deleteJob(@Param("jobID") int jobID);
	
	void updateJob(JobEntity jobEntity);
}
