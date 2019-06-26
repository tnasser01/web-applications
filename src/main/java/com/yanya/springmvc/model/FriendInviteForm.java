package com.yanya.springmvc.model;

import static javax.persistence.GenerationType.IDENTITY;

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

public class FriendInviteForm {
	
	private String userId;
	private String phone1;
	private String phone2;
	private String phone3;
	private String phone4;
	private String phone5;
	
	public FriendInviteForm() {}
	public FriendInviteForm(String userId, String phone1, String phone2, String phone3, String phone4, String phone5){
		this.userId  = userId;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.phone3 = phone3;
		this.phone4 = phone4;
		this.phone5 = phone5;
	}


	public String getPhone1(){
		return phone1;
	}


	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}


	public String getPhone2() {
		return phone2;
	}


	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}


	public String getPhone3() {
		return phone3;
	}


	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}


	public String getPhone4() {
		return phone4;
	}


	public void setPhone4(String phone4) {
		this.phone4 = phone4;
	}

	public String getPhone5() {
		return phone5;
	}


	public void setPhone5(String phone5) {
		this.phone5 = phone5;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}


}