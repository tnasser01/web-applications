package com.yanya.springmvc.model;

import com.yanya.springmvc.jsonview.Views;
import com.yanya.springmvc.model.Merchant;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonView;

//@Entity
//@Table(name = "CurrentMerchantStatus")
public class CurrentMerchantStatus implements Serializable {

//	@Id
//	@JsonView(Views.Public.class)
//	@Column(name = "currentMerchantStatusId", unique = true, nullable = false)
//	String currentMerchantStatusId;
//	
//	@OneToOne(fetch = FetchType.EAGER, mappedBy="currentMerchantStatus")
//	Merchant merchant;
//		
//	@JsonView(Views.Public.class)
//	@Column(name = "status")
//	String status;
//	
//	@JsonView(Views.Public.class)
//	@Column(name = "location")
//	String location;
//	
//	@JsonView(Views.Public.class)
//	@Column(name = "cityLng")
//	String cityLng;
//	
//	@JsonView(Views.Public.class)
//	@Column(name = "cityLat")
//	String cityLat;
//	
//	@JsonView(Views.Public.class)
//	@Column(name = "storeName")
//	String storeName;
//	
//	@Column(name = "visibility")
//	String visibility;
//	
//	public CurrentMerchantStatus(){}
//	
//	public CurrentMerchantStatus(String currentMerchantStatusId, String status, String location, String cityLng, String cityLat, String storeName, String visibility){
//		this.currentMerchantStatusId = currentMerchantStatusId;
//		this.status = status;
//		this.location = location;
//		this.cityLng = cityLng;
//		this.cityLat = cityLat;
//		this.storeName = storeName;
//		this.visibility = visibility;
//		
//	}
//	
//	public CurrentMerchantStatus(Merchant merchant, String status, String location, String cityLng, String cityLat, String storeName, String visibility){
//		this.merchant = merchant;
//		this.status = status;
//		this.location = location;
//		this.cityLng = cityLng;
//		this.cityLat = cityLat;
//		this.storeName = storeName;
//		this.visibility = visibility;
//		
//	}
//	
//
//	
//	public Merchant getMerchant(){
//		return merchant;
//	}
//	
//	public void setMerchant(Merchant merchant){
//		this.merchant = merchant;
//	}
//	
//
//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	public String getLocation() {
//		return location;
//	}
//
//	public void setLocation(String location) {
//		this.location = location;
//	}
//
//	public String getCityLng() {
//		return cityLng;
//	}
//
//	public void setCityLng(String cityLng) {
//		this.cityLng = cityLng;
//	}
//
//	public String getCityLat() {
//		return cityLat;
//	}
//
//	public void setCityLat(String cityLat) {
//		this.cityLat = cityLat;
//	}
//
//	public String getStoreName() {
//		return storeName;
//	}
//
//	public void setStoreName(String storeName) {
//		this.storeName = storeName;
//	}
//
//	public String getVisibility() {
//		return visibility;
//	}
//
//	public void setVisibility(String visibility) {
//		this.visibility = visibility;
//	}
//
//
//
//	public String getCurrentMerchantStatusId() {
//		return currentMerchantStatusId;
//	}
//
//	public void setCurrentMerchantStatusId(String currentMerchantStatusId) {
//		this.currentMerchantStatusId = currentMerchantStatusId;
//	}
//	

}