package com.wls.manage.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wls.manage.dao.InforCategoryMapper;
import com.wls.manage.dao.InformationMapper;
import com.wls.manage.dto.BaseDto;
import com.wls.manage.dto.ResultDto;
import com.wls.manage.dto.UploadFileEntity;
import com.wls.manage.entity.InformationEntity;
import com.wls.manage.service.FtpService;
import com.wls.manage.util.ResponseData;
/**
 * 资讯controller
 * @author jkq
 *
 */
@Controller
@RequestMapping(value = "/information")
public class InformationController extends BaseController {
	private static String baseDir = "picture";
	@Autowired
	private FtpService ftpService;

	@Autowired
	private InformationMapper informationDao;
	/**
	 * 提供查询服务
	 * @param pageNum
	 * @param pageSize
	 * @param posterID
	 * @param keyword
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/findAllInformation")
	@ResponseBody
	public Object findAllInformationForAdmin(@RequestParam(value="pageNum",required=false) Integer pageNum,
			@RequestParam(value="pageSize") Integer pageSize, 
			@RequestParam(value="keyword", required=false) String keyword) throws UnsupportedEncodingException {
		pageNum = pageNum == null? 1:pageNum;
		pageSize = pageSize==null? 10:pageSize;
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<InformationEntity>(informationDao.findAllInformation(keyword));
	}
	
	
	@RequestMapping(value = "/findInformationsByCate")
	@ResponseBody
	public Object findInformationsByCate(@RequestParam(value="pageNum",required=false) Integer pageNum,
			@RequestParam(value="pageSize") Integer pageSize,@RequestParam(value="categoryID") String infocategory) throws UnsupportedEncodingException {
		pageNum = pageNum == null? 1:pageNum;
		pageSize = pageSize==null? 10:pageSize;
		PageHelper.startPage(pageNum, pageSize);
		Page<InformationEntity> info = informationDao.findInformationByCategory(infocategory);
		PageInfo<InformationEntity> data = new PageInfo<InformationEntity>(info);
	    return ResponseData.newSuccess(data);
	}
	
	/**
	 * 删除资讯
	 * @param inforID
	 * @return
	 */
	@RequestMapping(value = "/deleteInformation")
	@ResponseBody
	public Object deleteInformation(int inforID) {
		 informationDao.deleteInformation(inforID);
		 return new BaseDto(0);
	}
	
	/**
	 * 根据资讯id查找资讯
	 * @param inforID
	 * @return
	 */
	@RequestMapping(value = "/findInformationByID")
	@ResponseBody
	public Object findInformationByID(@RequestParam int inforID) {
		return informationDao.findInformationByID(inforID);
	}
	
	/**
	 * 添加和修改资讯公用服务
	 * @param information
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/addInformation")
	@ResponseBody
	public Object addInformation( @RequestParam(required = false) MultipartFile uploadcoverpic,InformationEntity information) throws Exception {
		if (information.getTitle() == null || information.getContent() == null) {
			return new ResultDto(-1, "标题和内容不能为空");
		}
		String dir = String.format("%s/infor/%s", baseDir, information.getId());
		if (uploadcoverpic != null) {
			/**
			 * 这个地方需要把information爬取得图片存到ftp服务器上
			 */
			String fileName = String.format("information%s_%s.%s", information.getId(), new Date().getTime(), "jpg");
			UploadFileEntity uploadFileEntity = new UploadFileEntity(fileName, uploadcoverpic, dir);
			ftpService.uploadFile(uploadFileEntity);
			information.setCoverpiclist(FtpService.READ_URL+dir + "/" + fileName);
		}
		else {
			  throw new Exception("资讯上传图片时，封面图片为空异常");
		}
		informationDao.insertInformation(information);
		return new BaseDto(0);
	}
	
}
