package com.yanya.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.yanya.springmvc.model.UserPhoto;
import com.yanya.springmvc.model.SearchFilterForm;

import java.lang.Integer;
 
public interface UserPhotoService {
	
	UserPhoto saveNewUserPhoto(UserPhoto userPhoto);
    List<UserPhoto> findPhotosByCustomerId(String userId);
    UserPhoto findPhotoByPhotoId(String userPhotoId);
    void deleteUserPhoto(UserPhoto userPhoto);
    void updatePhotoDescription(String userPhotoId, String description, String userId);
    List<UserPhoto> findUserPhotosVisibleToCustomer(String userId);
    List<UserPhoto> findUserPhotosVisibleToMerchant(String userId);
}