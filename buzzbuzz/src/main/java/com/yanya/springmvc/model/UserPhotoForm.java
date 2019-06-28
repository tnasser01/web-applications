package com.yanya.springmvc.model;

import java.io.File;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.UserPhoto;

public class UserPhotoForm {
	
   //Save the uploaded product photo to this folder
  private static String PRODUCT_PHOTO_SAVE_PATH = "/media/Rihup/User/UserPhotos/";
   
  private String photoId;
  
  private String userId;

  private String description;
  
  private int likes;

  private MultipartFile userPhoto;

  public MultipartFile getUserPhoto() {
    return userPhoto;
  }

  public void setProductPhoto(MultipartFile userPhoto) {
    this.userPhoto = userPhoto;
  }

public UserPhoto toProduct(Customer customer) {
	System.out.println("Inside product form: " + customer.getUserId() );
	String saveTarget = PRODUCT_PHOTO_SAVE_PATH + customer.getUserId() + File.separator;
	 
	String imagePath = saveTarget + userPhoto.getOriginalFilename();
	System.out.println("Image path generated is: " + imagePath );
	System.out.println("User is " + System.getProperty("user.name"));
    return new UserPhoto(customer, description, userPhoto.getOriginalFilename());
  }

public String getPhotoId() {
	return photoId;
}

public void setPhotoId(String photoId) {
	this.photoId = photoId;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public int getLikes() {
	return likes;
}

public void setLikes(int likes) {
	this.likes = likes;
}

public void setUserPhoto(MultipartFile userPhoto) {
	this.userPhoto = userPhoto;
}

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}
  
}
