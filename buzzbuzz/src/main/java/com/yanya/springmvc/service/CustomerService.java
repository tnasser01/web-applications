package com.yanya.springmvc.service;
 
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;

import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.User;

import com.yanya.springmvc.model.Connection;
import com.yanya.springmvc.model.CustomerControlMenuForm;
import com.yanya.springmvc.model.ProfileBannerForm;
import com.yanya.springmvc.model.ProfilePhotoForm;
 
public interface CustomerService extends UserDetailsService{
      
    void saveCustomer(Customer customer);     
    void updateCustomer(Customer user);   
    void deleteCustomerByPhone(String phone);
 
    List<Customer> findAllCustomers(); 
    Customer findCustomerByCustomerId(String userId);
    Customer findCustomerByPhone(String phone);
    Customer findCustomerByUsername(String username);
    List<Customer> findCustomersVisibleToCustomer(String userId);
    List<Customer> findCustomersVisibleToMerchant(String userId);
    
    boolean isCustomerPhoneUnique(String phone);
    boolean isUsernameUnique(String username);
    
    Customer updateCustomerVisibility(String userId, String newVisibility);
    Customer updateProfilePhoto(String userId, String profilePhoto);   
    Customer updateProfileBanner(String userId, String profileBanner);   
    Customer updateProfileTagLine(String userId, String profileTagLine);
//    Customer updatePassword(String userId, String password);
    
    void decrementCustomerNotifications(String userId); 
    void incrementCustomerNotifications(String userId);
    
	Customer setCustomerUserDetailsToModel(HttpServletRequest model, Principal principal);
	Customer setCustomerProfileDataToModel(HttpServletRequest model, String username);
	List<Customer> findCustomerByInvitation(String invitation);

	   
   
     
}