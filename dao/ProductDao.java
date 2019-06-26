package com.yanya.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.ZipCode;
 
public interface ProductDao {
 
	@Autowired
	Product saveNewProduct(Product product);
	
	@Autowired
    List<Product> searchByZip(String zip);
	
	@Autowired
	List<Product> searchByKeyword(String keyword);
	
	@Autowired
    List<Product> findAllProducts();
    
	@Autowired
    List<Product> filterSearchResults(SearchFilterForm filter,List<String> zipCodeRange);

	@Autowired
	List<Product> findPublicProducts();
	
	@Autowired
    List<Product> findPrivateProductsByMerchantIds(List<String> userIds);
	
	@Autowired
    List<Product> findAllVisibleProducts(List<String> connectedMerchantIds);
		
	@Autowired
    Product findProductByProductName(String productName);
    
	@Autowired
    Product findProductByProductId(String productId);
    
	@Autowired
    List<Product> searchByZips(List<String> zips);
    
	@Autowired
    Product updateProduct(Product product);
   
	@Autowired
	List<Product> findProductImagePath(String userId, String imagePath, Integer photoNumber);
    	
	@Autowired
    public void deleteProduct(Product product);
}