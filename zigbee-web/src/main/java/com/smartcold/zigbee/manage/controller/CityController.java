package com.smartcold.zigbee.manage.controller;

import com.smartcold.zigbee.manage.dao.CityListMapper;
import com.smartcold.zigbee.manage.dao.ProvinceListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: qiunian.sun
 * Date: qiunian.sun(2016-05-21 16:41)
 */
@Controller
@RequestMapping(value = "/city")
public class CityController {

    @Autowired
    private ProvinceListMapper provinceListMapper;

    @Autowired
    private CityListMapper cityListDao;

    @RequestMapping(value = "/findProvinceList", method = RequestMethod.GET)
    @ResponseBody
    public Object findProvinceList() {
        return provinceListMapper.findProvinceList();
    }

    @RequestMapping(value = "/findCitysByProvinceId", method = RequestMethod.GET)
    @ResponseBody
    public Object findCitysByProvinceId(@RequestParam int provinceID) {
        return cityListDao.findCitysByProvinceId(provinceID);
    }
}
