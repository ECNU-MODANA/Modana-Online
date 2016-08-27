package com.smartcold.bgzigbee.manage.controller;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.smartcold.bgzigbee.manage.dao.ColdStorageDoorSetMapper;
import com.smartcold.bgzigbee.manage.dao.SpiderItemConfigMapper;
import com.smartcold.bgzigbee.manage.dto.ResultDto;
import com.smartcold.bgzigbee.manage.entity.ColdStorageDoorSetEntity;
import com.smartcold.bgzigbee.manage.enums.SpiderItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/coldStorageDoor")
public class ColdStorageDoorController {

	private Gson gson = new Gson();

	@Autowired
	private ColdStorageDoorSetMapper coldStorageDoorSetDao;

	@Autowired
	private SpiderItemConfigMapper spiderItemConfigDao;

	@RequestMapping(value = "/getcoldStorageDoorByStorageId")
	@ResponseBody
	public Object getcoldStorageDoorByStorageId(int coldStorageId) {
		return coldStorageDoorSetDao.findDoorByStorageId(coldStorageId);
	}

	@RequestMapping(value = "/updateMapping")
	@ResponseBody
	public Object updateMapping(int id, String mapping) {
		try {
			gson.fromJson(mapping, new TypeToken<Map<String, String>>() {
			}.getType());
		} catch (JsonParseException e) {
			return new ResultDto(-1, "参数不是合法的json map");
		}

		coldStorageDoorSetDao.updateMappingById(id, mapping);

		return new ResultDto(0, "修改成功");
	}

	@RequestMapping(value = "/findItem")
	@ResponseBody
	public Object findItem() {
		return spiderItemConfigDao.findItemByType(SpiderItemType.COLDSTORAGE.getType());
	}

	@RequestMapping(value = "/insertDoor", method = RequestMethod.POST)
	@ResponseBody
	public Object insertDoor(@RequestBody ColdStorageDoorSetEntity entity) {
		coldStorageDoorSetDao.insertDoor(entity);

		return new ResultDto(0, "添加成功");
	}

	@RequestMapping(value = "/deleteDoor", method = RequestMethod.DELETE)
	@ResponseBody
	public Object deleteDoor(int id) {
		coldStorageDoorSetDao.deleteDoor(id);

		return new ResultDto(0, "删除成功");
	}

	@RequestMapping(value = "/updateDoor", method = RequestMethod.POST)
	@ResponseBody
	public Object updateDoor(@RequestBody ColdStorageDoorSetEntity coldStorageDoorSetEntity){
		if (coldStorageDoorSetDao.updateDoor(coldStorageDoorSetEntity)) {
			return new ResultDto(0, "修改成功");
		}
		return new ResultDto(-1, "修改失败");
	}
}
