package com.wls.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wls.manage.dao.CommentMapper;
import com.wls.manage.dao.FileDataMapper;
import com.wls.manage.dao.RdcMapper;
import com.wls.manage.dao.UserMapper;
import com.wls.manage.dto.CommentDTO;
import com.wls.manage.dto.PersonalCommentDTO;
import com.wls.manage.entity.CommentEntity;
import com.wls.manage.entity.FileDataEntity;
import com.wls.manage.entity.RdcEntity;
import com.wls.manage.entity.UserEntity;
import com.wls.manage.service.CommentService;
import com.wls.manage.service.FtpService;
import com.wls.manage.util.SetUtil;
import com.wls.manage.util.TimeUtil;

/**
 * Author: qiunian.sun Date: qiunian.sun(2016-04-28 20:35)
 */
@Service
public class CommentServiceImpl implements CommentService {

	private final Gson gson = new Gson();

	@Autowired
	private CommentMapper commentDao;

	@Autowired
	private UserMapper userDao;
	
	@Autowired
	private RdcMapper rdcDao;
	
	@Autowired
	private FileDataMapper fileDataDao;

	@Override
	public List<CommentEntity> findLastNComment(@RequestParam int rdcID, @RequestParam int npoint) {
		return commentDao.findLastNComment(rdcID, npoint);
	}

	@Override
	public List<CommentDTO> findCommentsRdcID(@RequestParam int rdcID, @RequestParam int npoint) {
		List<CommentEntity> commentDTOList = commentDao.findLastNComment(rdcID, npoint);
		List<CommentDTO> results = Lists.newArrayList();
		for (CommentEntity commentEntity : commentDTOList) {
			CommentDTO commentDTO = new CommentDTO();
			commentDTO.setId(commentEntity.getId());
			commentDTO.setRdcID(commentEntity.getRdcID());
			commentDTO.setContent(commentEntity.getContent());
			commentDTO.setFacilityGrade(commentEntity.getFacilityGrade());
			commentDTO.setGrade(commentEntity.getGrade());
			commentDTO.setLocationGrade(commentEntity.getLocationGrade());
			commentDTO.setSanitaryGrade(commentEntity.getSanitaryGrade());
			commentDTO.setServiceGrade(commentEntity.getServiceGrade());
			commentDTO.setAddTime(TimeUtil.dateToString(commentEntity.getAddTime(), ""));
			commentDTO.setUsefulcnt(commentEntity.getUsefulcnt());
			
			//查出评论图片
			List<FileDataEntity> reviewPics = fileDataDao.findByBelongIdAndCategory(commentEntity.getId(), FileDataMapper.CATEGORY_COMMENT_PIC);
			for (FileDataEntity item:reviewPics) {
				item.setLocation(String.format("http://%s:%s/%s", FtpService.PUB_HOST, FtpService.READPORT, item.getLocation()));
			}
			commentDTO.setReviewPics(reviewPics);;
			commentDTO.setCommerID(commentEntity.getCommerID());
			UserEntity userEntity = userDao.findUserById(commentEntity.getCommerID());
			commentDTO.setCommerName(userEntity.getUsername());
			commentDTO.setAvatar(userEntity.getAvatar());
			results.add(commentDTO);
		}
		return results;
	}

	@Override
	public void insertComment(CommentEntity comment) {
		commentDao.insertComment(comment);
	}

	@Override
	public Page<PersonalCommentDTO> findCommentsUserID(int userID) {
		Page<CommentEntity> commentDTOList = commentDao.findCommentsByUserId(userID);//准备使用连接查询替换
		Page<PersonalCommentDTO> results = new Page<PersonalCommentDTO>();
		for (CommentEntity commentEntity : commentDTOList) {
			PersonalCommentDTO pDto = new PersonalCommentDTO();
			CommentDTO commentDTO = new CommentDTO();
			commentDTO.setId(commentEntity.getId());
			commentDTO.setRdcID(commentEntity.getRdcID());
			commentDTO.setContent(commentEntity.getContent());
			commentDTO.setFacilityGrade(commentEntity.getFacilityGrade());
			commentDTO.setGrade(commentEntity.getGrade());
			commentDTO.setLocationGrade(commentEntity.getLocationGrade());
			commentDTO.setSanitaryGrade(commentEntity.getSanitaryGrade());
			commentDTO.setServiceGrade(commentEntity.getServiceGrade());
			commentDTO.setAddTime(TimeUtil.dateToString(commentEntity.getAddTime(), ""));
			commentDTO.setUsefulcnt(commentEntity.getUsefulcnt());
			
			//查出评论图片
			List<FileDataEntity> reviewPics = fileDataDao.findByBelongIdAndCategory(commentEntity.getId(), FileDataMapper.CATEGORY_COMMENT_PIC);
			for (FileDataEntity item:reviewPics) {
				item.setLocation(String.format("http://%s:%s/%s", FtpService.PUB_HOST, FtpService.READPORT, item.getLocation()));
			}
			commentDTO.setReviewPics(reviewPics);;
			commentDTO.setCommerID(commentEntity.getCommerID());
			UserEntity userEntity = userDao.findUserById(commentEntity.getCommerID());
			List<RdcEntity> rdclist = rdcDao.findRDCByRDCId(commentEntity.getRdcID());
			if(SetUtil.isnotNullList(rdclist)){//修正2016-08-30 500错误
				RdcEntity rdcEntity=rdclist.get(0);
				pDto.setRdcname(rdcEntity.getName());
			}
			commentDTO.setCommerName(userEntity.getUsername());
			commentDTO.setAvatar(userEntity.getAvatar());
			pDto.setCommentdto(commentDTO);
			
			if (!reviewPics.isEmpty()) {
				pDto.setLogo(reviewPics.get(0).getLocation());
			}
			results.add(pDto);
		}
		return results;
	}
}
