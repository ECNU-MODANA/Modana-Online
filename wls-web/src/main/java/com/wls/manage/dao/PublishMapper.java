package com.wls.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.wls.manage.entity.PublishEntity;


/**
 * 
 *@author Kaiqiang Jiang
 *@date:2016-6-22 上午11:11:51
 *@Description: Publish Mapper
 */
public interface PublishMapper {

	PublishEntity findPulishByID(@Param("publishID") int publishID);
	
	List<PublishEntity> findPublishByUserId(@Param("userID") int userID);
	
	Page<PublishEntity> findPublishList(@Param("audit")Integer audit, @Param("keyword")String keyword, @Param("schoolid")Integer schoolid);

	void insertPublish(PublishEntity publishEntity);
	
	void deletePublish(@Param("publishID") int publishID);
	
	void updatePublish(PublishEntity publishEntity);
}
