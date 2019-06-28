package com.yanya.springmvc.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.yanya.springmvc.model.Customer;

public class VerificationCodeForm {

	private String customerFormId;
	private String phone;
	private String verificationCode;
	
	public VerificationCodeForm() {}

	public VerificationCodeForm(String customerFormId, String phone, String  verificationCode){
		this.customerFormId = customerFormId;
		this.phone = phone;
		this.verificationCode = verificationCode;
	}

	public String getCustomerFormId() {
		return customerFormId;
	}

	public void setCustomerFormId(String customerFormId) {
		this.customerFormId = customerFormId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}


	

}