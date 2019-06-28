package com.yanya.springmvc.dao;
 
import java.util.List;
 
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.User;
 
@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {
    
    @SuppressWarnings("unchecked")
    public User saveUser(User user) {
        System.out.println("inside userDao saveUser method");
        System.out.println("user is: " + user.toString());
        String id = (String)save(user);
    	System.out.println("The id returned from hibernate save is:" + id);
    	String hql = "FROM User c where c.userId = '" + id + "'";
    	Query query = getSession().createQuery(hql);
    	return  (User)query.uniqueResult();   
    	
    }
    
//    @SuppressWarnings("unchecked")
//    public User updateUser(User user) {
//        System.out.println("inside userDao saveUser method");
//        System.out.println("user is: " + user.toString());
//        String id = (String)update(user);
//    	System.out.println("The id returned from hibernate save is:" + id);
//    	String hql = "FROM User c where c.userId = '" + id + "'";
//    	Query query = getSession().createQuery(hql);
//    	return  (User)query.uniqueResult();   
//    	
//    }
    
    @SuppressWarnings("unchecked")
    public User findUserByUsername(String username) {
        System.out.println("inside UserDaoImpl");
    	Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("username", username));
        System.out.println("About to return unique result");
        return(User)criteria.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    public User findUserByUserId(String userId) {
        System.out.println("inside UserDaoImpl");
        System.out.println(userId + "&&&&&&&&");
        String hql = "FROM User u where u.userId =:userId";
//   	Criteria criteria = getSession().createCriteria(User.class);
//        criteria.add(Restrictions.eq("userId", userId));
//        System.out.println("About to return unique result");
        Query query = getSession().createQuery(hql);
        query.setParameter("userId", "userId");
        User user = (User)query.uniqueResult();
        if(user==null){System.out.println( "user is null!!!!!");}
        else{System.out.println(user.getUserId() + user.getUsername());}
        return user;
    }
    
  	@SuppressWarnings("unchecked")
    public void updatePassword(String username, String password){
        System.out.println("inside user dao update password");
        System.out.println("username: " + username);
        System.out.println("password: " + password);      
        String hql = "update com.yanya.springmvc.model.User u set u.password=:password where u.username=:username";
        Query query = getSession().createQuery(hql);
        query.setParameter("password", password);
        query.setParameter("username", username);
        query.executeUpdate();
        
  }
    
}