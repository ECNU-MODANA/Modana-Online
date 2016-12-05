package com.wls.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wls.manage.entity.SkillEntity;



/**
 * 
 *@author Kaiqiang Jiang
 *@date:2016-6-22 上午11:11:51
 *@Description: Skill Mapper
 */
public interface SkillMapper {

	SkillEntity findSkillByID(@Param("skillID") int skillID);
	
	List<SkillEntity> findSkillByUserId(@Param("userID") int userID);

	void insertSkill(SkillEntity skillEntity);
	
	void deleteSkill(@Param("skillID") int skillID);
	
	void updateSkill(SkillEntity skillEntity);
}
