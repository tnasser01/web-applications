package com.yanya.springmvc.model;

import static javax.persistence.GenerationType.IDENTITY;

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
@Table(name = "Notification")
public class Notification {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "notificationId", unique = true, nullable = false)
	private String notificationId;
	
	@NotNull
	@Column(name = "recipientId")
	private String recipientId;
	
	@NotNull
	@Column(name = "notificationText")
	private String notificationText;

	@NotNull
	@Column(name = "recieve_time", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date recieve_time;
	
	@Column(name = "read_time", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date read_time;

	public Notification(){}
	public Notification(String recipientId, String notificationText, Date recieve_time, Date read_time) {
		this.recipientId = recipientId;
		this.notificationText = notificationText;
		this.recieve_time = recieve_time;
		this.read_time = read_time;
		
	}

	public Notification(String  notificationId, String recipientId, String notificationText, Date recieve_time, Date read_time) {
		this.recipientId = recipientId;
		this.notificationId = notificationId;
		this.notificationText = notificationText;
		this.recieve_time = recieve_time;
		this.read_time = read_time;
		
	}

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public String getNotificationText() {
		return notificationText;
	}

	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
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
	public String getRecipientId() {
		return recipientId;
	}
	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	
}