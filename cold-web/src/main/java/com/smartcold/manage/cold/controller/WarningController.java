package com.smartcold.manage.cold.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartcold.manage.cold.dao.newdb.WarningsInfoMapper;
import com.smartcold.manage.cold.dao.newdb.WarningsSetMapper;
import com.smartcold.manage.cold.entity.newdb.WarningsInfo;

@Controller
@RequestMapping(value = "/warn")
public class WarningController extends BaseController {

	@Autowired
	private WarningsInfoMapper warningsInfoDao;
	
	@Autowired
	private WarningsSetMapper warningsSetDao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/findAllWarningsInfoByRdcId", method = RequestMethod.GET)
	@ResponseBody
	public Object findAllWarningsInfo(int rdcId) {
		
		List allInfoList = new ArrayList();
		List<WarningsInfo> warningsInfoList = warningsInfoDao.findAllWarningInfo(rdcId);
		for(WarningsInfo warningInfo : warningsInfoList){
			String warningName = warningsSetDao.findWarningSetById(warningInfo.getObjId()).getName();
			Map map = new HashMap();
			map.put("addtime", warningInfo.getAddtime());
			map.put("warningName", warningName);
			map.put("id", warningInfo.getId());
			allInfoList.add(map);
		}
		return allInfoList;
	}
}
