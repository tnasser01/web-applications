package com.yanya.springmvc.model;

import com.yanya.springmvc.jsonview.Views;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "MerchantStatus")
public class MerchantStatus implements Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@JsonView(Views.Public.class)
	String merchantStatusId;
	
	@JsonView(Views.Public.class)
	@Column(name = "userId", unique = true, nullable = false)
	String userId;
	
	@JsonView(Views.Public.class)
	@Column(name = "searchTextField")
	String searchTextField;
	
	@JsonView(Views.Public.class)
	@Column(name = "angelStatus")
	String angelStatus;
	
	@JsonView(Views.Public.class)
	@Column(name = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	Date timestamp;
	
	@JsonView(Views.Public.class)
	@Column(name = "cityLng")
	String cityLng;
	
	@JsonView(Views.Public.class)
	@Column(name = "cityLat")
	String cityLat;
	
	public MerchantStatus(){}
	
	public MerchantStatus(String userId, String searchTextField, String angelStatus, Date timestamp, String citylng, String citylat){
		this.userId = userId;
		this.searchTextField = searchTextField;
		this.angelStatus = angelStatus;
		this.timestamp = timestamp;
		this.cityLng = citylng;
		this.cityLat = citylat;
	}
	
	
	public MerchantStatus(String merchantStatusId, String userId, String searchTextField, String angelStatus, Date timestamp, String citylng, String citylat){
		this.merchantStatusId = merchantStatusId;
		this.userId = userId;
		this.searchTextField = searchTextField;
		this.angelStatus = angelStatus;
		this.timestamp = timestamp;
		this.cityLng = citylng;
		this.cityLat = citylat;
		
	}
	

	public String getSearchTextField() {
		return searchTextField;
	}
	public void setSearchTextField(String searchTextField) {
		this.searchTextField = searchTextField;
	}
	public String getMerchantStatusId() {
		return merchantStatusId;
	}
	public void setMerchantStatusId(String merchantStatusId) {
		this.merchantStatusId = merchantStatusId;
	}

	public String getAngelStatus() {
		return angelStatus;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void setAngelStatus(String angelStatus) {
		this.angelStatus = angelStatus;
	}

	public String getCityLng() {
		return cityLng;
	}

	public void setCityLng(String citylng) {
		this.cityLng = citylng;
	}

	public String getCityLat() {
		return cityLat;
	}

	public void setCityLat(String citylat) {
		this.cityLat = citylat;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	

}