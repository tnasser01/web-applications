package com.yanya.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.FilterForm;
import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.ZipCode;

import java.lang.Integer;
 
public interface ProductService {
	
	Product saveNewProduct(Product product);
	Product updateProduct(Product product);
    List<Product> searchByZip(String zip);
    List<Product> findAllProducts(); 
    List<Product> filterWithZipSearch(SearchFilterForm filters);
    List<Product> findProductsVisibleToCustomer(String userId);
    List<Product> findProductsVisibleToMerchant(String userId);
    Product findProductByProductName(String productName);
    Product findProductByProductId(String productId);
    List<Product> searchByKeywordOrZip(String keyword, String searchTermType);
    List<String> calculateZipList(String zip, int miles);
    List<Product> searchByZips(List<String> zips);
    ZipCode findLatAndLng(String zip);
    List<Product> filterSearchResults(List<Product>  searchResults, FilterForm filterForm);
    List<Product> findProductImagePath(String userId, String imagePath, Integer photoNumber);
    void deleteProduct(Product product);
    List<Product> findPrivateProductsByMerchantIds(List<String> userIds);
}