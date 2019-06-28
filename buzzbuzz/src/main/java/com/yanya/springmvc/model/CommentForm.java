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

public class CommentForm{
	
	private String userPhotoId;
	private String commentText;
	private String userId;
	private String username;
 	private Date timeStamp;
	
	public CommentForm() {}

	public CommentForm(String userPhotoId, String commentText, String userId, String username, Date timeStamp) {
		this.userPhotoId = userPhotoId;
		this.commentText = commentText;
		this.userId = userId;
		this.username = username;
		this.timeStamp = timeStamp;
	}

	public String getUserPhotoId() {
		return userPhotoId;
	}

	public void setUserPhotoId(String userPhotoId) {
		this.userPhotoId = userPhotoId;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}