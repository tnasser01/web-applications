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
@Table(name = "Message")
public class Message implements Serializable{

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "messageId", unique = true, nullable = false)
	private String messageId;
	
	@NotNull
	@Column(name = "senderName", nullable = false)
	private String senderName;
	
	@NotNull
	@Column(name = "recipientName", nullable = false)
	private String recipientName;
	
	@NotNull
	@Column(name = "senderId", nullable = false)
	private String senderId;
	
	@Column(name = "senderType")
	private String senderType;

	@Column(name = "recipientType")
	private String recipientType;
	
	@Column(name = "subject", nullable = false)
	private String subject;
	
	@NotNull
	@Column(name = "recipientId", nullable = false)
	private String recipientId;
	
	@NotNull
	@Column(name = "messageText")
	private String messageText;

	@NotNull
	@Column(name = "recieve_time", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date recieve_time;
	
	@Column(name = "read_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date read_time;

	public Message(){}
	public Message(String senderName, String recipientName, String senderId,
			String recipientId, String subject, String messageText, Date recieve_time, Date read_time) {
		this.senderName = senderName;
		this.recipientName = recipientName;
		this.subject = subject;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.messageText = messageText;
		this.recieve_time = recieve_time;
		this.read_time = read_time;
	}
	
	public Message(String senderName, String recipientName, String senderId,
			String recipientId, String senderType, String recipientType, String subject, String messageText, Date recieve_time, Date read_time) {
		this.senderName = senderName;
		this.recipientName = recipientName;
		this.subject = subject;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.messageText = messageText;
		this.recieve_time = recieve_time;
		this.read_time = read_time;
		this.senderType = senderType;
		this.recipientType = recipientType;
	}

	public Message(String messageId, String senderName, String recipientName, String senderId,
			String recipientId, String subject, String messageText, Date recieve_time, Date read_time) {
		this.messageId = messageId;
		this.senderName = senderName;
		this.subject = subject;
		this.recipientName = recipientName;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.messageText = messageText;
		this.recieve_time = recieve_time;
		this.read_time = read_time;
	}
	
	public Message(String messageId, String senderName, String recipientName, String senderId,
			String recipientId, String senderType, String recipientType, String subject, String messageText, Date recieve_time, Date read_time) {
		this.messageId = messageId;
		this.senderName = senderName;
		this.subject = subject;
		this.recipientName = recipientName;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.messageText = messageText;
		this.recieve_time = recieve_time;
		this.read_time = read_time;
		this.senderType = senderType;
		this.recipientType = recipientType;
	}
	



	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}


	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public Date getRecieve_time() {
		return recieve_time;
	}

	public void setRecieve_time(Date recieve_time) {
		this.recieve_time = recieve_time;
	}

	public Date getRead_time() {
		return read_time;
	}

	public void setRead_time(Date read_time) {
		this.read_time = read_time;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSenderType() {
		return senderType;
	}
	public void setSenderType(String senderType) {
		this.senderType = senderType;
	}
	public String getRecipientType() {
		return recipientType;
	}
	public void setRecipientType(String recipientType) {
		this.recipientType = recipientType;
	}

	

	
}