package com.yanya.springmvc.service;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanya.springmvc.dao.CommentDao;
import com.yanya.springmvc.model.Comment;
import com.yanya.springmvc.service.CommentService;



@Service("commentService")
@Transactional
public class CommentServiceImpl implements CommentService {
 
    @Autowired
    private CommentDao commentDao;
    
      
    public Comment saveNewComment(Comment comment) {
    	return commentDao.saveNewComment(comment);
    }
    
    public List<Comment> findCommentsByUserPhotoId(String userPhotoId){
    	return commentDao.findCommentsByUserPhotoId(userPhotoId);
    }
 

	
    
   
    
 
 
 
 
}