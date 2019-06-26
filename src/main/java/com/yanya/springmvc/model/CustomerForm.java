package com.yanya.springmvc.model;
 
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

import com.yanya.springmvc.model.FriendInvite;
import com.yanya.springmvc.model.UserPhoto;


@Entity
@Table(name="CustomerForm")
public class CustomerForm {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerFormId", unique = true, nullable = false)
    private String customerFormId;
 	
 	@NotNull
 	@Size(min=3, max=30)
 	@Column(name = "username", unique=true, nullable = false)
 	protected String username;
 	
 	@NotNull
 	@Size(min=5, max=25)	
 	@Column(name = "password", nullable = false)
 	protected String password;
    
 	@NotNull
    @Size(min=10, max=10)
    @Column(name = "phone", unique=true, nullable = false)
 	private String phone;
 	
 	@NotNull
 	@Column(name = "profilePhoto", nullable = false)
 	private String profilePhoto;
 	
 	@NotNull
 	@Column(name = "visibility", nullable = false)
 	private String visibility;
 	
 	@Column(name = "invitation")
 	private String invitation;

 	@Column(name = "authyId")
 	private Integer authyId;
 	
    public CustomerForm(){ }

    public CustomerForm(String username, String phone, String password, String profilePhoto, String visibility, String invitation, Integer authyId){
    	this.customerFormId = null;
    	this.phone = phone;
    	this.username = username;
    	this.password = password;
    	this.profilePhoto = profilePhoto;
    	this.visibility = visibility;
    	this.invitation = invitation;
    	this.authyId = 0;
    	
    }
    
    public CustomerForm(String customerFormId, String username, String phone, String password, String profilePhoto, String visibility, String invitation, Integer authyId){
    	this.customerFormId = customerFormId;
    	this.phone = phone;
    	this.username = username;
    	this.password = password;
    	this.profilePhoto = "default";
    	this.visibility = visibility;
    	this.invitation = invitation;
    	this.authyId = 0;
    

    }
    
    public String getCustomerFormId() {
        return customerFormId;
    }  
    
    public void setCustomerFormId(String customerFormId) {
        this.customerFormId = customerFormId;
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
    
 	public String getPhone() {
        return phone;
 	 }
 	 
 	public void setPhone(String phone) {
        this.phone = phone;
    }
 
    public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        if(customerFormId ==null) return 0;
        result = prime * result + Integer.parseInt(customerFormId);
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof CustomerForm))
            return false;
        CustomerForm other = (CustomerForm) obj;
        if (customerFormId != other.customerFormId)
            return false;
        return true;
    }  
 
    @Override
    public String toString() {
        return "CustomerForm customerFormId=" + customerFormId + ", username=" + username + ", phone=" + phone + "]";
    }

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getInvitation() {
		return invitation;
	}

	public void setInvitation(String invitation) {
		this.invitation = invitation;
	}

	public Integer getAuthyId() {
		return authyId;
	}

	public void setAuthyId(Integer authyId) {
		this.authyId = authyId;
	}


     
}