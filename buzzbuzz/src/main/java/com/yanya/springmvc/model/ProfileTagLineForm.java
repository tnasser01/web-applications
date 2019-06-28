package com.yanya.springmvc.model;

import java.io.File;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.UserPhoto;

public class ProfileTagLineForm {
	
   //Save the uploaded product photo to this folder
  private static String PROFILE_PHOTO_SAVE_PATH = "/media/Rihup/User/ProfileTagLines/";
  
  private String userId;
  
  private String profileTagLine;

  public ProfileTagLineForm(){}
  
  public ProfileTagLineForm(String userId, String profileTagLine){
	  this.userId = userId;
	  this.profileTagLine = profileTagLine;
  }


	public String getProfileTagLine() {
       return profileTagLine;
   }

	public void setProfileTagLine(String profileTagLine) {
		this.profileTagLine = profileTagLine;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
  
}
