package com.yanya.springmvc.dao;
 
import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManagerFactory;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;

//import com.yanya.springmvc.model.Cart;
import com.yanya.springmvc.model.CartItem;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.ZipCode;
import com.yanya.springmvc.dao.AbstractDao;
//import com.yanya.springmvc.dao.CartDao;

 
@Repository("cartItemDao")
public class CartItemDaoImpl extends AbstractDao<String, CartItem> implements CartItemDao {

    @SuppressWarnings("unchecked")
    public CartItem saveNewCartItem(CartItem cartItem) {
    	System.out.println("inside CartItemDaoImpl - saveNewCartItem method");
    	String id = (String)save(cartItem);
    	System.out.println("The id returned from hibernate save is:" + id);
    	String hql = "FROM CartItem c where c.cartItemId = '" + id + "'";
    	Query query = getSession().createQuery(hql);
    	return (CartItem)query.uniqueResult();
   
    }
    
    @SuppressWarnings("unchecked")
    public void deleteItem(CartItem cartItem) {
    	System.out.println("inside CartItemDaoImpl - deleteCartItem method");
    	delete(cartItem);
   
    }
    
    @SuppressWarnings("unchecked")
    public CartItem findItemByCartItemId(String cartItemId){
    	String hql = "From  CartItem c where  c.cartItemId = '" + cartItemId + "'";
    	Query query = getSession().createQuery(hql);
    	CartItem cartItem = (CartItem)query.uniqueResult();
    	return cartItem;
    }
    
    
    @SuppressWarnings("unchecked")
    public List<CartItem> findItemsByUserId(String userId){
    	String hql = "From CartItem c where c.customer.userId = '" + userId + "'";
    	Query query = getSession().createQuery(hql);
    	List<CartItem> cartItems = (List<CartItem>)query.list();
    	if(cartItems==null){
    		cartItems = new ArrayList<CartItem>();
    	}
    	return cartItems;
    }
    
    @SuppressWarnings("unchecked")
    public List<String> findUnqiueMerchantIdsByCartId(String cartId){
    	String hql = "SELECT distint c.userId FROM CartItem where c.cart.cartId = '" + cartId + "'";
    	Query query = getSession().createQuery(hql);
    	List<String> userIds = (List<String>)query.list();
    	if(userIds==null){
    		userIds = new ArrayList<String>();
    	}
    	return userIds;
    }
    
    
   
    @SuppressWarnings("unchecked")
    public int emptyCart(String userId){
    	Query query = getSession().createQuery("delete from CartItem c where c.customer.userId = :userId");
    	query.setParameter("userId", userId);
    	int result = query.executeUpdate();
    	return result;
    }
    
    @SuppressWarnings("unchecked")
    public CartItem updateItemQuantity(String cartItemId, int newQuantity) {
  
    	String hql = "Update CartItem c set c.newQuantity = '" + newQuantity + "' where c.cartItemId = '" + cartItemId + "'";
    	Query query = getSession().createQuery(hql);
    	query.executeUpdate();
    	String hql2 ="From CartItem c where c.cartItemId = '" + cartItemId + "'";
    	Query query2 = getSession().createQuery(hql2);
    	
    	return  (CartItem)query2.uniqueResult();
   
    }
    
    @SuppressWarnings("unchecked")
	public Integer ackCartItems(String cartId){
    	String hql = "update CartItem ci set ci.acked='1' where ci.acked='0' and cartId ='" + cartId + "'";
    	Query query = getSession().createQuery(hql);
    	return query.executeUpdate();

    }
    

}