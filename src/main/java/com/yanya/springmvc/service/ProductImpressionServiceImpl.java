package com.yanya.springmvc.service;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanya.springmvc.dao.ProductImpressionDao;
import com.yanya.springmvc.event.NotificationEventProducer;
import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.ProductImpression;
import com.yanya.springmvc.service.ProductImpressionService;

@Service("productImpressionService")
@Transactional
public class ProductImpressionServiceImpl implements ProductImpressionService {
 
    @Autowired
    private ProductImpressionDao productImpressionDao;
    
    @Autowired
    private NotificationEventProducer producer;
    
    
    public void saveOrUpdateProductImpression(ProductImpression productImpression) {
    	 productImpressionDao.saveOrUpdateProductImpression(productImpression);
    }
    
    public void saveProductImpression(ProductImpression productImpression, HttpServletRequest request) {
   	 productImpressionDao.saveProductImpression(productImpression);
 	 producer.createProductLikeNotification(productImpression, request, "increment", productImpression.getProduct().getMerchant().getUserId(), "merchant");

    }
    public void updateProductImpression(ProductImpression productImpression, HttpServletRequest request) {
   	 
    	productImpressionDao.updateProductImpression(productImpression);
   	 if(productImpression.getProductImpressionType().equals("like")){
   		producer.createProductLikeNotification(productImpression, request, "increment", productImpression.getProduct().getMerchant().getUserId(), "merchant");	 
   	 }else if(productImpression.getProductImpressionType().equals("unlike")){
   		producer.createProductLikeNotification(productImpression, request, "decrement", productImpression.getProduct().getMerchant().getUserId(), "merchant");	 
   	 }
    }
    
    public ProductImpression findProductImpressionByProductIdAndUserId(String productId, String userId){
    	return productImpressionDao.findProductImpressionByProductIdAndUserId(productId, userId);
    }
    
   @Cacheable("productImpressions")
    public HashMap<String, String> findProductImpressionsByProductIdsAndUserId(List<Product> products, String userId, String userType){
		List<String> productIds = new ArrayList<String>();
    	if(products!=null){
    		for(Product p: products){
    			productIds.add(p.getProductId());
    		}
    	}
		List<ProductImpression> productImpressions = productImpressionDao.findProductImpressionsByProductIdsAndUserId(productIds, userId, userType);
		
		if(productImpressions==null){
			return new HashMap<String, String>();
		}

		HashMap<String, String> prodImpressionsMap = new  HashMap<String,String>();	
	
			for(ProductImpression pi: productImpressions){
				prodImpressionsMap.put(pi.getProduct().getProductId(), pi.getProductImpressionType());
			}
					

		return prodImpressionsMap;
    }

}