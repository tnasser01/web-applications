package com.yanya.springmvc.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.UserPhoto;

@Entity
@Table(name = "Comment")
public class Comment implements Serializable{

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "commentId", unique = true, nullable = false)
	private String commentId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userPhotoId", nullable = false)
	private UserPhoto userPhoto;

	@Size(min = 1, max = 2200)
	@Column(name = "commentText")
	private String commentText;
	
	@Column(name = "userId")
	private String userId;
	
	@Column(name = "username")
	private String username;
	
 	@Column(name = "timeStamp", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
 	private Date timeStamp;
	
	public Comment() {}

	public Comment(UserPhoto userPhoto, String commentText, String userId, String username, Date timeStamp) {
		this.userPhoto = userPhoto;
		this.commentText = commentText;
		this.userId = userId;
		this.username = username;
		this.timeStamp = timeStamp;
	}
	
	public Comment(String commentId, UserPhoto userPhoto, String commentText, String userId, String username, Date timeStamp) {
		this.commentId = commentId;
		this.userPhoto = userPhoto;
		this.commentText = commentText;
		this.userId = userId;
		this.username = username;
		this.timeStamp = timeStamp;
	}

	@Override
	public int hashCode() {

		if (commentId == null)
			return 0;

		final int prime = 31;
		int result = 1;
		result = prime * result + Integer.parseInt(commentId);
		// result = prime * result + ((ssn == null) ? 0 : ssn.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Comment))
			return false;
		Comment other = (Comment) obj;
		if (commentId != other.commentId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CommentId=" + commentId  + "]";
	}




	public String getCommentId() {
		return commentId;
	}




	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}




	public UserPhoto getUserPhoto() {
		return userPhoto;
	}




	public void setUserPhoto(UserPhoto userPhoto) {
		this.userPhoto = userPhoto;
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