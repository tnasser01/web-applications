package com.yanya.springmvc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.yanya.springmvc.model.UserPhoto;

public class ImpressionForm{
	
	private String userPhotoId;
	private String userId;
	private String username;
	private String impressionType;
	private String userType;
	
	public ImpressionForm() {}
	
	public ImpressionForm(String userPhotoId, String userId, String impressionType, String userType) {
		this.userPhotoId = userPhotoId;
		this.userId = userId;
		this.impressionType = impressionType;
		this.userType = userType;
	}

	public String getUserPhotoId() {
		return userPhotoId;
	}

	public void setUserPhotoId(String userPhotoId) {
		this.userPhotoId = userPhotoId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getImpressionType() {
		return impressionType;
	}

	public void setImpressionType(String impressionType) {
		this.impressionType = impressionType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}






}