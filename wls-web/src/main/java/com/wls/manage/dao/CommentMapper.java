package com.wls.manage.dao;

import com.github.pagehelper.Page;
import com.wls.manage.entity.CommentEntity;
import org.apache.ibatis.annotations.Param;
/**
 * 评论mapper
 * @author jiangkaiqiang
 * @version 创建时间：2016-11-7 上午11:46:33 
 *
 */
public interface CommentMapper {

    public void insertComment(CommentEntity comment);

	Page<CommentEntity> findCommentsByCommenterId(@Param("commenterID") int commenterID);
	
	Page<CommentEntity> findCommentsByCommentId(@Param("commentID") int commentID, 
			@Param("flag") int flag);
	
	int deleteByCommentID(int commentID);
    
}