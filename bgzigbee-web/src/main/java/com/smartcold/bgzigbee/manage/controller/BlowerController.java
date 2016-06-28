package com.smartcold.bgzigbee.manage.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.smartcold.bgzigbee.manage.dao.BlowerSetMapper;
import com.smartcold.bgzigbee.manage.dto.ResultDto;

@Controller
@RequestMapping(value = "/blower")
public class BlowerController {

	private Gson gson = new Gson();

	@Autowired
	private BlowerSetMapper blowerDao;

	@RequestMapping(value = "/getBlowerByColdStorageId")
	@ResponseBody
	public Object getBlowerByColdStorageId(int coldStorageId) {
		return blowerDao.findByStorageId(coldStorageId);
	}

	@RequestMapping(value = "/updateMapping")
	@ResponseBody
	public Object updateMapping(int blowerId, String mapping) {
		try {
			gson.fromJson(mapping, new TypeToken<Map<String, String>>() {
			}.getType());
		} catch (JsonParseException e) {
			return new ResultDto(-1, "参数不是合法的json map");
		}

		blowerDao.updateMappingById(blowerId, mapping);

		return new ResultDto(0, "修改成功");
	}
}
