package com.yanya.springmvc.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "LikeMessage")
public class LikeMessage implements Serializable{

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "likeMessageId", unique = true, nullable = false)
	private String likeMessageId;
	
	@NotNull
	@Column(name = "senderId", nullable = false)
	private String senderId;
	
	@NotNull
	@Column(name = "senderName", nullable = false)
	private String senderName;
	
	@NotNull
	@Column(name = "senderType", nullable = false)
	private String senderType;
	
	@NotNull
	@Column(name = "recipientId", nullable = false)
	private String recipientId;
	
	@NotNull
	@Column(name = "recipientName", nullable = false)
	private String recipientName;
	
	@NotNull
	@Column(name = "recipientType", nullable = false)
	private String recipientType;
	
	@NotNull
	@Column(name = "imagePath")
	private String imagePath;
	
	@NotNull
	@Column(name = "messageText")
	private String messageText;

	@NotNull
	@Column(name = "recieveTime", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date recieveTime;
	
	@Column(name = "impressionId")
	private String impressionId;
	
	@Column(name = "productImpressionId")
	private String productImpressionId;
	
	@Column(name = "readTime")
	private Date readTime;

	public LikeMessage(){}


	public LikeMessage(String senderId, String senderName, String senderType, String recipientId, String recipientName,
			String recipientType, String messageText, String imagePath, Date recieveTime, String impressionId, String productImpressionId, Date readTime) {
		super();
		this.senderId = senderId;
		this.senderName = senderName;
		this.senderType = senderType;
		this.recipientId = recipientId;
		this.recipientName = recipientName;
		this.recipientType = recipientType;
		this.messageText = messageText;
		this.imagePath = imagePath;
		this.recieveTime = recieveTime;
		this.impressionId = impressionId;
		this.productImpressionId =  productImpressionId;
		this.readTime = readTime;
	}


	public LikeMessage(String likeMessageId, String senderId, String senderName, String senderType, String recipientId,
			String recipientName, String recipientType, String messageText, String imagePath, Date recieveTime,  String impressionId, String productImpressionId, Date readTime) {
		super();
		this.likeMessageId = likeMessageId;
		this.senderId = senderId;
		this.senderName = senderName;
		this.senderType = senderType;
		this.recipientId = recipientId;
		this.recipientName = recipientName;
		this.recipientType = recipientType;
		this.messageText = messageText;
		this.recieveTime = recieveTime;
		this.imagePath = imagePath;
		this.impressionId = impressionId;
		this.productImpressionId =  productImpressionId;
		this.readTime = readTime;
	}
	
	public String getLikeMessageId() {
		return likeMessageId;
	}


	public void setLikeMessageId(String likeMessageId) {
		this.likeMessageId = likeMessageId;
	}


	public String getSenderId() {
		return senderId;
	}


	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}


	public String getSenderName() {
		return senderName;
	}


	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}


	public String getSenderType() {
		return senderType;
	}


	public void setSenderType(String senderType) {
		this.senderType = senderType;
	}


	public String getRecipientId() {
		return recipientId;
	}


	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}


	public String getRecipientName() {
		return recipientName;
	}


	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}


	public String getRecipientType() {
		return recipientType;
	}


	public void setRecipientType(String recipientType) {
		this.recipientType = recipientType;
	}


	public String getMessageText() {
		return messageText;
	}


	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}


	public Date getRecieveTime() {
		return recieveTime;
	}


	public void setRecieveTime(Date recieveTime) {
		this.recieveTime = recieveTime;
	}


	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}


	public String getImpressionId() {
		return impressionId;
	}


	public void setImpressionId(String impressionId) {
		this.impressionId = impressionId;
	}


	public String getProductImpressionId() {
		return productImpressionId;
	}


	public void setProductImpressionId(String productImpressionId) {
		this.productImpressionId = productImpressionId;
	}


	public Date getReadTime() {
		return readTime;
	}


	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}


	
}