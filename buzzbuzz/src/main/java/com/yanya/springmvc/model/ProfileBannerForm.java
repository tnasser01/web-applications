package com.yanya.springmvc.model;

import java.io.File;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.UserPhoto;

public class ProfileBannerForm {
	
   //Save the uploaded product photo to this folder
  private static String PROFILE_PHOTO_SAVE_PATH = "/media/Rihup/User/ProfileBanners/";
  
  private String userId;
  
  private MultipartFile profileBanner;

  public ProfileBannerForm(){}
  
  public ProfileBannerForm(String userId, MultipartFile profileBanner){
	  this.userId =userId;
	  this.profileBanner = profileBanner;
  }


	public MultipartFile getProfileBanner() {
		return profileBanner;
	}

	public void setProfileBanner(MultipartFile profileBanner) {
		this.profileBanner = profileBanner;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


  
}
