package com.wls.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wls.manage.dao.CityMapper;
import com.wls.manage.dao.ProvinceMapper;
import com.wls.manage.util.APP;

@Controller
@RequestMapping(value = "/city")
public class CityController {

    @Autowired
    private ProvinceMapper provinceListMapper;

    @Autowired
    private CityMapper cityListDao;

    
    @APP
    @RequestMapping(value = "/findProvinceList", method = RequestMethod.GET)
    @ResponseBody
    public Object findProvinceList() {
        return provinceListMapper.findProvinceList();
    }
    
    @APP
    @RequestMapping(value = "/findCitysByProvinceId", method = RequestMethod.GET)
    @ResponseBody
    public Object findCitysByProvinceId(@RequestParam int provinceID) {
        return cityListDao.findCitysByProvinceId(provinceID);
    }

    @RequestMapping(value = "/findCityById", method = RequestMethod.GET)
    @ResponseBody
    public Object findCityById(@RequestParam int CityID) {
        return cityListDao.findCityById(CityID);
    }
}
