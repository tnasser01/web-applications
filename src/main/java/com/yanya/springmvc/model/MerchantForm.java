package com.yanya.springmvc.model;
 
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;
import static javax.persistence.GenerationType.IDENTITY;

 
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

 

public class MerchantForm{
 
	
 	@NotNull
 	@Size(min=3,max=30)
    private String storeName;
 	
 	@NotNull
 	@Size(min=3,max=30)
    private String username;
    
 	@NotNull
 	@Size(min=5,max=25)
    private String password;
    
 	@Size(min=5,max=5)
	private String userId;
    
 	@NotNull
 	@Size(min=10,max=10)
    private String phone;
 	
 	@NotNull
    private String state;
 	
 	@NotNull
 	@Size(min=5,max=5)
    private String zipCode;
    
    public MerchantForm(){ }

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
   
    



     
}