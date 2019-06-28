package com.yanya.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.yanya.springmvc.model.Comment;
 
public interface CommentDao {
 
	@Autowired
	Comment saveNewComment(Comment comment);
	
	@Autowired
    List<Comment> findCommentsByUserPhotoId(String userPhotoId);
	
	@Autowired
    void deleteCommentsByUserPhotoId(String userPhotoId);
}