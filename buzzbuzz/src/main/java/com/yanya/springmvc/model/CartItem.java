package com.yanya.springmvc.model;

import org.springframework.stereotype.Component;

//import com.yanya.springmvc.model.Cart;
import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.Product;
//import com.yanya.springmvc.service.CartService;
//import com.yanya.springmvc.service.CartServiceImpl;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import static javax.persistence.GenerationType.IDENTITY;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name="CartItem")
public class CartItem implements Serializable{ 

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "cartItemId", unique = true, nullable = false)
	private String cartItemId;
	
	@NotNull
	@Column(name = "productId", nullable = false)
	private String productId;
	
	@Column(name = "quantity",  nullable = false)
	private int quantity;
	
	@Column(name = "units",  nullable = false)
	private String units;
	
	@Column(name = "price",  nullable = false)
	private Double price;
	
	@Column(name = "productName",  nullable = false)
	private String productName;

	@Column(name = "storeName",  nullable = false)
	private String storeName;
	
	@Column(name ="imagePath")
	private String imagePath;
	
	@Column(name = "merchantId",  nullable = false)
	private String merchantId;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "userId", nullable = false)
	private Customer customer;
	
	public CartItem(){ }
	
	public CartItem(Customer customer, String merchantId, String productId, int quantity, String units, Double price, String productName, String storeName, String imagePath){
		this.customer = customer;
		this.merchantId = merchantId;
		this.productId = productId;
		this.quantity = quantity;
		this.units = units;
		this.price = price;
		this.productName = productName;
		this.storeName = storeName;
		this.imagePath = imagePath;	
	}
	
	public CartItem(String cartItemId, Customer customer, String merchantId, String productId, int quantity, String units, Double price, String productName, String storeName, String imagePath){
		this.cartItemId = cartItemId;
		this.customer = customer;
		this.merchantId = merchantId;
		this.productId = productId;
		this.quantity = quantity;
		this.units = units;
		this.price = price;
		this.productName = productName;
		this.storeName = storeName;
		this.imagePath = imagePath;		
	}

	public String getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}


	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

    public void formatProductNameSpaces(){
    	this.productName = productName.replace("%20", " ");
    	 
    	
    }

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return "CartItem [cartItemId=" + cartItemId + ",productId=" + productId + ", quantity="
				+ quantity + ", units=" + units + ", price=" + price + ", productName=" + productName + ", storeName=" + storeName + ", imagePath=" + imagePath + "]";
	}

//	public String getUserId() {
//		return userId;
//	}
//
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}





}
