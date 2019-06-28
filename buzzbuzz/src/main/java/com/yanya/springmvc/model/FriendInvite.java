package com.yanya.springmvc.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "FriendInvite")
public class FriendInvite {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "friendInviteId", unique = true, nullable = false)
	private String friendInviteId;
	
	@NotNull
	private String userId;

	@NotNull
	@Column(name = "referredPhoneNumber")
	private String referredPhoneNumber;
	
	@NotNull
	@Column(name = "sendTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendTime;
	
	@NotNull
	@Column(name =  "status")
	private String status;
	
	@Column(name = "inviteCode")
	private String inviteCode;
	
	public FriendInvite() {}


	public FriendInvite(String userId, String referredPhoneNumber, Date sendTime, String status) {
		this.userId = userId;
		this.referredPhoneNumber = referredPhoneNumber;
		this.sendTime = sendTime;
		this.status = status;
	}
	
	public FriendInvite(String friendInviteId, String userId, String referredPhoneNumber, Date sendTime, String status) {
		this.friendInviteId = friendInviteId;
		this.userId = userId;
		this.referredPhoneNumber = referredPhoneNumber;
		this.sendTime = sendTime;
		this.status = status;
	}
	

	@Override
	public int hashCode() {

		if (friendInviteId == null)
			return 0;

		final int prime = 31;
		int result = 1;
		result = prime * result + Integer.parseInt(friendInviteId);
		

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof FriendInvite))
			return false;
		FriendInvite other = (FriendInvite) obj;
		if (friendInviteId != other.friendInviteId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "friendInviteId=" + friendInviteId + "]";
	}


	public String getFriendInviteId() {
		return friendInviteId;
	}


	public void setFriendInviteId(String friendInviteId) {
		this.friendInviteId = friendInviteId;
	}


	public String getReferredPhoneNumber() {
		return referredPhoneNumber;
	}


	public void setReferredPhoneNumber(String referredPhoneNumber) {
		this.referredPhoneNumber = referredPhoneNumber;
	}

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getInviteCode() {
		return inviteCode;
	}


	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}


	public Date getSendTime() {
		return sendTime;
	}


	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}



}