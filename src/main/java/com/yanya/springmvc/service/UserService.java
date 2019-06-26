package com.yanya.springmvc.service;
 
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.User;
 
public interface UserService extends UserDetailsService {

		User saveUser(User user);
		void updatePassword(String userId, String password);
    User findUserByUsername(String username);
    User findUserByUserId(String userId);
    boolean isUsernameUnique(String username);
    String setUserToModel(Customer customer, Merchant merchant, HttpServletRequest model);
     
}