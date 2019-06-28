package com.yanya.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

////import com.yanya.springmvc.model.Cart;
import com.yanya.springmvc.model.CartItem;
import java.lang.Integer;

@Service
public interface CartItemService {
	
	CartItem addItem(CartItem cartItem, HttpServletRequest request);
	void deleteItem(String cartItemId, HttpServletRequest model);
	CartItem updateItemQuantity(String cartItemId, int newQuantity );
	List<CartItem> findItemsByUserId(String userId);
	CartItem findItemByCartItemId(String cartItemId);
	List<String> findUnqiueMerchantIdsByCartId(String cartId);
	int emptyCart(String cartId, HttpServletRequest model, Integer decrementNumber);
	void ackCartItems(String cartId, HttpServletRequest model);
    
}