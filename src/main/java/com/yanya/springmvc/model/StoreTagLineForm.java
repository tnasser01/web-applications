package com.yanya.springmvc.model;

import java.io.File;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.UserPhoto;

public class StoreTagLineForm {
	
   //Save the uploaded product photo to this folder
  private static String PROFILE_PHOTO_SAVE_PATH = "/media/Rihup/User/StoreTagLines/";
  
  private String userId;
  
  private String storeTagLine;

  public StoreTagLineForm(){}
  
  public StoreTagLineForm(String userId, String storeTagLine){
	  this.userId = userId;
	  this.storeTagLine = storeTagLine;
  }


	public String getStoreTagLine() {
       return storeTagLine;
   }

	public void setStoreTagLine(String storeTagLine) {
		this.storeTagLine = storeTagLine;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
  
}
