package com.wls.manage.entity;

import java.math.BigInteger;
import java.util.Date;
/**
 * 评论实体类
 * @author jiangkaiqiang
 * @version 创建时间：2016-10-21 下午1:53:51 
 *
 */
public class CommentEntity {
	private BigInteger id;

	private BigInteger commenterid;//评论者id
	
	private BigInteger commentid;//评论对象的id
	
	private Integer flag; //区分评论的对象 0:information 1:publish
	
	private String content;
	
	private Date commenttime;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getCommenterid() {
		return commenterid;
	}

	public void setCommenterid(BigInteger commenterid) {
		this.commenterid = commenterid;
	}

	public BigInteger getCommentid() {
		return commentid;
	}

	public void setCommentid(BigInteger commentid) {
		this.commentid = commentid;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCommenttime() {
		return commenttime;
	}

	public void setCommenttime(Date commenttime) {
		this.commenttime = commenttime;
	}

	
}
