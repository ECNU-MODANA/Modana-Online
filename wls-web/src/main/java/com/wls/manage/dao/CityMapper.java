package com.wls.manage.dao;

import com.wls.manage.entity.City_infoEntity;
import com.wls.manage.entity.Province_infoEntity;
import com.wls.manage.entity.School_infoEntity;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * @author jiangkaiqiang
 * @version 创建时间：2016-11-7 下午1:56:40 
 *
 */
public interface CityMapper {
    List<City_infoEntity> findCitysByProvinceId(@Param("provinceID") int provinceID);
    
    List<School_infoEntity> findSchoolsByCityId(@Param("cityID") int cityID);

    City_infoEntity findCityById(@Param("CityID") int CityID);

	Province_infoEntity findProvinceById(@Param("provinceID") int provinceID);

	School_infoEntity findSchoolById(@Param("schoolID") int schoolID);
}
