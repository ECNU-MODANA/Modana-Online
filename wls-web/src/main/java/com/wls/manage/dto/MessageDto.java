package com.wls.manage.dto;

import java.math.BigInteger;
import java.util.Date;

/**
 * 
 * @author jiangkaiqiang
 * @version 创建时间：2016-12-8 下午6:37:42 
 *
 */
public class MessageDto {
    private BigInteger id;
	
	private String msgcategory;
	
	private BigInteger senderid;//发送者id
	
	private String content;
	
	private BigInteger receiverid;//接收者id
	
    private Date informtime;//通知时间
    
    private String senderavatar;
    
    private String receiveravatar;
    
    private String sendername;
    
    private String receivername;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getMsgcategory() {
		return msgcategory;
	}

	public void setMsgcategory(String msgcategory) {
		this.msgcategory = msgcategory;
	}

	public BigInteger getSenderid() {
		return senderid;
	}

	public void setSenderid(BigInteger senderid) {
		this.senderid = senderid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigInteger getReceiverid() {
		return receiverid;
	}

	public void setReceiverid(BigInteger receiverid) {
		this.receiverid = receiverid;
	}

	public Date getInformtime() {
		return informtime;
	}

	public void setInformtime(Date informtime) {
		this.informtime = informtime;
	}

	public String getSenderavatar() {
		return senderavatar;
	}

	public void setSenderavatar(String senderavatar) {
		this.senderavatar = senderavatar;
	}

	public String getReceiveravatar() {
		return receiveravatar;
	}

	public void setReceiveravatar(String receiveravatar) {
		this.receiveravatar = receiveravatar;
	}

	public String getSendername() {
		return sendername;
	}

	public void setSendername(String sendername) {
		this.sendername = sendername;
	}

	public String getReceivername() {
		return receivername;
	}

	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}
    
    
}
