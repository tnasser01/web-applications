package com.yanya.springmvc.service;
 
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.yanya.springmvc.dao.ConnectionDao;
import com.yanya.springmvc.dao.CustomerDao;
//import com.yanya.springmvc.model.Cart;
import com.yanya.springmvc.model.Connection;
import com.yanya.springmvc.model.Customer;

import com.yanya.springmvc.model.CustomerControlMenuForm;
import com.yanya.springmvc.model.Impression;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.ProductImpression;
import com.yanya.springmvc.model.ProfileBannerForm;
import com.yanya.springmvc.model.ProfilePhotoForm;
import com.yanya.springmvc.model.ProfileTagLineForm;
import com.yanya.springmvc.model.UserPhoto;
import com.yanya.springmvc.service.ConnectionService;
import com.yanya.springmvc.service.UserPhotoService;
 
@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {
 
    @Autowired
    private CustomerDao dao;
    
    @Autowired
    private ConnectionDao connectionDao;
    
//    @Autowired
//    private CartService cartService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserPhotoService userPhotoService;
    
    @Autowired
    private ImpressionService impressionService;
    
    @Autowired
    private ProductImpressionService productImpressionService;
    
    @Autowired
    private ConnectionService connectionService;
     
    public Customer findCustomerByCustomerId(String userId) {
        return dao.findByCustomerId(userId);
    }
 
    public void saveCustomer(Customer customer) {
        Customer c = dao.saveCustomer(customer);
        System.out.println("*********Customer ID after save is: " + c.getUserId());
        makeDirectories(c);
   
    }
 
    public void updateCustomer(Customer customer) {
        Customer entity = dao.findByCustomerId(customer.getUserId());
        if(entity!=null){
            entity.setUsername(customer.getUsername());
            entity.setPassword(customer.getPassword());
            entity.setPhone(customer.getPhone());
        }
    }
 
    public void deleteCustomerByPhone(String phone) {
        dao.deleteCustomerByPhone(phone);
    }
    
	@Transactional
    public List<Customer> findAllCustomers() {
        return dao.findAllCustomers();
    }
 
    public Customer findCustomerByPhone(String phone) {
        return dao.findCustomerByPhone(phone);
    }
    
    public boolean isCustomerPhoneUnique(String phone) {
        Customer customer = findCustomerByPhone(phone);
        
        return ( customer == null );
    }
    
    public boolean isUsernameUnique(String username) {
        Customer customer = findCustomerByUsername(username);
        
        return ( customer == null );
    }
    
    public Customer findCustomerByUsername(String username) {
    		return dao.findCustomerByUsername(username);
    }
    
    public Customer updateCustomerVisibility(String userId, String newVisibility){
    	Customer customer = dao.updateCustomerVisibility(userId, newVisibility);
//    	currentCustomerStatusDao.updateCustomerVisibility(CustomerId, newVisibility);
    	return customer;
    }
    
	public List<Customer> findCustomerByInvitation(String invitation){
		List<Customer> customers = dao.findCustomerByInvitation(invitation);
		return customers;
	}
    public Customer updateProfilePhoto(String userId, String profilePhoto){
    	Customer customer = dao.updateProfilePhoto(userId, profilePhoto);
    	return customer;
    }
    
    public Customer updateProfileBanner(String userId, String profileBanner){
    	Customer customer = dao.updateProfileBanner(userId, profileBanner);
    	return customer;
    }
    
    public Customer updateProfileTagLine(String userId, String profileTagLine){
    	Customer customer = dao.updateProfileTagLine(userId, profileTagLine);
    	return customer;
    }
//    
//    public Customer updatePassword(String userId, String password){
//    	Customer customer = dao.updatePass(userId, password);
//    	return customer;
//    }
//    
    public void decrementCustomerNotifications(String userId){
    	dao.decrementNotifications(userId);
    }
    
    public void incrementCustomerNotifications(String userId){
    	dao.incrementNotifications(userId);
    }
    

	
    public List<Customer> findCustomersVisibleToCustomer(String userId){
    	List<Customer> publicCustomers = dao.findByVisibility("public");
    	List<Customer> privateCustomers = dao.findByVisibility("private");
    	List<Customer> allVisibleCustomers = new ArrayList<Customer>();
    	
    	
    	//add all private merchants that the user is connected to
    	for(Customer curr: privateCustomers){
    		Connection c = connectionDao.findConnectionByUsers(userId, curr.getUserId(), "accepted");	
    		if(c!=null){
    			System.out.println(c.getConnectionId() + curr.getUserId());
    			allVisibleCustomers.add(curr);
    		}
    	}
    	
    	//add all public merchants - none
    	for(Customer curr: publicCustomers){	
    		allVisibleCustomers.add(curr);
    	}
    	  		
    	return allVisibleCustomers;
    		
    }
    
    public List<Customer> findCustomersVisibleToMerchant(String userId){
    	List<Customer> publicCustomers = dao.findByVisibility("public");
    	List<Customer> privateCustomers = dao.findByVisibility("private");
    	List<Customer> allVisibleCustomers = new ArrayList<Customer>();
    	
    	
    	//add all private merchants that the user is connected to
    	for(Customer curr: privateCustomers){
    		Connection c = connectionDao.findConnectionByUsers(userId, curr.getUserId(), "accepted");	
    		if(c!=null){
    			System.out.println(c.getConnectionId() + curr.getUserId());
    			allVisibleCustomers.add(curr);
    		}
    	}
    	
    	//add all public Customers - none
    	for(Customer curr: publicCustomers){	
    		allVisibleCustomers.add(curr);
    	}
    	  		
    	return allVisibleCustomers;
    		
    }
    
    private void makeDirectories(Customer customer){
	    	Path profilePhotos = Paths.get("/media/Rihup/User/ProfilePhotos" + File.separator +  customer.getUserId());
	    	Path profileBanners = Paths.get("/media/Rihup/User/ProfileBanners"  + File.separator + customer.getUserId());
	    	Path userPhotos = Paths.get("/media/Rihup/User/UserPhotos" + File.separator  + customer.getUserId());
	        
	    	Path defaultPhoto = Paths.get("/media/Rihup/User/ProfilePhotos/default.png");
	    	Path defaultBanner = Paths.get("/media/Rihup/User/ProfileBanners/weed.jpg");
	    	
	    	Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
	        //add owners permission
	        perms.add(PosixFilePermission.OWNER_READ); perms.add(PosixFilePermission.OWNER_WRITE);perms.add(PosixFilePermission.OWNER_EXECUTE);
	        //add group permissions
	        perms.add(PosixFilePermission.GROUP_READ);perms.add(PosixFilePermission.GROUP_WRITE); perms.add(PosixFilePermission.GROUP_EXECUTE);
	        //add others permissions
	        perms.add(PosixFilePermission.OTHERS_READ);  perms.add(PosixFilePermission.OTHERS_WRITE); perms.add(PosixFilePermission.OTHERS_EXECUTE);  
	        
	    	if(!Files.exists(profilePhotos)){
	    		try{
	    			Files.createDirectories(profilePhotos);
	    			Files.setPosixFilePermissions(profilePhotos, perms);
	    			Files.copy(defaultPhoto, profilePhotos.resolve(defaultPhoto.getFileName()));
	    			Files.setPosixFilePermissions(Paths.get("/media/Rihup/User/ProfilePhotos/" + customer.getUserId() + "/default.png"), perms);
	    		}catch(IOException io){
	    			io.printStackTrace();
	    		}
	    	}
	    	
	    	if(!Files.exists(profileBanners)){
	    		try{
	    			Files.createDirectories(profileBanners);
	    			Files.setPosixFilePermissions(profileBanners, perms);
	    			Files.copy(defaultBanner, profileBanners.resolve(defaultBanner.getFileName()) );
	    			Files.setPosixFilePermissions(Paths.get("/media/Rihup/User/ProfileBanners/" + customer.getUserId() + "/weed.jpg"), perms);
	    		}catch(IOException io){
	    			io.printStackTrace();
	    		}
	    	}
	    	
	    	if(!Files.exists(userPhotos)){
	    		try{
	    			Files.createDirectories(userPhotos);
	    			Files.setPosixFilePermissions(userPhotos, perms);
	    		}catch(IOException io){
	    			io.printStackTrace();
	    		}
	    	}
    	
    }

    	
    
	public Customer setCustomerUserDetailsToModel(HttpServletRequest model, Principal principal){
		
		Customer customer = null;
		String username = "";
		try{
			 username = principal.getName();
		}catch(ClassCastException | NullPointerException cce){
			System.out.println("not logged in");
			throw cce;
		}
		
		System.out.println("retrieving customer user from session");
		//customer = (Customer)model.getSession().getAttribute("user");		
		//if(customer==null){
			System.out.println("******* Customer was not in session so we have to check the database");
			customer = findCustomerByUsername(username);
	//	}
		
		System.out.println("called customer daouser namemethod 1st time");
		
		List<Product> hotProducts = productService.findProductsVisibleToCustomer(customer.getUserId());
		HashMap<String,String> productImpressions = productImpressionService.findProductImpressionsByProductIdsAndUserId(hotProducts, customer.getUserId(), "customer");
			
		List<UserPhoto> userPhotos = userPhotoService.findUserPhotosVisibleToCustomer(customer.getUserId());
		HashMap<String, String> impressions = impressionService.findImpressionsByUserPhotoIdsAndUserId(userPhotos, customer.getUserId(), "customer");
			

		for (Map.Entry<String, String> entry : impressions.entrySet()) { 
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
		}
		Integer userPhotosSize = customer.getUserPhotos().size();
		

		//model.getSession().setAttribute("userPhotosSize", userPhotosSize);
		model.getSession().setAttribute("home", "/");
		model.getSession().setAttribute("itemsSize", userPhotosSize);
		model.getSession().setAttribute("userPhotos", userPhotos);
		model.getSession().setAttribute("impressions", impressions);
		model.getSession().setAttribute("productImpressions", productImpressions);
		model.getSession().setAttribute("hotProducts", hotProducts);
		model.getSession().setAttribute("user", customer);
		model.getSession().setAttribute("customer", customer);
		model.getSession().setAttribute("userType", "customer");
		model.getSession().setAttribute("userVisibleName", customer.getUsername());
		return customer;
	}
	
	public Customer setCustomerProfileDataToModel(HttpServletRequest model, String username){
			
		Customer customer =  findCustomerByUsername(username);
				
		try{
		
			List<Merchant> connectedMerchants = connectionService.findUserConnectedMerchants(customer.getUserId());			
			List<Customer> connectedFriends = connectionService.findUserConnectedFriends(customer.getUserId());
			Integer connFriendsSize = connectedFriends.size();
			Integer connMerchantsSize = connectedMerchants.size();
			Integer userPhotosSize = customer.getUserPhotos().size();
	    model.setAttribute("connectedMerchants", connectedMerchants);
	    model.setAttribute("connectedFriends", connectedFriends);
	    if(connectedMerchants==null){
	    		model.setAttribute("connMerchantsSize", 0);
	    }else{
	    		model.setAttribute("connMerchantsSize", connMerchantsSize);
	    		System.out.println("size of connected merchants:" + connectedMerchants.size());
	    }
	    	
	    if(connectedFriends==null){
	    		model.setAttribute("connFriendsSize", 0);
	    }else{
	        	model.setAttribute("connFriendsSize", connFriendsSize);
	        	System.out.println("size of connected friends:" + connectedFriends.size());
	   	}
	    	
	    if(customer.getUserPhotos()==null){
	    		model.setAttribute("itemsSize", 0);
	    }else{
	        	model.setAttribute("itemsSize", userPhotosSize);
	        	System.out.println("size of userPhotos:" + userPhotosSize);
	    	}
	    	model.setAttribute("profile", customer);
	    	model.setAttribute("profilePhotoForm", new ProfilePhotoForm());
	    	model.setAttribute("profileBannerForm", new ProfileBannerForm());
	    	model.setAttribute("profileTagLineForm", new ProfileTagLineForm());
			
		}catch(NullPointerException npe){
			System.out.println("caught npe#########");
			throw new ClassCastException();
			
		
		}
		return customer;
	}
	

	

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	System.out.println("insude customerserviceImpl - loadUserByUsername");
    	System.out.println("username is " + username);
    	Customer customer = dao.findCustomerByUsername(username);
    	if(customer != null){
    		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    		authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
    		return new User(customer.getUsername(), customer.getPassword(), authorities);
    	}
    	
    	throw new UsernameNotFoundException("Customer '" + username + "' not found.");
    	
    }
    
    
    
    
     
}