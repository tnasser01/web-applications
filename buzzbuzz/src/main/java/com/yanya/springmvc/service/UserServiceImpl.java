package com.yanya.springmvc.service;
 
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.yanya.springmvc.dao.UserDao;
import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.RoleEnum;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.service.MerchantService;
import com.yanya.springmvc.service.CustomerService;

 
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
 
    @Autowired
    private UserDao dao;
   
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private MerchantService merchantService;
    
    public User findUserByUsername(String username) {
    		return dao.findUserByUsername(username);
    }
    
    public User findUserByUserId(String userId){
    		return dao.findUserByUserId(userId);
    }
    
    public void updatePassword(String userId, String password){
  		dao.updatePassword(userId, password);
  }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	User user = dao.findUserByUsername(username);
    	
    	if(user instanceof Customer){
    		System.out.println("inside UserServiceImpl loadUserByUsername.  this is instance of Customer ");
    		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    		authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
    		
    		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    	}
    	else if(user instanceof Merchant){
    		System.out.println("inside UserServiceImpl loadUserByUsername.  this is instance of Merchant ");
    		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    		authorities.add(new SimpleGrantedAuthority("ROLE_MERCHANT"));
    		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    	}
		System.out.println(".inside UserServiceImpl loadUserByUsername.  this is instance neither customer nor merchant ");
    	throw new UsernameNotFoundException("User '" + username + "' not found.");
    	
    }
    
    public boolean isUsernameUnique(String username) {
        User user = findUserByUsername(username);
        
        return ( user == null );
    }
    
    public User saveUser(User user){
    	return dao.saveUser(user);
    }
     
    
    public String setUserToModel(Customer customer, Merchant merchant, HttpServletRequest model){
		String userType = null;
		
		
    	try{
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
    		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
    		if(authentication != null){
    			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    			for(GrantedAuthority ga: authorities){
    				System.out.println("&&&&&&&&&&&&&" + ga.getAuthority() + "&&&&&&&&&&&");
    				if (ga.getAuthority().equals(RoleEnum.ROLE_MERCHANT.toString())) {
    					merchant  = merchantService.findByUsername(userDetails.getUsername());
    					model.setAttribute("userType", "merchant");
    					model.setAttribute("user", merchant);
    					userType = "merchant";
    				}else if (ga.getAuthority().equals(RoleEnum.ROLE_CUSTOMER.toString())) {
        				customer = customerService.findCustomerByUsername(userDetails.getUsername());
    					model.setAttribute("userType", "customer");
        					model.setAttribute("user", customer);
        					userType = "customer";	
        			}
    			}
    		}
    	}catch(ClassCastException cce){
    		System.out.println("caught classcast exception!");
    		throw cce;
		}
		
    
    	return userType;
    }




}