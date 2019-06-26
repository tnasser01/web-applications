package com.yanya.springmvc.model;

import static javax.persistence.GenerationType.IDENTITY;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonView;
import com.yanya.springmvc.jsonview.Views;

import org.springframework.format.annotation.DateTimeFormat;

//@Entity
//@Table(name = "MerchantStatus2")
public class MerchantStatus2{
	
	@Id 
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "merchantStatusId", unique = true, nullable = false)
	private String merchantStatusId;
	
	@JsonView(Views.Public.class)
	@Column(name = "userId", nullable = false)
	private String userId;

	@JsonView(Views.Public.class)
	@Column(name = "searchTextField")
	private String searchTextField = " ";
	
	@JsonView(Views.Public.class)
	@Column(name = "status", nullable = true)
	private String status;
	
	public MerchantStatus2(){}
	
	public MerchantStatus2(String userId, String searchTextField, String status){
		this.userId = userId;
		this.searchTextField = searchTextField;
//		this.timestamp = new Date();
		this.status = status;

	}
	
	public MerchantStatus2(String merchantStatusId, String userId, String searchTextField, String status){
		this.merchantStatusId = merchantStatusId;
		this.userId = userId;
		this.searchTextField = searchTextField;
		this.status = status;
	}

	public String getSearchTextField() {
		return this.searchTextField;
	}

	public void setSearchTextField(String address) {
		this.searchTextField = address;
	}

	public String getMerchantStatusId() {
		return merchantStatusId;
	}

	public void setMerchantStatusId(String merchantStatusId) {
		this.merchantStatusId = merchantStatusId;
	}

	public String getMerchantId() {
		return userId;
	}

	public void setMerchantId(String userId) {
		this.userId = userId;
	}

//	public Date getTimestamp() {
//		return timestamp;
//	}
//
//	public void setTimestamp(Date timestamp) {
//		this.timestamp = timestamp;
//	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	
}