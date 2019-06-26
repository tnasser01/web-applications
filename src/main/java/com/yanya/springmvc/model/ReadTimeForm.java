package com.yanya.springmvc.model;

import java.io.File;
import java.lang.String;

public class ReadTimeForm {
	
  private String userId;
  
  private String messageId;
  
  private String recipientType;
  
  private String readTime;

  public ReadTimeForm(String recipientType, String userId, String messageId, String readTime){
	  
	  this.recipientType = recipientType;
	  this.userId = userId;
	  this.messageId = messageId;
	  this.readTime = readTime;
	  
  }

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}

public String getMessageId() {
	return messageId;
}

public void setMessageId(String messageId) {
	this.messageId = messageId;
}

public String getReadTime() {
	return readTime;
}

public void setReadTime(String readTime) {
	this.readTime = readTime;
}

public String getRecipientType() {
	return recipientType;
}

public void setRecipientType(String recipientType) {
	this.recipientType = recipientType;
}
  
  
 


  
}
