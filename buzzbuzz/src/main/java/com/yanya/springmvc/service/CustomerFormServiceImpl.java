package com.yanya.springmvc.service;
 
import java.util.ArrayList;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.yanya.springmvc.dao.CustomerFormDao;
import com.yanya.springmvc.model.CustomerForm;
 
@Service("customerFormService")
@Transactional
public class CustomerFormServiceImpl implements CustomerFormService {
 
    @Autowired
    private CustomerFormDao dao;
     
    public CustomerForm findByCustomerFormId(String customerFormId) {
        return dao.findByCustomerFormId(customerFormId);
    }
 
    public void saveCustomerForm(CustomerForm customerForm) {
        dao.saveCustomerForm(customerForm);
    }
//    
//    public void deletePreviousCustomerForms(String phone){
//    	dao.deletePreviousCustomerForms(phone);
//    }
 

}