package com.yanya.springmvc.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.List;
import java.util.Set;
import java.util.Locale;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Errors;


import javax.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.ProductForm;
import com.yanya.springmvc.model.RoleEnum;
import com.yanya.springmvc.service.ProductService;
import com.yanya.springmvc.controller.CustomerController;
import com.yanya.springmvc.model.CartItem;
import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.FilterForm;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.service.CustomerService;
import com.yanya.springmvc.service.MerchantService;
import com.yanya.springmvc.service.MerchantStatusService;
import javax.servlet.http.HttpServletRequest;
import com.yanya.springmvc.model.ZipCode;
@Controller
@RequestMapping("/")
public class ProductController{
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private MerchantStatusService merchantStatusService;
	
    @Autowired
    MessageSource messageSource;
    
	@ModelAttribute("strainList")
	public List<String> getStrainList(){
		List<String> strainList = new ArrayList<String>();
	    strainList.add("Indica");
		strainList.add("Sativa");
		strainList.add("Hybrid");
		return strainList;
	}
	
	
	@ModelAttribute("searchSuggestion")
	public List<String> getSearchSuggestion(){
		List<String> searchSuggestionList = new ArrayList<String>();
		searchSuggestionList.add("Indica");
		searchSuggestionList.add("Blueberry Kush");
		searchSuggestionList.add("Sativa");
		searchSuggestionList.add("OG Minis");
		searchSuggestionList.add("Hybrid");
		
		int size = searchSuggestionList.size();
		int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
		
		List<String> searchSuggestion = new ArrayList<String>();
		searchSuggestion.add(searchSuggestionList.get(item));
		return searchSuggestion;
	}
	
	@ModelAttribute("productTypeList")
	public List<String> getProductTypeList(){
		List<String> productTypeList = new ArrayList<String>();
	    productTypeList.add("Flower");
		productTypeList.add("Concentrates");
		productTypeList.add("Edibles");
		productTypeList.add("Pipes");
		productTypeList.add("Pens");
		productTypeList.add("Bongs");
		productTypeList.add("Papers");
		productTypeList.add("Cigars");
		productTypeList.add("Grinders");
		return productTypeList;
	}
	
	@ModelAttribute("units")
	public List<String> getUnits(){
		List<String> units = new ArrayList<String>();
		units.add("1/8");
		units.add("1/4");
		units.add("1/2");
		units.add("1 oz");
		return units;
	}
	
	@ModelAttribute("quantities")
	public List<Integer> getQuantities(){
		List<Integer> quantities = new ArrayList<Integer>();
		quantities.add(1);
		quantities.add(2);
		quantities.add(3);
		quantities.add(4);
		quantities.add(5);
		quantities.add(6);
		quantities.add(7);
		quantities.add(8);
		quantities.add(9);
		quantities.add(10);
		return quantities;
	}
    	
	@RequestMapping(value = {"/product/{productId}" }, method=GET)
	public String showProductPage(@PathVariable String productId, HttpServletRequest model, Principal principal){
		customerService.setCustomerUserDetailsToModel(model, principal);
		Product product = productService.findProductByProductId(productId);
		model.setAttribute("product", product);
		
		String pathProductName = product.getProductName().replace(" ", "%20");
		model.setAttribute("viewType", "home");
		model.setAttribute("pathProductName", pathProductName);
		model.setAttribute("cartItem", new CartItem());
		model.setAttribute("userType", "customer");
		//System.out.println("In product controller, product " + product.getProductName() );
		return "productPage";
	}
	
	@RequestMapping(value = {"/sell/product/{productId}" }, method=GET)
	public String showMerchantProductPage(@PathVariable String productId, HttpServletRequest model, Principal principal){
		merchantService.setMerchantUserDetailsToModel(model, principal);
		Product product = productService.findProductByProductId(productId);
		model.setAttribute("product", product);		
		String pathProductName = product.getProductName().replace(" ", "%20");
		model.setAttribute("viewType", "home");
		model.setAttribute("pathProductName", pathProductName);
		model.setAttribute("cartItem", new CartItem());
		model.setAttribute("userType", "merchant");
		//System.out.println("In product controller, product " + product.getProductName() );
		return "productPage";
	}
	
	
	@RequestMapping(value = {"/sell/product/edit/{productId}" }, method=GET)
	public String showEditProductForm(@PathVariable("productId") String productId, HttpServletRequest model, Principal principal){
		merchantService.setMerchantUserDetailsToModel(model, principal);
		Product product = productService.findProductByProductId(productId);
		model.setAttribute("product", product);
		model.setAttribute("productForm", new ProductForm());
		return "editProduct";
	}
	

	
	
		
	
    /*
     * This method will search for products by keyword or zipcode
     */
    @RequestMapping(value = {"/search", "/sell/search" }, method = RequestMethod.GET)
    public String search(@ModelAttribute("search") String searchTerm,HttpServletRequest model) {
	
    	
    	String userType = null;
		Customer customer = null;
		Merchant merchant = null;
		
	   	try{
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
    		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
    		if(authentication != null){
    			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    			for(GrantedAuthority ga: authorities){
    				System.out.println("&&&&&&&&&&&&&" + ga.getAuthority() + "&&&&&&&&&&&");
    				if (ga.getAuthority().equals(RoleEnum.ROLE_MERCHANT.toString())) {
    					merchant  = merchantService.findByUsername(userDetails.getUsername());
    					model.setAttribute("userType", "merchant");
    					model.setAttribute("user", merchant);
    					userType = "merchant";
    				}else if (ga.getAuthority().equals(RoleEnum.ROLE_CUSTOMER.toString())) {
        				customer = customerService.findCustomerByUsername(userDetails.getUsername());
    					model.setAttribute("userType", "customer");
        					model.setAttribute("user", customer);
        					userType = "customer";	
        			}
    			}
    		}
    	}catch(ClassCastException cce){
    		System.out.println("caught classcast exception!");
    		return "main";
		}
		
		
    			List<Product> searchResults = new ArrayList<Product>();
				SearchFilterForm form = new SearchFilterForm();
    	
    			//1. First determine the search term type
    			String searchTermType = determineSearchTermType(searchTerm);
    			model.setAttribute("searchTermType", searchTermType);
    			ZipCode zip =null;
    			Boolean goodZip = false;

    			System.out.println("searchTerm Typeis: " + searchTermType);
    			if(searchTermType.equals("zip")){
    				
					if(Integer.valueOf(searchTerm) < 10001){
					       searchTerm = String.valueOf(10001); 				
					}
        			String foundSearchZip =searchTerm;
        			Integer zipInt = Integer.valueOf(foundSearchZip);
        			
        			
    				while(!goodZip){
	    				zip = productService.findLatAndLng(String.format("%05d", zipInt));
	    				
	    				if(zip!=null){
	    					goodZip = true;
	    				}else{
	    					zipInt++;
	    					foundSearchZip =  String.valueOf(zipInt); 
	    				}
    				}
    				
    			}else{
    				zip  = productService.findLatAndLng("90231");
    			}
        		///////////////////////////////
            	////Search by keyword or zip///
        		///////////////////////////////
        		
    			
    			///////////////////////////////
    			/////PRODUCT RESULTS///////////
    			///////////////////////////////
            	searchResults = productService.searchByKeywordOrZip(searchTerm, searchTermType);
            	
            	//Ensure all keyword matched search results are within 50 mile radius             		
            	List<Product> outOfRange = new  ArrayList<Product>();
            		
            	for(Product p : searchResults){
             		Double d = FilterForm.distFrom(Double.parseDouble(p.getMerchant().getLat()), Double.parseDouble(p.getMerchant().getLng()), zip.getLat(), zip.getLng());

            		if( d > 50.00){
            				System.out.println("For search, " + p.getProductName() + "is " + d + " miles away from city center.  Removing from list to dislay");
            				outOfRange.add(p);
            			}else{
            				System.out.println("For search, " + p.getProductName() + "is " + d + " miles away from city center");
            				
            			}	
    			}
    			
            	searchResults.removeAll(outOfRange);

        		System.out.println(searchResults.size());
        		
        		for(Product p: searchResults){
        				System.out.println(p.getProductName());
        		}
        		
           		Set<String> names = new HashSet<String>();
           		Set<String> types = new HashSet<String>();
           		Set<String> strains = new HashSet<String>();
        		
           		for(Product p: searchResults){
           			names.add(p.getProductName());
           			types.add(p.getProductType());
           			strains.add(p.getFlowerStrain());
           		}
           		model.setAttribute("searchTerm", searchTerm);
           		model.setAttribute("names", names);
           		model.setAttribute("types", types);
           		model.setAttribute("strains", strains);
        		model.getSession().setAttribute("searchResults", searchResults);

        		//Find all public and connected merchants who match search term
        		List<Merchant> matchedMerchants = new ArrayList<Merchant>();
        		List<Customer> matchedCustomers =new ArrayList<Customer>();
        		List<Merchant> mismatchedMerchants = new ArrayList<Merchant>();
        		List<Customer> mismatchedCustomers = new ArrayList<Customer>();
        		
        		if(!searchTermType.equals("zip")){
        			
        			
        			if(userType.equals("customer")){
        				matchedMerchants = merchantService.findOnlineMerchantListVisibleToCustomer(customer.getUserId());
        				matchedCustomers = customerService.findCustomersVisibleToCustomer(customer.getUserId());
        			}else{
        				matchedMerchants = merchantService.findOnlineMerchantListVisibleToMerchant(merchant.getUserId());
        				matchedCustomers = customerService.findCustomersVisibleToMerchant(merchant.getUserId());
        			}
        			
        			System.out.println("size of matched merchants before removeAll: "  + matchedMerchants.size());
        			for(Merchant mm  :  matchedMerchants){
        				mm.getStoreName();
        			}
        			for(Customer cc  :  matchedCustomers){
        				cc.getUsername();
        			}
        			System.out.println("size of matched customers before removeAll : "  + matchedCustomers.size());
        			
        			String lowercaseSearchTerm = searchTerm.toLowerCase();
        			System.out.println("lowerCaseSearchTerm  is: " + lowercaseSearchTerm);
        			for(Merchant merch: matchedMerchants){
        				if( !merch.getStoreName().toLowerCase().contains(lowercaseSearchTerm) && !merch.getUsername().toLowerCase().contains(lowercaseSearchTerm) ){
        					mismatchedMerchants.add(merch);
        					System.out.println("mismatched lowerCase merchant store name  is: " + merch.getStoreName().toLowerCase());
        				
        				}
        			}
        			for(Customer cust: matchedCustomers){
        				if( !cust.getUsername().toLowerCase().contains(lowercaseSearchTerm) ){
        					mismatchedCustomers.add(cust);
        					System.out.println("mismatched lowerCase merchant store name  is: " + cust.getUsername().toLowerCase());
        				}
        			}
        			

        			matchedMerchants.removeAll(mismatchedMerchants);
        			matchedCustomers.removeAll(mismatchedCustomers);
        		}
        		
        		System.out.println("size of mismatched merchants " + mismatchedMerchants.size());
        		System.out.println("size of mismatched customers " + mismatchedCustomers.size());
        		System.out.println("size of matched merchants after remove all: " + matchedMerchants.size());
        		System.out.println("size of matched customers after remove all: " + matchedCustomers.size());
        		model.setAttribute("matchedMerchantsSize", matchedMerchants.size());
        		model.setAttribute("matchedCustomersSize", matchedCustomers.size());
        		model.setAttribute("matchedMerchants", matchedMerchants);
        		model.setAttribute("matchedCustomers", matchedCustomers);
	     		model.setAttribute("searchTerm", searchTerm);
	     		model.setAttribute("filterForm", form);
	     		model.setAttribute("viewType", "search");

	     		return "searchResults";
    }

    private String determineSearchTermType(String searchTerm){
    	String regex = "\\d{5}";

    	if(searchTerm.matches(regex)){
    		return "zip";
    		
    	}else{
    		return "text";
    	}
    }
    
    @RequestMapping(value = {"/sell/searchFilter","/searchFilter" }, method = RequestMethod.GET)
    public String searchFilter(@Valid SearchFilterForm filterForm, ModelMap model) {
    			
    			String searchTermType = determineSearchTermType(filterForm.getSearchTerm());
    			List<Product> searchResults = productService.searchByKeywordOrZip(filterForm.getSearchTerm(), searchTermType);
        		System.out.println(searchResults.size());
        		
        		for(Product p: searchResults){
        				System.out.println(p.getProductName());
        		}
        		
            	String userType = null;
        		Customer customer = null;
        		Merchant merchant = null;
        		
        	   	try{
            		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
            		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
            		if(authentication != null){
            			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            			for(GrantedAuthority ga: authorities){
            				System.out.println("&&&&&&&&&&&&&" + ga.getAuthority() + "&&&&&&&&&&&");
            				if (ga.getAuthority().equals(RoleEnum.ROLE_MERCHANT.toString())) {
            					merchant  = merchantService.findByUsername(userDetails.getUsername());
            					model.addAttribute("userType", "merchant");
            					model.addAttribute("user", merchant);
            					userType = "merchant";
            				}else if (ga.getAuthority().equals(RoleEnum.ROLE_CUSTOMER.toString())) {
                				customer = customerService.findCustomerByUsername(userDetails.getUsername());
            					model.addAttribute("userType", "customer");
                					model.addAttribute("user", customer);
                					userType = "customer";	
                			}
            			}
            		}
            	}catch(ClassCastException cce){
            		System.out.println("caught classcast exception!");
            		return "main";
        		}
        		
        	
        		model.addAttribute("searchResults", searchResults);
	     		model.addAttribute("searchTerm", filterForm.getSearchTerm());
	     		model.addAttribute("filterForm", filterForm);
    
	     		return "searchResults";
    }

}

	
	
