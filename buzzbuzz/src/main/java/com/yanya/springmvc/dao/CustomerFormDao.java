 package com.yanya.springmvc.dao;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yanya.springmvc.model.CustomerForm;
 
@Repository
public interface CustomerFormDao {
 
	@Autowired
    CustomerForm findByCustomerFormId(String id);
 
	@Autowired
    void saveCustomerForm(CustomerForm customerForm);
 
 
}