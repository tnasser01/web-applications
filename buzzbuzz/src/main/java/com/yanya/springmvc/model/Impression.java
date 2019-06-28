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
import javax.persistence.IdClass;
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
import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.UserPhoto;


@Entity
@Table(name = "Impression")
public class Impression implements Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "impressionId", unique = true, nullable = false)
	private String impressionId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userPhotoId", nullable = false)	
	private UserPhoto userPhoto;
	
	@Column(name = "userId", unique = true, nullable = false)
	private String userId;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "userType")
	private String userType;
	
	@Column(name = "impressionType")
	private String impressionType;
	
	public Impression() {}

	public Impression(UserPhoto userPhoto, String userId, String username, String impressionType,  String userType) {
		this.userPhoto = userPhoto;
		this.userId = userId;
		this.username = username;
		this.impressionType = impressionType;
		this.userType = userType;
	}
	
	
	public Impression(String impressionId, UserPhoto userPhoto, String userId, String username, String impressionType, String userType) {
		this.impressionId = impressionId;
		this.userPhoto = userPhoto;
		this.userId = userId;
		this.username = username;
		this.impressionType = impressionType;
		this.userType = userType;
	}
	

	public UserPhoto getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(UserPhoto userPhoto) {
		this.userPhoto = userPhoto;
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

	@Override
	public int hashCode() {
		Integer result = 0;
		if((userPhoto == null) || userId == null){
			return result;
		}
		
		result = 23 * Integer.parseInt(userId) + 31 * Integer.parseInt(userPhoto.getUserPhotoId());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Impression))
			return false;
		Impression other = (Impression) obj;
		if ((impressionId != other.impressionId)){
			return false;
		}
		return true;
	}

	public String getImpressionId() {
		return impressionId;
	}

	public void setImpressionId(String impressionId) {
		this.impressionId = impressionId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}



}