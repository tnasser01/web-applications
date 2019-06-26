package com.yanya.springmvc.service;
 
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.CustomerForm;
import com.yanya.springmvc.model.User;
 
public interface CustomerFormService {
	
    void saveCustomerForm(CustomerForm customerForm);
    CustomerForm findByCustomerFormId(String customerFormId);
//    void deletePreviousCustomerForms(String phone);

    
}