package com.yanya.springmvc.service;
 
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanya.springmvc.dao.ConnectionDao;
import com.yanya.springmvc.dao.CustomerDao;
import com.yanya.springmvc.dao.MerchantDao;
import com.yanya.springmvc.dao.ProductDao;
import com.yanya.springmvc.dao.ZipDao;
import com.yanya.springmvc.model.Connection;
import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.ProductImpression;
import com.yanya.springmvc.model.ZipCode;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.service.ProductService;

import com.yanya.springmvc.model.FilterForm;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Integer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {
 
    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private ZipDao zipDao;
    
    @Autowired
    private CustomerDao customerDao;
    
    @Autowired
    private MerchantDao merchantDao;
    
    @Autowired
    private ConnectionDao connectionDao;
    
    public Product saveNewProduct(Product product) {
    	return productDao.saveNewProduct(product);
    }
    
    public Product updateProduct(Product product) {
    	return productDao.updateProduct(product);
    }
    
    public Product findProductByProductName(String productName) {
    	return  productDao.findProductByProductName(productName);   	
    }
     
    public Product findProductByProductId(String productId) {
    	return  productDao.findProductByProductId(productId);   	
    }
     
    public List<Product> searchByZip(String zip) {
        return productDao.searchByZip(zip);
    }
    
    public List<Product> searchByZips(List<String> zips) {
        return productDao.searchByZips(zips);
    }
    
    public List<Product> findAllProducts() {
        return productDao.findAllProducts();
    }
    
    public List<Product> findProductImagePath(String userId, String imagePath, Integer photoNumber){
    	return productDao.findProductImagePath(userId, imagePath, photoNumber);
    }
    public void deleteProduct(Product product) {
    	productDao.deleteProduct(product);
    }
    
    @Cacheable("products")
    public List<Product> findProductsVisibleToCustomer(String userId){   	
    	//add visible products   	
    	List<Connection> visibleMerchantConnections = connectionDao.findUserConnectionsByType(userId, "merchant");
    	List<String> visibleMerchantIds = new ArrayList<String>();
    	
    	if(visibleMerchantConnections!=null){
	    	for(Connection c: visibleMerchantConnections){
	    		if(c.getRequesteeType().equals("merchant")){
	    			visibleMerchantIds.add(c.getRequesteeId());
	    		}else{
	    			visibleMerchantIds.add(c.getRequesterId());
	    		}
	    	}
	       	
	    	List<Product> visibleProducts = productDao.findAllVisibleProducts(visibleMerchantIds);
	    	List<ProductImpression> productImpressions = null;
	    	List<String> productIds = null;
	    	
	    	if(visibleProducts!=null){
	    		for(Product p : visibleProducts){
	    			p.setLikes();
	    	}
  		
	    	return visibleProducts;
	    	}
    	}
    	return null;
    }
    
    @Cacheable("products")
    public List<Product> findProductsVisibleToMerchant(String userId){   	
    	//add visible products   	
    	List<Connection> visibleMerchantConnections = connectionDao.findUserConnectionsByType(userId, "merchant");
    	List<String> visibleMerchantIds = new ArrayList<String>();
    	
    	if(visibleMerchantConnections!=null){
	    	for(Connection c: visibleMerchantConnections){
	    		if(!c.getRequesteeId().equals(userId)){
	    			visibleMerchantIds.add(c.getRequesteeId());
	    		}else{
	    			visibleMerchantIds.add(c.getRequesterId());
	    		}
	    	}
	       	
	    	visibleMerchantIds.add(userId);
	    	List<Product> visibleProducts = productDao.findAllVisibleProducts(visibleMerchantIds);
	    	List<ProductImpression> productImpressions = null;
	    	List<String> productIds = null;
	    	
	    	if(visibleProducts!=null){
	    		for(Product p : visibleProducts){
	    			p.setLikes();
	    	}
  		
	    	return visibleProducts;
	    	}
    	}
    	return null;
    }
    
    public List<Product> findPrivateProductsByMerchantIds(List<String> userIds){
    	return productDao.findPrivateProductsByMerchantIds(userIds);
    }
    
    public List<Product> searchByKeywordOrZip(String keyword, String searchTermType){
    	List<Product> matchedProducts = new ArrayList<Product>();
    	
    	if(searchTermType.equals("zip")){
    		List<String> matchedZips = calculateZipList(keyword, 50);
    		matchedProducts = searchByZips(matchedZips);
    		
    	}else{
    		matchedProducts = productDao.searchByKeyword(keyword);
    	}
    	
    	List<Product> offlineProducts = new ArrayList<Product>();
    	System.out.println("$$$$$ Total # of keyword matched products returned: " + matchedProducts.size());

    		for(Product p: matchedProducts){
    			System.out.println("^^^^^^^ Product's status is " + p.getMerchant().getStatus());
    			if(p.getMerchant().getStatus().equals("offline")){
    				System.out.println("removing " + p.getProductName() + "because it is offline");
    				offlineProducts.add(p);
    			}
    		}
    	
    	matchedProducts.removeAll(offlineProducts);
    	List<Product> nonVisibleProducts = new ArrayList<Product>();
		UserDetails userDetails = null;
		try{
			userDetails =(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch(ClassCastException ce){ 
	
		}
	
	    Customer searcher = customerDao.findCustomerByUsername(userDetails.getUsername());
	    if(searcher!=null){
	    		System.out.println("searchers user id is " + searcher.getUserId());
	    		
	    		for(Product p: matchedProducts){
	    		if(p.getMerchant().getVisibility().equals("private")){
					System.out.println("@@@@@@@@" + p.getProductName() + "status is private ...." + p.getMerchant().getStatus() );
	    			Connection c = connectionDao.findConnectionByUsers(searcher.getUserId(), p.getMerchant().getUserId(), "accepted");
	    			if(c==null){
	    				System.out.println("@@@@@@@@ This user has no connection to  + " + p.getMerchant().getUsername() + "  ...." + p.getMerchant().getStatus() );
	    				nonVisibleProducts.add(p);
	    				
	    			}
	    		}else{
	    			System.out.println("!!!!!!! " +  p.getProductName() + " visibility is " +  p.getMerchant().getVisibility());
	    		}
	    	}
	    	
	   }else{
	    	Merchant merchantSearcher = merchantDao.findByUsername(userDetails.getUsername());
	    	System.out.println("merchantSearchers user id is " + merchantSearcher.getUserId());
	    	for(Product p: matchedProducts){
	    		if(p.getMerchant().getVisibility().equals("private")){
					System.out.println("@@@@@@@@" + p.getProductName() + "status is private ...." + p.getMerchant().getStatus() );
	    			Connection c = connectionDao.findConnectionByUsers(merchantSearcher.getUserId(), p.getMerchant().getUserId(), "accepted");
	    			if(c==null){
	    				System.out.println("@@@@@@@@ This user has no connection to  + " + p.getMerchant().getUsername() + "  ...." + p.getMerchant().getStatus() );
	    				nonVisibleProducts.add(p);
	    				
	    			}
	    		}else{
	    			System.out.println("!!!!!!! " +  p.getProductName() + " visibility is " +  p.getMerchant().getVisibility());
	    		}
	    	}
	    }
    	
    	matchedProducts.removeAll(nonVisibleProducts);
    	
    	return matchedProducts;
    }
    
    public List<Product> filterSearchResults(List<Product>  searchResults, FilterForm filterForm){
		//products to be hidden in grid and list view
		List<Product> productsToFilterOut = new ArrayList<Product>();
		List<Product> matchingProducts = new ArrayList<Product>();
		//RATINGS CHECK
		//If all ratings are unchecked (false value) in form, add all the products to the filtered list 
		if(!filterForm.getRatings().containsValue("true")){			
			//productsToFilterOut.addAll(searchResults);
			System.out.println("no rating selected.  no items added");
			matchingProducts.addAll(searchResults);
		//else if the corresponding rating is selected, add the product to the list
		}else{
			for(Product searchResult : searchResults){
				boolean ratingMatch = false;
				System.out.println(searchResult.getRating() + " is " + searchResult.getProductName() + "**********" );
				switch(searchResult.getRating()){
					case 1: 
						if(Boolean.parseBoolean(filterForm.getRatings().get("oneStar"))){
							System.out.println("search result matched oneStar");
							ratingMatch = true;
						}
						break;	
					case 2:  
							if(Boolean.parseBoolean(filterForm.getRatings().get("twoStars"))){
								System.out.println("search result matched twoStars");
								ratingMatch = true;
							}
							break;		
					case 3: 
							if(Boolean.parseBoolean(filterForm.getRatings().get("threeStars"))){
								System.out.println("search result matched threeStars");
								ratingMatch = true;
							}
							break;			
					case 4: 
							if(Boolean.parseBoolean(filterForm.getRatings().get("fourStars"))){
								System.out.println("search result matched fourStars");
								ratingMatch = true;
							}
							break;					
					case 5: 
							System.out.println("search result matched fiveStars");
							if(Boolean.parseBoolean(filterForm.getRatings().get("fiveStars"))){	
								ratingMatch = true;
							}
							break;	
					default: 
						System.out.println("no case was matched");
						break;
	
				}
				System.out.println(searchResult.getProductName() + " rating match is " + ratingMatch);
				if(!ratingMatch){
					productsToFilterOut.add(searchResult);
					System.out.println(searchResult.getProductName() + " added to filter out list");
				}else if(ratingMatch){
					matchingProducts.add(searchResult);
					System.out.println(searchResult.getProductName() + " added to matching list");
					
				}
			
			}
		}
		
		//PRICE CHECK
		List<Product> priceMismatches = new ArrayList<Product>();
		for(Product searchResult: matchingProducts){
			if( !(( (searchResult.getPrice1()==null)|| (searchResult.getPrice1() > Double.parseDouble(filterForm.getLowPrice())) && (searchResult.getPrice1() < Double.parseDouble(filterForm.getHighPrice()))) || 
				((searchResult.getPrice2()==null)|| (searchResult.getPrice2() > Double.parseDouble(filterForm.getLowPrice())) && (searchResult.getPrice2() < Double.parseDouble(filterForm.getHighPrice()))) ||
				((searchResult.getPrice3()==null)|| (searchResult.getPrice3() > Double.parseDouble(filterForm.getLowPrice())) && (searchResult.getPrice3() < Double.parseDouble(filterForm.getHighPrice()))) ||
				((searchResult.getPrice4()==null)|| (searchResult.getPrice4() > Double.parseDouble(filterForm.getLowPrice())) && (searchResult.getPrice4() < Double.parseDouble(filterForm.getHighPrice()))) ||	
				((searchResult.getPrice5()==null)|| (searchResult.getPrice5() > Double.parseDouble(filterForm.getLowPrice())) && (searchResult.getPrice5() < Double.parseDouble(filterForm.getHighPrice()))) 
					)){ 
					System.out.println("For " + searchResult.getProductName() + " price1,2,3,4, 5 did not fall within the hi low range");
				priceMismatches.add(searchResult);
			}else{
				System.out.println("price check ok for " + searchResult.getProductName());
				
			}
				
		}
		matchingProducts.removeAll(priceMismatches);
		productsToFilterOut.addAll(priceMismatches);
		
		System.out.println("%%%%%% Matching products size is: " + matchingProducts.size());
		//DISTANCE CHECK
    	//Ensure all keyword matched search results are within 50 mile radius             		
    	List<Product> outOfRange = new  ArrayList<Product>();
    	ZipCode zip = null;
    	Boolean goodZip = false;
		String foundSearchZip =filterForm.getZip();
		Integer zipInt = Integer.valueOf(foundSearchZip);
		while(!goodZip){
			
			zip = findLatAndLng(String.valueOf(zipInt));
			
			if(zip!=null){
				goodZip = true;
			}else{
				zipInt++;
				foundSearchZip =  String.valueOf(zipInt); 
			}
		}
    	
    	
    	for(Product p : matchingProducts){
     		Double d = FilterForm.distFrom(Double.parseDouble(p.getMerchant().getLat()), Double.parseDouble(p.getMerchant().getLng()), zip.getLat(), zip.getLng());

    		if( d > 50.00){
    				System.out.println("For search, " + p.getProductName() + "is " + d + " miles away from city center.  Removing from list to dislay");
    				outOfRange.add(p);
    			}else{
    				System.out.println("For search, " + p.getProductName() + "is " + d + " miles away from city center");
    				
    			}
    		
    		searchResults.removeAll(outOfRange);
		}
			productsToFilterOut.addAll(outOfRange);
		
		//NAME CHECK
		if(!filterForm.getNames().containsValue("false")){
			System.out.println("all names are selected");
		}else{
			List<Product> nameMisMatches = new ArrayList<Product>();
			for(Product p: matchingProducts){
				String s =  "'" + p.getProductName() + "'";
				System.out.println("in names hashmap, " + p.getProductName() + " " + s);
				if(!Boolean.parseBoolean(filterForm.getNames().get(s))){
					System.out.println(p.getProductName() + " was not selected.  Removing from filtered products");
					nameMisMatches.add(p);
				}
			}	
		matchingProducts.removeAll(nameMisMatches);
		productsToFilterOut.addAll(nameMisMatches);
		}
		
		//STRAIN CHECK
		if(!filterForm.getStrains().containsValue("false")){
			System.out.println("all strains are selected");
		}else{
			List<Product> strainMisMatches = new ArrayList<Product>();
			for(Product p: matchingProducts){
				String s =  "'" + p.getFlowerStrain() + "'";
				System.out.println("in strains hashmap, " + p.getFlowerStrain() + " " + s);
				if(!Boolean.parseBoolean(filterForm.getStrains().get(s))){
					System.out.println(p.getFlowerStrain() + " was not selected.  Removing from filtered products");
					strainMisMatches.add(p);
				}
			}	
			matchingProducts.removeAll(strainMisMatches);
			productsToFilterOut.addAll(strainMisMatches);
		}
		
		//TYPE CHECK
		if(!filterForm.getTypes().containsValue("false")){
			System.out.println("all types are selected");
		}else{
			List<Product> typeMisMatches = new ArrayList<Product>();
			for(Product p: matchingProducts){
				String s =  "'" + p.getProductType() + "'";
				System.out.println("in types hashmap, " + p.getProductType() + " " + s);
				if(!Boolean.parseBoolean(filterForm.getTypes().get(s))){
					System.out.println(p.getProductType() + " was not selected.  Removing from filtered products");
					typeMisMatches.add(p);
				}
			}	
			matchingProducts.removeAll(typeMisMatches);
			productsToFilterOut.addAll(typeMisMatches);
		}

		return productsToFilterOut;
    }
    
    public List<Product> filterWithZipSearch(SearchFilterForm filter){
        
        //Parses searchFilterForm for:
        //1. list of strains to search for,2. list of names to search for,3. zip code range to search for,/4. price range to search for

    	System.out.println("Zip Code search term from form is: " + filter.getSearchTerm());
    	System.out.println("Distance from form is: " + filter.getDistance());
    	List<String> zipCodes = calculateZipList(filter.getSearchTerm(), filter.getDistance());
    	
    	return productDao.filterSearchResults(filter, zipCodes);
    }
    
    public List<String> calculateZipList(String zipOrig, int miles){
    	
    	//1. convert radius to degrees
    	 
    	Double degrees = miles/69.000000;
    	System.out.println("Calculated degrees of distance is: " + degrees);
    	
    	//2. Find the zipOrig latitude and longitude.
    	ZipCode zip = null;
    	boolean found = false;
    	Integer zp = Integer.valueOf(zipOrig);
    	while(!found){
    		zip = zipDao.findLatAndLng(String.valueOf(zp));
    		if(zip==null){
    			zp++;
    		}else{
    			found = true;
    		}
    	}
    	System.out.println("ZipCode from file is: " + zip.getZip() );
    	System.out.println("lat from file is: " + zip.getLat() );
    	System.out.println("long is: " + zip.getLng());
		
	    //3. add/substract the degrees to the orig lat and long to create a square area
	    double latMax = zip.getLat() + degrees;
	    double latMin = zip.getLat() - degrees;
	    double longMax = zip.getLng() + degrees;
	    double longMin = zip.getLng() - degrees;
    	System.out.println("Calculated lat max is: " + latMax);
    	System.out.println("Calculated lat min is: " + latMin);
    	System.out.println("Calculated lng max is: " + longMax);
    	System.out.println("Calculated lng min is: " + longMin);
	   
	    //4. query for zip codes whose degrees are in between the area
	    List<String> zipsInRange = zipDao.findZipsInRange(latMax, latMin, longMax, longMin);
	    System.out.println("number of zips within " + miles + " miles of " + zipOrig + ": " + zipsInRange.size());
    	return zipsInRange;
    	
    		
    }
    
    public ZipCode findLatAndLng(String zip){
    	return zipDao.findLatAndLng(zip);
    }
 
 
}