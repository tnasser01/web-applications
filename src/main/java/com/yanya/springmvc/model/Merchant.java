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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonView;
import com.yanya.springmvc.model.MerchantForm;
 
@Entity
@Table(name="Merchant")
@DiscriminatorValue("merchant")
public class Merchant extends User implements Serializable{
 
	@Column(name = "userId", unique = true, nullable = false, insertable=false, updatable=false)
    private String userId;
	
 	@NotNull
 	@Size(min=3, max=30)
    private String storeName;
 	
 	@NotNull
 	@Size(min=10,max=10)
 	@Column(name = "phone", unique=true, nullable = false)
    private String phone;
 	
    @NotNull
 	@Column(name = "state", nullable = false)
    private String state;
    
    @NotNull
    @Size(min=5,max=5)
    private String zipCode;
    
    @Column(name = "profilePhoto")
    private String profilePhoto;
    
 	@Column(name = "profileBanner")
    private String profileBanner;
 	
 	@Column(name = "profileTagLine", nullable = true)
    private String profileTagLine;
 	
 	@NotNull
 	@Column(name = "visibility", nullable = true)
    private String visibility;
 	
 	@Column(name = "status", nullable = true)
    private String status;
 	
 	@Column(name = "notifications")
 	private Integer notifications;
 	
//	@JsonView(Views.Public.class)
	@Column(name = "location")
	String location;
	
//	@JsonView(Views.Public.class)
	@Column(name = "lng")
	String lng;
	
//	@JsonView(Views.Public.class)
	@Column(name = "lat")
	String lat;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "merchant")
    private Set<Product> products;
    
    public Merchant(){ }
    
    public Merchant(String storeName, String phone,String state, String zipCode, String profilePhoto, String profileBanner, String profileTagLine, String visibility, String status, Integer notifications,  String lat, String lng, String location){
    	this.storeName = storeName;
    	this.phone = phone;
    	this.state = state;
    	this.zipCode = zipCode;
    	this.profilePhoto = profilePhoto;
    	this.profileBanner = profileBanner;
    	this.profileTagLine = profileTagLine;
    	this.visibility = visibility;
    	this.status = status;
    	this.notifications = notifications;
    	this.lat = lat;
    	this.lng = lng;
    	this.location = location;
    }
    
    public Merchant(String userId, String storeName, String phone,String state, String zipCode, String profilePhoto, String profileBanner, String profileTagLine, String visibility, String status, Integer notifications, String lat, String lng, String location){
    	
    	this.userId = userId;
    	this.storeName = storeName;
    	this.phone = phone;
    	this.state = state;
    	this.zipCode = zipCode;
    	this.profilePhoto = "default";
    	this.profileBanner = "default";
    	this.profileTagLine = "";
    	this.visibility = visibility;
    	this.status = status;
    	this.notifications = notifications;
    	this.lat = lat;
    	this.lng = lng;
    	this.location = location;
    }
   
    public Merchant(MerchantForm form){
    	super(form.getUsername(), form.getPassword());
    	this.storeName = form.getStoreName();
    	this.phone = form.getPhone();
    	this.state = form.getState();
    	this.zipCode = form.getZipCode();
    	this.profilePhoto = "default";
    	this.profileBanner = "default";
    	this.profileTagLine = "Enter your tag line here";
    	this.visibility = "private";
    	this.status = "online";
    	this.notifications = 0;
    	this.lat = "";
    	this.lng = "";
    	this.location = "";
    }
    
    public Set<Product> getProducts(){
    	return this.products;
    }
    
    public void setProducts(Set<Product> products){
    		this.products = products;
    }
 	
 	public void setStoreName(String storeName){
 		this.storeName = storeName;
 	}
 	
 	public String getStoreName(){
 		return this.storeName;
 	}
 	
 	public void setPhone(String phone){
 		this.phone = phone;
 	}
 	
 	public String getPhone(){
 		return phone;
 	}
 	
 	public void setState(String state){
 		this.state = state;
 	}
 	
 	public String getState(){
 		return state;
 	}
 	
 	public void setZipCode(String zipCode){
 		this.zipCode = zipCode;
 	}
 	
 	public String getZipCode(){
 		return zipCode;
 	}


 
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        if(userId == null) return 0;
        result = prime * result + Integer.parseInt(userId);
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Merchant))
            return false;
        Merchant other = (Merchant) obj;
        if (userId != other.userId)
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "MerchantId=" + userId + ", MerchantName=" + storeName;
    }


	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getNotifications() {
		return notifications;
	}

	public void setNotifications(Integer notifications) {
		this.notifications = notifications;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public String getProfileBanner() {
		return profileBanner;
	}

	public void setProfileBanner(String profileBanner) {
		this.profileBanner = profileBanner;
	}

	public String getProfileTagLine() {
		return profileTagLine;
	}

	public void setProfileTagLine(String profileTagLine) {
		this.profileTagLine = profileTagLine;
	}



     
}