 package com.yanya.springmvc.dao;
 
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yanya.springmvc.model.Customer;
 
@Repository
public interface CustomerDao {
 
	@Autowired
    Customer findByCustomerId(String id);
 
	@Autowired
    Customer saveCustomer(Customer customer);
     
	@Autowired
    void deleteCustomerByPhone(String phone);
   
	@Autowired
	public List<Customer> findCustomersByCustomerIds(List<String> customerIds);
	
	@Autowired
    List<Customer> findAllCustomers();
 
	@Autowired
	List<Customer> findByVisibility(String visibility);
	
	@Autowired
    public List<Customer> findCustomerByInvitation(String invitation);
	
	@Autowired
    Customer findCustomerByPhone(String phone);

	@Autowired
    Customer findCustomerByUsername(String username);
  
	@Autowired
    Customer updateCustomerVisibility(String userId, String newVisibility);
 
	@Autowired
    Customer updateProfilePhoto(String userId, String profilePhoto);
	
	@Autowired
    Customer updateProfileBanner(String userId, String profileBanner);
	
	@Autowired
    Customer updateProfileTagLine(String userId, String profileTagLine);

//	@Autowired
//  Customer updatePass(String userId, String pass);
//	
	@Autowired
	Customer incrementNotifications(String userId);
	
	@Autowired
	Customer decrementNotifications(String userId);
}