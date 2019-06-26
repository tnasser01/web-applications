package com.yanya.springmvc.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
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
@Table(name = "Connection")
public class Connection implements Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "connectionId", unique = true, nullable = false)
	private String connectionId;

	@Column(name = "requesterId")
	private String requesterId;

	@Column(name = "requesteeId")
	private String requesteeId;
	
	@Column(name = "requesterName")
	private String requesterName;

	@Column(name = "requesteeName")
	private String requesteeName;
	
	@Column(name = "requesterType")
	private String requesterType;

	@Column(name = "requesteeType")
	private String requesteeType;
	
	@Column(name = "requesterImage")
	private String requesterImage;
	
	@Column(name = "requesteeImage")
	private String requesteeImage;
	
	@Column(name = "status")
	private String status;

	
	@Column(name = "readTime")
	private Integer readTime;
	
	public Connection() {}

	public Connection(String requesterId, String requesteeId, String requesterName, String requesteeName, String requesterType, String requesteeType, String requesterImage, String requesteeImage, String status, String imagePath,  Integer readTime) {
		this.requesterId = requesterId;
		this.requesteeId = requesteeId;
		this.requesterName = requesterName;
		this.requesteeName = requesteeName;
		this.requesterType = requesterType;
		this.requesteeType = requesteeType;
		this.requesterImage = requesterImage;
		this.requesteeImage = requesteeImage;
		this.status = status;
		this.readTime = readTime;
	}


	public Connection(String connectionId, String requesterId, String requesteeId, String requesterName, String requesteeName, String requesterType, String requesteeType,String requesterImage, String requesteeImage, String status, String imagePath, Integer readTime) {
		this.connectionId = connectionId;
		this.requesterId = requesterId;
		this.requesteeId = requesteeId;
		this.requesterName = requesterName;
		this.requesteeName = requesteeName;
		this.requesterType = requesterType;
		this.requesteeType = requesteeType;
		this.requesterImage = requesterImage;
		this.requesteeImage = requesteeImage;
		this.status = status;
		this.readTime = readTime;
	}

	@Override
	public int hashCode() {

		if (connectionId == null)
			return 0;

		final int prime = 31;
		int result = 1;
		result = prime * result + Integer.parseInt(connectionId);
		

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Connection))
			return false;
		Connection other = (Connection) obj;
		if (connectionId != other.connectionId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ConnectionId=" + connectionId + "]";
	}

	public String getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}

	public String getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(String requesterId) {
		this.requesterId = requesterId;
	}

	public String getRequesteeId() {
		return requesteeId;
	}

	public void setRequesteeId(String requesteeId) {
		this.requesteeId = requesteeId;
	}

	public String getRequesterName() {
		return requesterName;
	}

	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}

	public String getRequesteeName() {
		return requesteeName;
	}

	public void setRequesteeName(String requesteeName) {
		this.requesteeName = requesteeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRequesterType() {
		return requesterType;
	}

	public void setRequesterType(String requesterType) {
		this.requesterType = requesterType;
	}

	public String getRequesteeType() {
		return requesteeType;
	}

	public void setRequesteeType(String requesteeType) {
		this.requesteeType = requesteeType;
	}




	public Integer getReadTime() {
		return readTime;
	}

	public void setReadTime(Integer readTime) {
		this.readTime = readTime;
	}

	public String getRequesterImage() {
		return requesterImage;
	}

	public void setRequesterImage(String requesterImage) {
		this.requesterImage = requesterImage;
	}

	public String getRequesteeImage() {
		return requesteeImage;
	}

	public void setRequesteeImage(String requesteeImage) {
		this.requesteeImage = requesteeImage;
	}


	


}