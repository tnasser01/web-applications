package com.yanya.springmvc.model;
 
import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.Size;
 
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.Merchant;

import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.DiscriminatorType;

@Entity
@Table(name="User")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="userType", discriminatorType=DiscriminatorType.STRING)
public class User extends WebSecurityConfigurerAdapter  {
	
 	@Id
	@GeneratedValue(strategy = IDENTITY)
 	@Column(name = "userId", unique=true, nullable = false)
	public String userId;
 	
 	@NotNull
 	@Size(min=3, max=30)
 	@Column(name = "username", unique=true, nullable = false)
	protected String username;
 	
 	@NotNull
 	@Size(min=5, max=25)
 	@Column(name = "password", unique=false, nullable = false)
 	protected String password;
 	
 	@OneToOne
 	@Transient
 	@JoinColumn(name="customer",  updatable=false, insertable=false)
 	private Customer customer;
 	
	@OneToOne
	@Transient
 	@JoinColumn(name="merchant",  updatable=false, insertable=false)
 	private Merchant merchant;
 	
  @Column(name = "userType", insertable = false, updatable = false)
  private String userType;
 	
    public User(){ }
    
    public User(String username, String password){
    	this.username = username;
    	this.password = password;
    	
    }
    
    public User(String username, String password, String userType){
    	this.username = username;
    	this.password = password;
    	this.userType = userType;
    }

    public User(String userId, String username, String password, String userType){
    	this.userId = userId;
    	this.username = username;
    	this.password = password;
    	this.userType = userType;
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
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (userId != other.userId)
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "username=" + username;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}


}