package com.yanya.springmvc.dao;
 
import java.util.List;
 
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.yanya.springmvc.model.CustomerForm;
 
@Repository("customerFormDao")
public class CustomerFormDaoImpl extends AbstractDao<Integer, CustomerForm> implements CustomerFormDao {
   
	@SuppressWarnings("unchecked")
    public CustomerForm findByCustomerFormId(String id) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("customerFormId", id));
        return (CustomerForm) criteria.uniqueResult();
    }
    @SuppressWarnings("unchecked")
    public void saveCustomerForm(CustomerForm customerForm) {
        System.out.println("inside customerFormDao saveCustomerForm method");
        System.out.println("customerForm is: " + customerForm.toString());
    	persist(customerForm);
    }

//    @SuppressWarnings
//    public void deletePreviousCustomerForms(String phone){
//    	String hql = "Delete from CustomerForm c where c.phone"
//    }

}