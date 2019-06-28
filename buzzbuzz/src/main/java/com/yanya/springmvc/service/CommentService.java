package com.yanya.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.yanya.springmvc.model.Comment;

import java.lang.Integer;

@Service
public interface CommentService {
	
	Comment saveNewComment(Comment comment);
	List<Comment> findCommentsByUserPhotoId(String userPhotoId);

}