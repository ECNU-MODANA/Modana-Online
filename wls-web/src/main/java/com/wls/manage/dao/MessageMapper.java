package com.wls.manage.dao;


import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.wls.manage.entity.MessageEntity;
/**
 * 
 * @author kaiqiang jiang
 * @version 创建时间：2016-9-13 上午11:41:07
 * 消息通知mapper
 */
public interface MessageMapper {

	MessageEntity findMessageByID(@Param("msgID") int msgID);
	
	Page<MessageEntity> findAllMessage(@Param("keyword")String keyword);
	
	Page<MessageEntity> findMessageBySenderID(@Param("senderID") int senderID);
	
	Page<MessageEntity> findMessageByReceiverID(@Param("receiverID") int receiverID);
	
	Page<MessageEntity> findMessageByCategory(@Param("msgcategory") String msgcategory);

	void insertMessage(MessageEntity messageEntity);
	
	void deleteMessage(@Param("msgID") int msgID);

	
}
