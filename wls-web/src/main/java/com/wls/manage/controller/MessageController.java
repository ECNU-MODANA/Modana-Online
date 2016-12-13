package com.wls.manage.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
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
import com.wls.manage.dao.MessageMapper;
import com.wls.manage.dao.MsgCategoryMapper;
import com.wls.manage.dao.ResumeVisMapper;
import com.wls.manage.dao.UserMapper;
import com.wls.manage.dto.MessageDto;
import com.wls.manage.entity.MessageEntity;
import com.wls.manage.entity.ResumeVisEntity;
import com.wls.manage.entity.UserEntity;
import com.wls.manage.util.ResponseData;
/**
 * 
 * @author kaiqiang jiang
 * @version 创建时间：2016-9-13 下午2:24:54
 * 消息通知controller
 */
@Controller
@RequestMapping(value = "/message")
public class MessageController extends BaseController {
	@Autowired
	private MessageMapper messageDao;
	@Autowired
	private UserMapper userMapper;
	@Autowired 
	private MsgCategoryMapper msgCategoryDao;
	@Autowired 
	private ResumeVisMapper resumeVisMapper;
	
	/**
	 * 为前台user查询通知提供服务
	 * @param pageNum
	 * @param pageSize
	 * @param userID
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/findMessageByUserId")
	@ResponseBody
	public Object findMessageByUserId(
			@RequestParam(value="userID", required=false) Integer userID) throws UnsupportedEncodingException {
		List<MessageEntity> data= new ArrayList<MessageEntity>(messageDao.findMessageBySenderID(userID));
		return ResponseData.newSuccess(data);
	}
	
	
	@RequestMapping(value = "/findMessageByReceiverId")
	@ResponseBody
	public Object findMessageByReceiverId(
			@RequestParam(value="userID", required=false) Integer userID) throws UnsupportedEncodingException {
		List<MessageEntity> messageEntities= new ArrayList<MessageEntity>(messageDao.findMessageByReceiverID(userID));
		List<MessageDto> messageDtos = new ArrayList<MessageDto>();
		for (MessageEntity messageEntity : messageEntities) {
			MessageDto messageDto = new MessageDto();
			messageDto.setId(messageEntity.getId());
			messageDto.setContent(messageEntity.getContent());
			messageDto.setInformtime(messageEntity.getInformtime());
			messageDto.setMsgcategory(messageEntity.getMsgcategory());
			messageDto.setReceiverid(messageEntity.getReceiverid());
			messageDto.setSenderid(messageEntity.getSenderid());
			UserEntity senderEntity  = userMapper.findUserById(messageEntity.getSenderid().intValue());
			UserEntity ReceiverEntity  = userMapper.findUserById(messageEntity.getReceiverid().intValue());
			messageDto.setReceiveravatar(ReceiverEntity.getAvatar());
			messageDto.setReceivername(ReceiverEntity.getNickname());
			messageDto.setSenderavatar(senderEntity.getAvatar());
			if (senderEntity.getNickname()==null) {
				messageDto.setSendername("游客"+senderEntity.getId());
			}
			else {
				messageDto.setSendername(senderEntity.getNickname());
			}
			messageDtos.add(messageDto);
		}
		return messageDtos;
	}
	
	/**
	 * 根据分类查询消息
	 * @param pageNum
	 * @param pageSize
	 * @param categoryID
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/findMessageByCate")
	@ResponseBody
	public Object findMessageByCate(@RequestParam(value="pageNum",required=false) Integer pageNum,
			@RequestParam(value="pageSize") Integer pageSize,@RequestParam(value="category") String category) throws UnsupportedEncodingException {
		pageNum = pageNum == null? 1:pageNum;
		pageSize = pageSize==null? 10:pageSize;
		PageHelper.startPage(pageNum, pageSize);
		Page<MessageEntity> msg = messageDao.findMessageByCategory(category);
		PageInfo<MessageEntity> data = new PageInfo<MessageEntity>(msg);
	    return ResponseData.newSuccess(data);
	}
	
	/**
	 * 删除消息
	 * @param msgID
	 * @return
	 */
	@RequestMapping(value = "/deleteMessage")
	@ResponseBody
	public Object deleteMessage(int msgID,Integer messageSenderID) {
		 messageDao.deleteMessage(msgID);
		 return  messageDao.findMessageByReceiverID(messageSenderID);
	}
	
	/**
	 * 根据id查询消息
	 * @param msgID
	 * @return
	 */

	@RequestMapping(value = "/findMessageByID")
	@ResponseBody
	public Object findMessageByID(@RequestParam int msgID) {
		return messageDao.findMessageByID(msgID);
	}
	/**
	 * 增加消息
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addMessage")
	@ResponseBody
	public Object addMessage(
			@RequestParam(value="messageReceiverID",required=false) Integer messageReceiverID,
			@RequestParam(value="messageContent",required=false) String messageContent,
			@RequestParam(value="msgcategory",required=false) String msgcategory,
			@RequestParam(value="messageSenderID",required=false) Integer messageSenderID) throws Exception {
		if (messageSenderID==null) {
			return ResponseData.newFailure("请先登录");
		}
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setContent(messageContent);
		messageEntity.setMsgcategory(msgcategory);
		messageEntity.setReceiverid(BigInteger.valueOf(messageReceiverID));
		messageEntity.setSenderid(BigInteger.valueOf(messageSenderID));
		messageDao.insertMessage(messageEntity);
		if (msgcategory.equals("1")) {
			return ResponseData.newSuccess("消息已发送，等待对方回复");
		}
		return ResponseData.newSuccess("请求已发送，等待对方同意");
	}
	
	/**
	 * 增加消息
	 * @param message
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/messageResponse")
	@ResponseBody
	public Object messageResponse(@RequestParam(value="messageID",required=false) Integer messageID,
			@RequestParam(value="messageReceiverID",required=false) Integer messageReceiverID,
			@RequestParam(value="messageResponse",required=false) String messageResponse,
			@RequestParam(value="msgcategory",required=false) String msgcategory,
			@RequestParam(value="messageSenderID",required=false) Integer messageSenderID) throws Exception {
		if (msgcategory.equals("1")) {
			MessageEntity messageEntity = new MessageEntity();
			messageEntity.setContent(messageResponse);
			messageEntity.setMsgcategory("1");
			messageEntity.setReceiverid(BigInteger.valueOf(messageReceiverID));
			messageEntity.setSenderid(BigInteger.valueOf(messageSenderID));
			messageDao.insertMessage(messageEntity);
		}
		if (msgcategory.equals("2")) {
			ResumeVisEntity resumeVisEntity = new ResumeVisEntity();
			resumeVisEntity.setOwnerid(BigInteger.valueOf(messageSenderID));
			resumeVisEntity.setVisiblerid(BigInteger.valueOf(messageReceiverID));
			resumeVisMapper.insertResumeVis(resumeVisEntity);
		}
		messageDao.deleteMessage(messageID);
		return messageDao.findMessageByReceiverID(messageSenderID);
	}
	
	/**
	 * 查询所有的消息类别
	 * @return
	 */
	@RequestMapping(value = "/findAllMsgCategory")
	@ResponseBody
	public Object findAllMsgCategory() {
		return msgCategoryDao.findAllMsgCategory();
	}
}