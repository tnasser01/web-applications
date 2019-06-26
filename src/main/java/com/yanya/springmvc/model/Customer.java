package com.yanya.springmvc.model;
 
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.FetchMode.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.yanya.springmvc.model.FriendInvite;
import com.yanya.springmvc.model.UserPhoto;
//import com.yanya.springmvc.model.Cart;
import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.CustomerForm;


@Entity
@Table(name="Customer")
@DiscriminatorValue("customer")
public class Customer extends User implements Serializable {
 
  @Column(name = "userId", unique = true, nullable = false, insertable=false, updatable=false)
	private String userId;
	
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
 	
 	@Column(name = "notifications")
 	private Integer notifications;
 	
 	@Column(name = "profileTagLine")
 	private String profileTagLine;
 	
 	@Column(name = "profileBanner")
 	private String profileBanner;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
  @Fetch(value = FetchMode.SUBSELECT)
  private Set<UserPhoto> userPhotos;
  
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
  private List<CartItem> cartItems;
  
// 	
//  @OneToOne(fetch = FetchType.EAGER)
//  private Cart cart;
//  
    public Customer(){ }

    public Customer(
    				String phone, 
    				String profilePhoto, 
    				String visibility, 
    				String invitation, 
    				Integer authyId, 
    				Integer notifications, 
    				String profileTagLine,
    				String profileBanner){

    	this.phone = phone;
    	this.profilePhoto = profilePhoto;
    	this.visibility = visibility;
    	this.invitation = invitation;
    	this.authyId = authyId;
    	this.notifications = notifications;
    	this.profileTagLine = profileTagLine;
    	this.profileBanner = profileBanner;
   }
    
    public Customer(String userId,  
    		String phone, 
    		String profilePhoto, 
    		String visibility, 
    		String invitation, 
    		Integer authyId, 
    		Integer notifications, 
    		String profileTagLine,
    		String profileBanner){
    	this.userId = userId;
    	this.phone = phone;
    	this.profilePhoto = profilePhoto;
    	this.visibility = visibility;
    	this.invitation = invitation;
    	this.authyId = authyId;
    	this.notifications = notifications;
    	this.profileTagLine = profileTagLine;
    	this.profileBanner = profileBanner;	

    }
    
    public Customer(CustomerForm form){
    	super(form.getUsername(), form.getPassword());
    	this.phone = form.getPhone();
    	this.authyId  = form.getAuthyId();
    	this.invitation = form.getInvitation();
    	this.profilePhoto = "default.png";
    	this.profileBanner = "weed.jpg";
    	this.profileTagLine = "Enter your tag line here";
    	this.visibility = "private";
    	this.notifications = 0;


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
        if(userId ==null) return 0;
        result = prime * result + Integer.parseInt(userId);
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Customer))
            return false;
        Customer other = (Customer) obj;
        if (userId != other.userId)
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "Customer userId=" + userId + ", phone=" + phone + "]";
    }

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	@Cacheable("userPhotos")
	public Set<UserPhoto> getUserPhotos() {
		return userPhotos;
	}

	public void setUserPhotos(Set<UserPhoto> userPhotos) {
		this.userPhotos = userPhotos;
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

	public Integer getNotifications() {
		return notifications;
	}

	public void setNotifications(Integer notifications) {
		this.notifications = notifications;
	}


	public String getProfileTagLine() {
		return profileTagLine;
	}

	public void setProfileTagLine(String profileTagLine) {
		this.profileTagLine = profileTagLine;
	}

	public String getProfileBanner() {
		return profileBanner;
	}

	public void setProfileBanner(String profileBanner) {
		this.profileBanner = profileBanner;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

//	public Cart getCart() {
//		return cart;
//	}
//
//	public void setCart(Cart cart) {
//		this.cart = cart;
//	}

     
}