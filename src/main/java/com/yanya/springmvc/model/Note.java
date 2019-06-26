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
@Table(name = "Note")
public class Note {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "entryId", unique = true, nullable = false)
	private String entryId;
	
	@NotNull
	@Column(name = "userId", nullable = false)
	private String userId;

	@NotNull
	@Column(name = "customerName", nullable = false)
	private String customerName;

	@NotNull
	@Size(min = 3, max = 45)
	@Column(name = "merchantId")
	private String merchantId;

	@NotNull
	@Column(name = "storeName", nullable = false)
	private String storeName;

	@NotNull
	@Column(name = "bodyText", nullable = false)
	private String bodyText;

	@NotNull
	@Column(name = "entryDate")
	private Date entryDate;

	@Column(name = "lastEditDate")
	private Date lastEditDate;
	
	public Note() {}

	public Note(String userId, String customerName, String merchantId, String storeName, String bodyText,
			Date entryDate, Date lastEditDate) {
		this.userId = userId;
		this.customerName = customerName;
		this.merchantId = merchantId;
		this.storeName = storeName;
		this.bodyText = bodyText;
		this.entryDate = entryDate;
		this.lastEditDate = lastEditDate;
	}

	public Note(String entryId, String userId, String customerName, String merchantId, String storeName,
			String bodyText, Date entryDate, Date lastEditDate) {
		this.entryId = entryId;
		this.userId = userId;
		this.customerName = customerName;
		this.merchantId = merchantId;
		this.storeName = storeName;
		this.bodyText = bodyText;
		this.entryDate = entryDate;
		this.lastEditDate = lastEditDate;
	}

	@Override
	public int hashCode() {

		if (entryId == null)
			return 0;

		final int prime = 31;
		int result = 1;
		result = prime * result + Integer.parseInt(entryId);

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Note))
			return false;
		Note other = (Note) obj;
		if (entryId != other.entryId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "entryId=" + entryId + ", customerName=" + customerName + "]";
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantId() {
		return merchantId;
	}
	
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getbodyText() {
		return bodyText;
	}

	public void setbodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getLastEditDate() {
		return lastEditDate;
	}

	public void setLastEditDate(Date lastEditDate) {
		this.lastEditDate = lastEditDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}



}