package com.yanya.springmvc.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.yanya.springmvc.jsonview.Views;

public class MerchantStatusForm{

	@JsonView(Views.Public.class)
	private String userId;
	
	@JsonView(Views.Public.class)
	private String searchTextField;
	
	@JsonView(Views.Public.class)
	private Boolean isAngelOn;
	
	public MerchantStatusForm(){}
	
	public MerchantStatusForm(String userId, String searchTextField, Boolean isAngelOn){
		this.userId = userId;
		this.searchTextField = searchTextField;
		this.isAngelOn = isAngelOn;  }

	public String getSearchTextField() {
		return this.searchTextField;}
	
	public void setSearchTextField(String address) {
		this.searchTextField = address;}

	public Boolean getIsAngelOn() {
		return isAngelOn;}
	
	public void setIsAngelOn(Boolean isAngelOn) {
		this.isAngelOn = isAngelOn;}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	

	
}