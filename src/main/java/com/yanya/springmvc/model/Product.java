package com.yanya.springmvc.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import com.yanya.springmvc.model.ProductImpression;

@Entity
@Table(name = "Product")
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "productId", unique = true, nullable = false)
	private String productId;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "userId", nullable = false)
	private Merchant merchant;

	@NotNull
	@Size(min = 3, max = 45)
	@Column(name = "productName", nullable = false)
	private String productName;

	@NotNull
//	@Size(min = 3, max = 45)
	@Column(name = "productDescription")
	private String productDescription;

	@Column(name = "productType", nullable = false)
	private String productType;

	@Column(name = "flowerStrain", nullable = false)
	private String flowerStrain;

	@NotNull
	@Column(name = "price1")
	private Double price1;

	@Column(name = "price2")
	private Double price2;

	@Column(name = "price3")
	private Double price3;

	@Column(name = "price4")
	private Double price4;
	
	@Column(name = "price5")
	private Double price5;

	@NotNull
	@Column(name = "imagePath1")
	private String imagePath1;

	@Column(name = "imagePath2")
	private String imagePath2;

	@Column(name = "imagePath3")
	private String imagePath3;

	@Column(name = "imagePath4")
	private String imagePath4;

	@Column(name = "imagePath5")
	private String imagePath5;
	
	@Column(name = "quantity1")
	private String quantity1;
	
	@Column(name = "quantity2")
	private String quantity2;
	
	@Column(name = "quantity3")
	private String quantity3;
	
	@Column(name = "quantity4")
	private String quantity4;
	
	@Column(name = "quantity5")
	private String quantity5;                                                                                                                                                                                                                                                                                     
	
	@Column(name = "likes")
	private int likes;
	
	@Column(name = "rating")
	private int rating;
	
	@Column(name = "average")
	private double average;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
	private Set<ProductImpression> productImpressions;
	
	public Product() {}

	public Product(Merchant merchant, String productName, String productDescription, String productType, String flowerStrain, 
			       Double price1, Double price2, Double price3, Double price4, Double price5, 
			       String imagePath1, String imagePath2, String imagePath3, String imagePath4, String imagePath5,
			       String quantity1, String quantity2, String quantity3, String quantity4, String quantity5) {
		   this(null, merchant, productName, productDescription, productType, flowerStrain, 
				price1, price2, price3, price4, price5,
				imagePath1, imagePath2, imagePath3, imagePath4, imagePath5,
				quantity1, quantity2, quantity3, quantity4, quantity5);
	}
	
	

	public Product(String productId, Merchant merchant, String productName, String productDescription, String productType, String flowerStrain, 
		       Double price1, Double price2, Double price3, Double price4, Double price5, 
		       String imagePath1, String imagePath2, String imagePath3, String imagePath4, String imagePath5,
		       String quantity1, String quantity2, String quantity3, String quantity4, String quantity5) {
		this.productId = productId;
		this.merchant = merchant;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productType = productType;
		this.flowerStrain = flowerStrain;
		this.price1 = price1;
		this.price2 = price2;
		this.price3 = price3;
		this.price4 = price4;
		this.price5 = price5;
		this.imagePath1 = imagePath1;
		this.imagePath2 = imagePath2;
		this.imagePath3 = imagePath3;
		this.imagePath4 = imagePath4;
		this.imagePath5 = imagePath5;
		this.quantity1 = quantity1;
		this.quantity2 = quantity2;
		this.quantity3 = quantity3;
		this.quantity4 = quantity4;
		this.quantity5 = quantity5;
		this.rating = 0;
		this.likes = 0;
		this.average = 0;
	}

	public Merchant getMerchant() {
		return this.merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getFlowerStrain() {
		return flowerStrain;
	}

	public void setFlowerStrain(String flowerStrain) {
		this.flowerStrain = flowerStrain;
	}

	public Double getPrice1() {
		return price1;
	}

	public void setPrice1(Double price1) {
		this.price1 = price1;
	}

	public Double getPrice2() {
		return price2;
	}

	public void setPrice2(Double price2) {
		this.price2 = price2;
	}

	public Double getPrice3() {
		return price3;
	}

	public void setPrice3(Double price3) {
		this.price3 = price3;
	}

	public Double getPrice4() {
		return price4;
	}

	public void setPrice4(Double price4) {
		this.price4 = price4;
	}

	@Override
	public int hashCode() {
		if (productId == null)
			return 0;
		final int prime = 31;
		int result = 1;
		result = prime * result + Integer.parseInt(productId);

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Product))
			return false;
		Product other = (Product) obj;
		if (productId != other.productId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductId=" + productId + ", productName=" + productName + "]";
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes() {
		int likeCnt = 0;
		for(ProductImpression i: productImpressions){
			if(i.getProductImpressionType().equals("like")){
				System.out.println("######" + i.getProductImpressionId());
				likeCnt++;
			}	
		}
		this.likes = likeCnt;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}
	
	public Double calculateAverage(){
		return ((this.rating*.5) + (this.likes*.5));
	}

	public Set<ProductImpression> getProductImpressions() {
		return productImpressions;
	}

	public void setProductImpressions(Set<ProductImpression> productImpressions) {
		this.productImpressions = productImpressions;
	}

	public Double getPrice5() {
		return price5;
	}

	public void setPrice5(Double price5) {
		this.price5 = price5;
	}

	public String getImagePath1() {
		return imagePath1;
	}

	public void setImagePath1(String imagePath1) {
		this.imagePath1 = imagePath1;
	}

	public String getImagePath2() {
		return imagePath2;
	}

	public void setImagePath2(String imagePath2) {
		this.imagePath2 = imagePath2;
	}

	public String getImagePath3() {
		return imagePath3;
	}

	public void setImagePath3(String imagePath3) {
		this.imagePath3 = imagePath3;
	}

	public String getImagePath4() {
		return imagePath4;
	}

	public void setImagePath4(String imagePath4) {
		this.imagePath4 = imagePath4;
	}

	public String getImagePath5() {
		return imagePath5;
	}

	public void setImagePath5(String imagePath5) {
		this.imagePath5 = imagePath5;
	}

	public String getQuantity1() {
		return quantity1;
	}

	public void setQuantity1(String quantity1) {
		this.quantity1 = quantity1;
	}

	public String getQuantity2() {
		return quantity2;
	}

	public void setQuantity2(String quantity2) {
		this.quantity2 = quantity2;
	}

	public String getQuantity3() {
		return quantity3;
	}

	public void setQuantity3(String quantity3) {
		this.quantity3 = quantity3;
	}

	public String getQuantity4() {
		return quantity4;
	}

	public void setQuantity4(String quantity4) {
		this.quantity4 = quantity4;
	}

	public String getQuantity5() {
		return quantity5;
	}

	public void setQuantity5(String quantity5) {
		this.quantity5 = quantity5;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}
	
	

}