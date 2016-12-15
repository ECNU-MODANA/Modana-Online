package com.modana.service;

import com.modana.manage.service.base.HttpService;
import com.modana.manage.service.base.impl.HttpServiceImpl;

/**
 * 
 *@author Kaiqiang Jiang
 *@date:2016-6-4 下午3:45:34
 *@Description: (1)Test Git Update
 */
public class CommonTest {
	public static void main(String[] args) {
		 HttpService httpService = new HttpServiceImpl();
	     String msg="content="+"1"+"&infocategory=1&source=腾讯新闻&coverpiclist="+"2"+"&title="+"3"+"&time="+"2016-11-23 12:10:12";
	     httpService.sendPost("http://localhost:8989/i/information/addInformation", msg,10);
	}
}
