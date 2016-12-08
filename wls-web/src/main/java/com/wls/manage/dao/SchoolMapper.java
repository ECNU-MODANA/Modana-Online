package com.wls.manage.dao;

import com.wls.manage.entity.School_infoEntity;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * @author jiangkaiqiang
 * @version 创建时间：2016-11-7 下午2:17:18 
 *
 */
public interface SchoolMapper {
    List<School_infoEntity> findSchoolsByCityId(@Param("cityID") int cityID);

    School_infoEntity findSchoolById(@Param("schoolID") int schoolID);
    
    
    List<School_infoEntity> findSchoolList();

}
