package com.yanya.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.ProductImpression;

import java.lang.Integer;

@Service
public interface ProductImpressionService {
	
	void saveOrUpdateProductImpression(ProductImpression productImpression);
	void saveProductImpression(ProductImpression productImpression, HttpServletRequest request);
	void updateProductImpression(ProductImpression productImpression, HttpServletRequest request);
	ProductImpression findProductImpressionByProductIdAndUserId(String productId, String userId);	
	HashMap<String, String> findProductImpressionsByProductIdsAndUserId(List<Product> products, String userId, String userType);
	
}