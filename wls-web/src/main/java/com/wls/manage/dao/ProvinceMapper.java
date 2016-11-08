package com.wls.manage.dao;

import com.wls.manage.entity.Province_infoEntity;

import java.util.List;

/**
 * province list
 * @author jiangkaiqiang
 * @version 创建时间：2016-11-7 下午2:04:16 
 *
 */
public interface ProvinceMapper {

    List<Province_infoEntity> findProvinceList();

}
