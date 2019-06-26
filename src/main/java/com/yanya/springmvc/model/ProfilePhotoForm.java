package com.yanya.springmvc.model;

import java.io.File;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.UserPhoto;

public class ProfilePhotoForm {
	
   //Save the uploaded product photo to this folder
  private static String PROFILE_PHOTO_SAVE_PATH = "/media/Rihup/User/ProfilePhotos/";
  
  private String userId;
  
  private MultipartFile profilePhoto;

  public ProfilePhotoForm(){}
  
  public ProfilePhotoForm(String userId, MultipartFile profilePhoto){
	  this.userId = userId;
	  this.profilePhoto = profilePhoto;
  }


	public MultipartFile getProfilePhoto() {
       return profilePhoto;
   }

	public void setProfilePhoto(MultipartFile profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
  
}
