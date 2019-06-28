package com.yanya.springmvc.event;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public class NotificationEventCartItem{
	
	protected String updateType;
	protected String userId;
	protected String userType;
	protected HttpServletRequest model;
	
	public NotificationEventCartItem(HttpServletRequest model, String updateType, String userId, String userType) {
		this.model = model;
		this.updateType = updateType;
		this.userId = userId;
		this.userType = userType;
	}

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public HttpServletRequest getModel() {
		return model;
	}

	public void setModel(HttpServletRequest model) {
		this.model = model;
	}
	

	
}