package com.yanya.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.yanya.springmvc.model.UserPhoto;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.ZipCode;
 
public interface UserPhotoDao {
 
	@Autowired
	UserPhoto saveNewUserPhoto(UserPhoto userPhoto);

	@Autowired
    UserPhoto findPhotoByPhotoId(String userPhotoId);
 
	@Autowired
    List<UserPhoto> findPhotosByCustomerId(String userPhotoId);
 
	@Autowired
    void deleteUserPhoto(UserPhoto userPhoto);
	
	@Autowired
    void updatePhotoDescription(String userPhotoId, String description, String userId);
	
	@Autowired
    List<UserPhoto> findAllVisibleUserPhotos(List<String> userIds, String userId);
}
