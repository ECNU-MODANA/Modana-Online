package com.wls.manage.service;

import java.util.List;

import com.wls.manage.dto.SingleInfoEntity;

public interface SingleInfoService {

	public SingleInfoEntity getAllInfoByKey(String key);
	
	public List<SingleInfoEntity> getBatchInfoByKey(String key);
	
}
