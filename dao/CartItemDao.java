package com.yanya.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yanya.springmvc.model.CartItem;
//import com.yanya.springmvc.model.Cart;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.ZipCode;
 
public interface CartItemDao {
 
	@Autowired
	CartItem saveNewCartItem(CartItem cartItem);
	
	@Autowired
  void deleteItem(CartItem cartItem);
	
	@Autowired
	CartItem updateItemQuantity(String cartItemId, int quantity);
	
	@Autowired
	List<CartItem> findItemsByUserId(String userId);
	
	@Autowired
	CartItem findItemByCartItemId(String cartItemId);
	
	@Autowired
	List<String> findUnqiueMerchantIdsByCartId(String cartId);
	
	@Autowired
	int emptyCart(String cartId);
	
	@Autowired
	Integer ackCartItems(String cartId);
	
}