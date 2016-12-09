package com.wls.manage.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wls.manage.dao.PublishMapper;
import com.wls.manage.dao.UserMapper;
import com.wls.manage.dto.BaseDto;
import com.wls.manage.dto.PublishDto;
import com.wls.manage.entity.PublishEntity;
import com.wls.manage.entity.UserEntity;
/**
 * 
 * @author kaiqiang jiang
 * @version 创建时间：2016-9-13 下午2:24:54
 * 消息通知controller
 */
@Controller
@RequestMapping(value = "/publish")
public class PublishController extends BaseController {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PublishMapper publishMapper;
	
	/**
	 * 为前台user查询通知提供服务
	 * @param pageNum
	 * @param pageSize
	 * @param userID
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/findPublishByUserId")
	@ResponseBody
	public Object findPublishByUserId(
			@RequestParam(value="userID", required=false) Integer userID) throws UnsupportedEncodingException {
		List<PublishEntity> publishEntities = publishMapper.findPublishByUserId(userID);
		List<PublishDto> pDtos = getPublishDtos(publishEntities);
		return pDtos;
	}
	
	
	private List<PublishDto> getPublishDtos(List<PublishEntity> publishEntities) {
		List<PublishDto> pDtos = new ArrayList<PublishDto>();
		for (PublishEntity publishEntity : publishEntities) {
			PublishDto publishDto = new PublishDto();
			publishDto.setId(publishEntity.getId());
			publishDto.setContent(publishEntity.getContent());
			publishDto.setPubcategory(publishEntity.getPubcategory());
			publishDto.setPublisherid(publishEntity.getPublisherid());
			publishDto.setPubtime(publishEntity.getPubtime());
			publishDto.setSchoolid(publishEntity.getSchoolid());
			publishDto.setTitle(publishEntity.getTitle());
			UserEntity userEntity = userMapper.findUserById(publishEntity.getPublisherid().intValue());
			publishDto.setPublishername(userEntity.getNickname());
			publishDto.setPublisheravatar(userEntity.getAvatar());
			pDtos.add(publishDto);
		}
		return pDtos;
	}


	/**
	 * 根据分类查询发布
	 * @param pageNum
	 * @param pageSize
	 * @param categoryID
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/findPublishList")
	@ResponseBody
	public Object findPublishList(@RequestParam(value="pageNum",required=false) Integer pageNum,
			@RequestParam(value="pageSize") Integer pageSize, 
			@RequestParam(value="audit", required=false) Integer audit,
			@RequestParam(value="schoolid", required=false) Integer schoolid,
			@RequestParam(value="keyword", required=false) String keyword) throws UnsupportedEncodingException {
		if( !(audit == 1 || audit == 2 || audit == 3||audit == 4 || audit == 5 || audit == 6) ){
			audit = null;
		}
		if(schoolid==null||schoolid==-1){
			schoolid = null;
		}
		pageNum = pageNum == null? 1:pageNum;
		pageSize = pageSize==null? 12:pageSize;
		PageHelper.startPage(pageNum, pageSize);
		if(keyword.equals("undefined")||keyword.equals(""))
			keyword = null;
		else{
		keyword = URLDecoder.decode(keyword, "UTF-8");
		}
		PageHelper.startPage(pageNum, pageSize);
		Page<PublishEntity> publishEntities = publishMapper.findPublishList(audit, keyword, schoolid);
	    return new PageInfo<PublishDto>(getPublishDtos(publishEntities));
	}
	
	/**
	 * 删除发布
	 * @param msgID
	 * @return
	 */
	@RequestMapping(value = "/deletePublish")
	@ResponseBody
	public Object deletePublish(int publishID,int userID) {
		 publishMapper.deletePublish(publishID);
		 return getPublishDtos(publishMapper.findPublishByUserId(userID));
	}
	
	/**
	 * 根据id查询发布
	 * @param msgID
	 * @return
	 */

	@RequestMapping(value = "/findPublishByID")
	@ResponseBody
	public Object findPublishByID(@RequestParam int publishID) {
		ArrayList<PublishEntity> publishEntities = new ArrayList<PublishEntity>();
		publishEntities.add(publishMapper.findPulishByID(publishID));
		return getPublishDtos(publishEntities);
	}
	/**
	 * 增加发布
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPublish")
	@ResponseBody
	public Object addPublish(PublishEntity publishEntity) throws Exception {
		publishMapper.insertPublish(publishEntity);
		return new BaseDto(0);
	}
}