package com.yanya.springmvc.model;

import java.io.File;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.yanya.springmvc.model.Product;

public class ProductForm {
	
	//Save the uploaded product photo to this folder
    private static String PRODUCT_PHOTO_SAVE_PATH1 = "/media/Rihup/Merchant/ProductPhotos1/";
    private static String PRODUCT_PHOTO_SAVE_PATH2 = "/media/Rihup/Merchant/ProductPhotos2/";
    private static String PRODUCT_PHOTO_SAVE_PATH3 = "/media/Rihup/Merchant/ProductPhotos3/";
    private static String PRODUCT_PHOTO_SAVE_PATH4 = "/media/Rihup/Merchant/ProductPhotos4/";
    private static String PRODUCT_PHOTO_SAVE_PATH5 = "/media/Rihup/Merchant/ProductPhotos5/";
  private String userId;

  private String productId;
  
  private String productName;

  private String productDescription;
  
  private String productType;
  
  private String flowerStrain;

  private Double price1;
  
  private Double price2;

  private Double price3;
  
  private Double price4;
  
  private Double price5;
  
  private String quantity1;
  
  private String quantity2;

  private String quantity3;
  
  private String quantity4;
  
  private String quantity5;
  
  private int rating;
  
  private int likes;

  private MultipartFile productPhoto1;
  
  private MultipartFile productPhoto2;
  
  private MultipartFile productPhoto3;
  
  private MultipartFile productPhoto4;
  
  private MultipartFile productPhoto5;

  public ProductForm(){}

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

public int getRating() {
	return rating;
}

public void setRating(int rating) {
	this.rating = rating;
}

public int getLikes() {
	return likes;
}

public void setLikes(int likes) {
	this.likes = likes;
}

public Product toProduct(Merchant merchant) {
	System.out.println("Inside product form: " + merchant.getUserId() + " " + " " + productName );

	 
	String imagePath1 = PRODUCT_PHOTO_SAVE_PATH1 + productPhoto1.getOriginalFilename();
	String imagePath2 = PRODUCT_PHOTO_SAVE_PATH2 + productPhoto2.getOriginalFilename();
	String imagePath3 = PRODUCT_PHOTO_SAVE_PATH3 + productPhoto3.getOriginalFilename();
	String imagePath4 = PRODUCT_PHOTO_SAVE_PATH4 + productPhoto4.getOriginalFilename();
	String imagePath5 = PRODUCT_PHOTO_SAVE_PATH5 + productPhoto5.getOriginalFilename();
	System.out.println("Image path generated is: " + imagePath1 );
	System.out.println("Image path generated is: " + imagePath2 );
	System.out.println("Image path generated is: " + imagePath3 );
	System.out.println("Image path generated is: " + imagePath4 );
	System.out.println("Image path generated is: " + imagePath5 );
	System.out.println("User is " + System.getProperty("user.name"));
    return new Product(merchant, productName, productDescription, productType, flowerStrain, 
    					price1, price2, price3, price4, price5, 
    					productPhoto1.getOriginalFilename(), productPhoto2.getOriginalFilename(), productPhoto3.getOriginalFilename(), productPhoto4.getOriginalFilename(), productPhoto5.getOriginalFilename(),
    					quantity1, quantity2, quantity3, quantity4, quantity5 );
  }

public Product toProduct(Merchant merchant, String productId) {
	System.out.println("Inside product form: " + merchant.getUserId() + " " + " " + productName );

	 
	String imagePath1 = PRODUCT_PHOTO_SAVE_PATH1 + productPhoto1.getOriginalFilename();
	String imagePath2 = PRODUCT_PHOTO_SAVE_PATH2 + productPhoto2.getOriginalFilename();
	String imagePath3 = PRODUCT_PHOTO_SAVE_PATH3 + productPhoto3.getOriginalFilename();
	String imagePath4 = PRODUCT_PHOTO_SAVE_PATH4 + productPhoto4.getOriginalFilename();
	String imagePath5 = PRODUCT_PHOTO_SAVE_PATH5 + productPhoto5.getOriginalFilename();
	System.out.println("Image path generated is: " + imagePath1 );
	System.out.println("Image path generated is: " + imagePath2 );
	System.out.println("Image path generated is: " + imagePath3 );
	System.out.println("Image path generated is: " + imagePath4 );
	System.out.println("Image path generated is: " + imagePath5 );
	System.out.println("User is " + System.getProperty("user.name"));
    return new Product(productId,merchant, productName, productDescription, productType, flowerStrain, 
    					price1, price2, price3, price4, price5, 
    					productPhoto1.getOriginalFilename(), productPhoto2.getOriginalFilename(), productPhoto3.getOriginalFilename(), productPhoto4.getOriginalFilename(), productPhoto5.getOriginalFilename(),
    					quantity1, quantity2, quantity3, quantity4, quantity5 );
  }

public Double getPrice5() {
	return price5;
}

public void setPrice5(Double price5) {
	this.price5 = price5;
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


public MultipartFile getProductPhoto1() {
	return productPhoto1;
}

public void setProductPhoto1(MultipartFile productPhoto1) {
	this.productPhoto1 = productPhoto1;
}

public MultipartFile getProductPhoto2() {
	return productPhoto2;
}

public void setProductPhoto2(MultipartFile productPhoto2) {
	this.productPhoto2 = productPhoto2;
}

public MultipartFile getProductPhoto3() {
	return productPhoto3;
}

public void setProductPhoto3(MultipartFile productPhoto3) {
	this.productPhoto3 = productPhoto3;
}

public MultipartFile getProductPhoto4() {
	return productPhoto4;
}

public void setProductPhoto4(MultipartFile productPhoto4) {
	this.productPhoto4 = productPhoto4;
}

public MultipartFile getProductPhoto5() {
	return productPhoto5;
}

public void setProductPhoto5(MultipartFile productPhoto5) {
	this.productPhoto5 = productPhoto5;
}

public String getProductId() {
	return productId;
}

public void setProductId(String productId) {
	this.productId = productId;
}

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}
  
}
