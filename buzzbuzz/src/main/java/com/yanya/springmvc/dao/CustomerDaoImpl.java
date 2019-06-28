package com.yanya.springmvc.dao;
 
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.UserPhoto;
 
@Repository("customerDao")
public class CustomerDaoImpl extends AbstractDao<Integer, Customer> implements CustomerDao {
   
	@SuppressWarnings("unchecked")
    public Customer findByCustomerId(String id) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("userId", id));
        return (Customer) criteria.uniqueResult();
    }
    @SuppressWarnings("unchecked")
    public Customer saveCustomer(Customer customer) {
        System.out.println("inside customerDao saveCustomer method");
        System.out.println("customer is: " + customer.toString());
        String id = (String)save(customer);
    	System.out.println("The id returned from hibernate save is:" + id);
    	String hql = "FROM Customer c where c.userId = '" + id + "'";
    	Query query = getSession().createQuery(hql);
    	return  (Customer)query.uniqueResult();   
    	
    }
    @SuppressWarnings("unchecked")
    public void deleteCustomerByPhone(String phone) {
        Query query = getSession().createSQLQuery("delete from Customer where phone = :phone");
        query.setString("phone", phone);
        query.executeUpdate();
    }
    
    @SuppressWarnings("unchecked")
    public List<Customer> findCustomerByInvitation(String invitation){
    	String hql = "FROM Customer c where c.invitation =:invitation";
    	Query query = getSession().createQuery(hql);
    	query.setString("invitation", invitation);
    	List<Customer> customer = (List<Customer>)query.list();
    	return customer;
    }
    
 
    @SuppressWarnings("unchecked")
    public List<Customer> findAllCustomers() {
        Query query = getSession().createQuery("from Customer");
        System.out.println("inside findAllCustomers()");
    	Criteria criteria = getSession().createCriteria(Customer.class);
    	criteria.setFetchMode("comments", FetchMode.JOIN);
    	List<Customer> customers = (List<Customer>)criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list(); 
    	if(customers==null){
    		customers = new ArrayList<Customer>();
    	}
    	return customers;
    }
    
    @SuppressWarnings("unchecked")
    public List<Customer> findByVisibility(String visibility){
        Query query = getSession().createQuery("from Customer where visibility = :visibility");
        query.setParameter("visibility", visibility);
    	List<Customer> customers = (List<Customer>)query.list(); 
    	if(customers==null){
    		customers = new ArrayList<Customer>();
    	}
        return customers;
    }
 
    @SuppressWarnings("unchecked")
    public Customer findCustomerByPhone(String phone) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("phone", phone));
        return (Customer) criteria.uniqueResult();
    }
    
    @SuppressWarnings("unchecked") 
    public Customer findCustomerByUsername(String username) {
       System.out.println("inside customer dao impl.  FindCustomerByUserName");
       System.out.println("username is " + username);
    	Criteria criteria = getSession().createCriteria(Customer.class);
        criteria.add(Restrictions.eq("username", username));
    	criteria.setFetchMode("userphoto.comments", FetchMode.JOIN);
//        Query query = getSession().createSQLQuery("select Customer from Customer where username = :username" );
//        query.setString("username", username);
        return (Customer)criteria.uniqueResult();
    }
	@SuppressWarnings("unchecked")
    public Customer updateCustomerVisibility(String userId, String newVisibility){
        String hql = "update Customer m set m.visibility=:visibility where m.userId=:userId ";
        Query query = getSession().createQuery(hql);
        System.out.println("userId is: " + userId);
        System.out.println("new visibility is: " + newVisibility);
        query.setParameter("visibility", newVisibility);
        query.setParameter("userId", userId);
        query.executeUpdate();
        
    	String hql2 = "FROM Customer m where m.userId = '" + userId + "'";
    	Query merchQuery = getSession().createQuery(hql2);
    	return  (Customer)merchQuery.uniqueResult(); 
   
    }
	@SuppressWarnings("unchecked")
    public Customer updateProfilePhoto(String userId, String profilePhoto){
        String hql = "update Customer m set m.profilePhoto=:profilePhoto where m.userId=:userId ";
        Query query = getSession().createQuery(hql);
        System.out.println("userId is: " + userId);
        System.out.println("profile Photo is: " + profilePhoto);
        query.setParameter("profilePhoto", profilePhoto);
        query.setParameter("userId", userId);
        query.executeUpdate();
        
    	String hql2 = "FROM Customer m where m.userId = '" + userId + "'";
    	Query custQuery = getSession().createQuery(hql2);
    	return  (Customer)custQuery.uniqueResult(); 
   
    }
	
	@SuppressWarnings("unchecked")
   public Customer updateProfileBanner(String userId, String profileBanner){
        String hql = "update Customer m set m.profileBanner=:profileBanner where m.userId=:userId ";
        Query query = getSession().createQuery(hql);
        System.out.println("userId is: " + userId);
        System.out.println("profile banner is: " + profileBanner);
        query.setParameter("profileBanner", profileBanner);
        query.setParameter("userId", userId);
        query.executeUpdate();
        
    	String hql2 = "FROM Customer m where m.userId = '" + userId + "'";
    	Query custQuery = getSession().createQuery(hql2);
    	return  (Customer)custQuery.uniqueResult(); 
	}
	
	@SuppressWarnings("unchecked")
    public Customer updateProfileTagLine(String userId, String profileTagLine){
        String hql = "update Customer c set c.profileTagLine=:profileTagLine where c.userId=:userId ";
        Query query = getSession().createQuery(hql);
        System.out.println("userId is: " + userId);
        System.out.println("profileTagLine is: " + profileTagLine);
        query.setParameter("profileTagLine", profileTagLine);
        query.setParameter("userId", userId);
        query.executeUpdate();
        
    	String hql2 = "FROM Customer m where m.userId = '" + userId + "'";
    	Query custQuery = getSession().createQuery(hql2);
    	return  (Customer)custQuery.uniqueResult(); 
	}
	
	@SuppressWarnings("unchecked")
	public Customer incrementNotifications(String userId){
		String  hql ="UPDATE Customer c set c.notifications = c.notifications + 1  WHERE c.userId = '" + userId + "'";
        Query query = getSession().createQuery(hql);
        System.out.println("userId is: " + userId);
        query.executeUpdate();
        
    	String hql2 = "FROM Customer m where m.userId = '" + userId + "'";
    	Query custQuery = getSession().createQuery(hql2);
    	return  (Customer)custQuery.uniqueResult(); 
	}
	
  @SuppressWarnings("unchecked")
	public List<Customer> findCustomersByCustomerIds(List<String> customerIds){
    	String hql = "FROM Customer m where m.userId IN (:customerIds)";
    	Query query = getSession().createQuery(hql);
		query.setParameterList("customerIds", customerIds);
		List<Customer> customers = (List<Customer>)query.list();
		if(customers==null){
			customers = new ArrayList<Customer>();
		}
		return customers;
    }	
	
	@SuppressWarnings("unchecked")
	public Customer decrementNotifications(String userId){
		String  hql ="UPDATE Customer c set c.notifications = c.notifications - 1 WHERE c.userId = '" + userId + "'";
        Query query = getSession().createQuery(hql);
        System.out.println("userId is: " + userId);
        query.executeUpdate();
        
    	String hql2 = "FROM Customer m where m.userId = '" + userId + "'";
    	Query custQuery = getSession().createQuery(hql2);
    	return  (Customer)custQuery.uniqueResult(); 
	}
}