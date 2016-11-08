package com.wls.manage.dao;

import com.wls.manage.entity.City_infoEntity;

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

    City_infoEntity findCityById(@Param("CityID") int CityID);
}
