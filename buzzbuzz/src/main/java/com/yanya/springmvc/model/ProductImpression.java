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
import javax.persistence.IdClass;
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

import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.Product;


@Entity
@Table(name = "ProductImpression")
public class ProductImpression implements Serializable{

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "productImpressionId", unique = true, nullable = false)
	private String productImpressionId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "productId", nullable = false)	
	private Product product;
	
	@Column(name = "userId", unique = true, nullable = false)
	private String userId;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "productImpressionType")
	private String productImpressionType;
	
	@Column(name = "userType")
	private  String userType;
	
	public ProductImpression() {}

	public ProductImpression(Product product, String userId, String username, String productImpressionType, String userType) {
		this.product = product;
		this.userId = userId;
		this.username = username;
		this.productImpressionType = productImpressionType;
		this.userType = userType;
	}
	
	
	public ProductImpression(String productImpressionId, Product product, String userId, String username, String productImpressionType, String userType) {
		this.productImpressionId = productImpressionId;
		this.product = product;
		this.userId = userId;
		this.username = username;
		this.productImpressionType = productImpressionType;
		this.userType = userType;
	}
	

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductImpressionType() {
		return productImpressionType;
	}

	public void setProductImpressionType(String productImpressionType) {
		this.productImpressionType = productImpressionType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int hashCode() {
		Integer result = 0;
		if((product == null) || userId == null){
			return result;
		}
		
		result = 23 * Integer.parseInt(userId) + 31 * Integer.parseInt(product.getProductId());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProductImpression))
			return false;
		ProductImpression other = (ProductImpression) obj;
		if ((productImpressionId != other.productImpressionId)){
			return false;
		}
		return true;
	}

	public String getProductImpressionId() {
		return productImpressionId;
	}

	public void setProductImpressionId(String productImpressionId) {
		this.productImpressionId = productImpressionId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}



}