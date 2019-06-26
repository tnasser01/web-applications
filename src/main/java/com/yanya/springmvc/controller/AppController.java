package com.yanya.springmvc.controller;

 
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
 
import javax.validation.Valid;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;


import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.service.CustomerService;

import com.yanya.springmvc.service.MerchantService;
import com.yanya.springmvc.service.ProductService;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.Product;

@Controller
@RequestMapping("/")
public class AppController {
    
    @Autowired
    MerchantService merchantService;
    @Autowired
    ProductService productService;
    @Autowired
    CustomerService customerService;    
    @Autowired
    MessageSource messageSource;
    
//    /*
//     * This method will return the main.jsp page at launch
//     */
//    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
//    public String getMain(ModelMap model) {
// 
//        return "main";
//    }
     

    /*
     * This method will return the shop main page
     */
    @RequestMapping(value = {"/shop" }, method = RequestMethod.GET)
    public String shop(ModelMap model) {
        return "shop";
    }
     
    
    /*
     * This method will search for products by keyword or zipcode
     */
//    @RequestMapping(value = {"/search" }, method = RequestMethod.GET)
//    public String chooseKeywordOrZipCodeSearch(@ModelAttribute("search") String searchTerm, ModelMap model) {
//
//        System.out.println("Search Term is: " + searchTerm);
//        Boolean isDigit = searchTerm.substring(0,1).matches("\\d.*");
//        System.out.println("isDigit: " + isDigit);
//        if(isDigit){
//        		List<Product> products = productService.searchByZip(searchTerm);
//        		SearchFilterForm form = new SearchFilterForm();
//        		model.addAttribute("products", products);
//        		model.addAttribute("searchTerm", searchTerm);
//        		model.addAttribute("filterForm", form);
//        }else
//        		//Search by keyword
//        ;
//        
//        return "searchResults";
//    }
    
    /*
     * This method will filter the product search results
     */
    @RequestMapping(value = {"/filter" }, method = RequestMethod.POST)
    public String filter(@ModelAttribute("filterForm") SearchFilterForm searchForm, ModelMap model) {
    	List<Product> products = new ArrayList<Product>();
        System.out.println("Filter Search Term is: " + searchForm.getSearchTerm());
        Boolean isDigit = searchForm.getSearchTerm().substring(0,1).matches("\\d.*");
        System.out.println("isDigit: " + isDigit);
        if(isDigit){
        		products = productService.filterWithZipSearch(searchForm);
        	   if(products.isEmpty()){
        		   	System.out.println("Product filtering returned no results.  Try expanding search");
        	   }else{
        		
        	   }
        }else{
        	//do filter with keyword search
        }
    	
    	
        model.addAttribute("products", products);
        return "searchResults";
    }

   
 
}