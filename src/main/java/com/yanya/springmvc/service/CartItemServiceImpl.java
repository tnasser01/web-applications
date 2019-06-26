package com.yanya.springmvc.service;
 
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanya.springmvc.dao.CartItemDao;
import com.yanya.springmvc.event.NotificationEventProducer;
//import com.yanya.springmvc.model.Cart;
import com.yanya.springmvc.model.CartItem;
import com.yanya.springmvc.model.Customer;
//import com.yanya.springmvc.service.CartService;
import java.lang.Integer;
import com.yanya.springmvc.event.NotificationEventCartItem;

@Service("cartItemService")
@Transactional
public class CartItemServiceImpl implements CartItemService {
 
    @Autowired
    private CartItemDao cartItemDao;
    @Autowired 
    private NotificationEventProducer  producer;
    
    public CartItem addItem(CartItem cartItem, HttpServletRequest request) {
    	System.out.println("inside CartItemServiceImpl - addItem method");   	
    	String userId = ((Customer)request.getSession().getAttribute("customer")).getUserId();
    	System.out.println("CUSTOMER ID IS: " + userId);
    	for(int i = 0; i <cartItem.getQuantity(); i++ ){
    		//increase the cart #
    		producer.createNotification(request, 
    				"increment", 
    				userId , 
    				"customer");
    	}
    	return cartItemDao.saveNewCartItem(cartItem);	
    }	
   
    public List<CartItem> findItemsByUserId(String userId){
    	return cartItemDao.findItemsByUserId(userId);
    }
    
  	public CartItem findItemByCartItemId(String cartItemId){
  		return cartItemDao.findItemByCartItemId(cartItemId);
  		
  	}
    
    public List<String> findUnqiueMerchantIdsByCartId(String cartId){
    	return cartItemDao.findUnqiueMerchantIdsByCartId(cartId);
    }
    
    public int emptyCart(String userId, HttpServletRequest request, Integer dec){    	
    	callProductNotificationDecrement(request, dec);
    	cartItemDao.emptyCart(userId);
    	return dec;
    	
    }
        
    public void deleteItem(String cartItemId, HttpServletRequest request) {
    	CartItem cartItem = findItemByCartItemId(cartItemId);
    	callProductNotificationDecrement(request, cartItem.getQuantity());
    	cartItemDao.deleteItem(cartItem);
    }
    
    private void callProductNotificationDecrement(HttpServletRequest request, Integer dec){
    	String userId = ((Customer)request.getSession().getAttribute("customer")).getUserId();
       	System.out.println("CUSTOMER ID IS: " + userId);
       	for(int i = 0; i < dec; i++ ){
	       	producer.createNotification(request, 
					"decrement", 
					userId , 
					"customer");
       	}
       	
    }
    
    public CartItem updateItemQuantity(String cartItemId, int newQuantity ) {
        return cartItemDao.updateItemQuantity(cartItemId, newQuantity );
        
    }
    
	public void ackCartItems(String cartId, HttpServletRequest model){
    	Customer customer = (Customer)model.getSession().getAttribute("customer");
    	Integer quantity = cartItemDao.ackCartItems(customer.getUserId());
		for(int i = 0; i < quantity; i++){
			producer.createNotification(model, "decrement", customer.getUserId(), "customer" );
		}	
	}

 
}