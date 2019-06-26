package com.yanya.springmvc.service;
 
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanya.springmvc.dao.CommentDao;
import com.yanya.springmvc.dao.ConnectionDao;
import com.yanya.springmvc.dao.UserPhotoDao;
import com.yanya.springmvc.dao.ZipDao;
import com.yanya.springmvc.model.UserPhoto;
import com.yanya.springmvc.model.ZipCode;
import com.yanya.springmvc.model.Connection;
import com.yanya.springmvc.model.Impression;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.service.UserPhotoService;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Integer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

@Service("userPhotoService")
@Transactional
public class UserPhotoServiceImpl implements UserPhotoService {
 
    @Autowired
    private UserPhotoDao userPhotoDao;
    
    @Autowired
    private CommentDao commentDao;
    
    @Autowired
    private ConnectionDao connectionDao;
    
    
    public UserPhoto saveNewUserPhoto(UserPhoto userPhoto) {
    	return userPhotoDao.saveNewUserPhoto(userPhoto);
    }
    
    public List<UserPhoto> findPhotosByCustomerId(String userId) {
    	return  userPhotoDao.findPhotosByCustomerId(userId);   	
    }
    
    public UserPhoto findPhotoByPhotoId(String photoId) {
    	return  userPhotoDao.findPhotoByPhotoId(photoId);   	
    }
    
    public void deleteUserPhoto(UserPhoto userPhoto){
    	commentDao.deleteCommentsByUserPhotoId(userPhoto.getUserPhotoId());
    	userPhotoDao.deleteUserPhoto(userPhoto);
    }
    
    public void updatePhotoDescription(String userPhotoId, String description, String userId){
    	userPhotoDao.updatePhotoDescription(userPhotoId, description, userId);
    	
    }
    
   @Cacheable("userPhotos")
   public List<UserPhoto> findUserPhotosVisibleToCustomer(String userId){
    	
    	//add visible userPhotos
    	List<Connection> visibleCustomerConnections = connectionDao.findUserConnectionsByType(userId, "customer");
    	List<String> visibleCustomerIds = new ArrayList<String>();
    	
    	if(visibleCustomerConnections!=null){
	    	for(Connection c: visibleCustomerConnections){
	    		if(c.getRequesteeId().equals(userId)){
	    			visibleCustomerIds.add(c.getRequesterId());
	    		}else{
	    			visibleCustomerIds.add(c.getRequesteeId());
	    		}
	    	}
	    
    	
	    	List<UserPhoto> visibleUserPhotos = userPhotoDao.findAllVisibleUserPhotos(visibleCustomerIds, userId);
	    	List<Impression> impressions = null;
	    	List<String> userPhotoIds = null;
	    	
	    	if(visibleUserPhotos!=null){
	    		for(UserPhoto p : visibleUserPhotos){
	    			p.setLikes();
	    	}
  		
	    	return visibleUserPhotos;
	    	}
    	}
 
    	return null;
    }
   
   
 @Cacheable("userPhotos")
 public List<UserPhoto> findUserPhotosVisibleToMerchant(String userId){
  	
  	//add visible userPhotos
  	List<Connection> visibleCustomerConnections = connectionDao.findUserConnectionsByType(userId, "customer");
  	List<String> visibleCustomerIds = new ArrayList<String>();
  	
  	if(visibleCustomerConnections!=null){
    	for(Connection c: visibleCustomerConnections){
    		if(c.getRequesteeId().equals(userId)){
    			visibleCustomerIds.add(c.getRequesterId());
    		}else{
    			visibleCustomerIds.add(c.getRequesteeId());
    		}
    	}
    
  	
    	List<UserPhoto> visibleUserPhotos = userPhotoDao.findAllVisibleUserPhotos(visibleCustomerIds, userId);
    	List<Impression> impressions = null;
    	List<String> userPhotoIds = null;
    	
    	if(visibleUserPhotos!=null){
    		for(UserPhoto p : visibleUserPhotos){
    			p.setLikes();
    	}
		
    	return visibleUserPhotos;
    	}
  	}

  	return null;
  }

 
}