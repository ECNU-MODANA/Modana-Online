package com.smartcold.bgzigbee.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartcold.bgzigbee.manage.entity.CompressorGroupSetEntity;

/**
 * Author: qiunian.sun Date: qiunian.sun(2016-05-02 14:24)
 */
public interface CompressorGroupSetMapper {

	List<CompressorGroupSetEntity> findByRdcId(@Param("rdcId") int rdcId);

	void updateMappingById(@Param("compressorGroupId") int compressorGroupId, @Param("mapping") String mapping);
}