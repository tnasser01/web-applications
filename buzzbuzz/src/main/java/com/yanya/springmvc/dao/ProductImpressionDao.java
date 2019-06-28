package com.yanya.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.yanya.springmvc.model.ProductImpression;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.ZipCode;
 
public interface ProductImpressionDao {
 
	@Autowired
	void saveOrUpdateProductImpression(ProductImpression productImpression);
	@Autowired
	void saveProductImpression(ProductImpression productImpression);
	@Autowired
	void updateProductImpression(ProductImpression productImpression);
	@Autowired
	ProductImpression findProductImpressionByProductIdAndUserId(String productId, String userId);
	@Autowired
	List<ProductImpression> findProductImpressionsByProductIdsAndUserId(List<String> productIds, String userId, String userType);

}