package com.yanya.springmvc.event;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.yanya.springmvc.model.LikeMessage;

public class LikeMessageNotificationEvent extends NotificationHeartEvent{
	

	private LikeMessage likeMessage;
	
	public LikeMessageNotificationEvent(LikeMessage likeMessage,HttpServletRequest model, String updateType, String userId, String userType) {
		super(model, updateType, userId, userType);
		System.out.println("inside LikeMEssageNotificationEVent consutrctor");
		System.out.println("super called in consjutrctor");
		this.likeMessage = likeMessage;
		
	}

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		super.updateType = updateType;
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
		super.userType = userType;
	}

	public HttpServletRequest getModel() {
		return model;
	}

	public void setModel(HttpServletRequest model) {
		super.model = model;
	}

	public LikeMessage getLikeMessage() {
		return likeMessage;
	}

	public void setLikeMessage(LikeMessage likeMessage) {
		this.likeMessage = likeMessage;
	}
	

	
}