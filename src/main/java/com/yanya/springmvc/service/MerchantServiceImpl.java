package com.yanya.springmvc.service;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanya.springmvc.model.Connection;
import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.UserPhoto;
import com.yanya.springmvc.dao.ConnectionDao;
import com.yanya.springmvc.dao.MerchantDao;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.ProfileBannerForm;
import com.yanya.springmvc.model.ProfilePhotoForm;
import com.yanya.springmvc.model.ProfileTagLineForm;
import com.yanya.springmvc.service.ConnectionService;
import com.yanya.springmvc.service.MerchantService;
import com.yanya.springmvc.service.ProductService;
import com.yanya.springmvc.service.UserPhotoService;
import com.yanya.springmvc.model.ProfileBannerForm;
import com.yanya.springmvc.model.StoreProfilePhotoForm;
import java.io.File;
import java.io.IOException;
import java.lang.Integer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.security.Principal;

@Service("merchantService")
@Transactional
public class MerchantServiceImpl implements MerchantService {
 
    @Autowired
    private MerchantDao merchantDao;
    
    @Autowired
    private ConnectionDao connectionDao;
    
    @Autowired
    private ConnectionService connectionService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private ImpressionService impressionService;
    
    @Autowired
    private ProductImpressionService productImpressionService;
    
    @Autowired
    private UserPhotoService userPhotoService;
    
    public Merchant saveMerchant(Merchant merchant) {
    	Merchant merch = merchantDao.saveMerchant(merchant);
    	makeDirectories(merch);
    	return merch;
    }
    
    private void makeDirectories(Merchant merchant){
//    	File storePhotos = new File("/media/Rihup/Merchant/StorePhotos" + File.separator +  merchant.getUserId());
//    	File storeBanners = new File("/media/Rihup/Merchant/StoreBanners"  + File.separator + merchant.getUserId());
//    	File productPhotos1 = new File("/media/Rihup/Merchant/ProductPhotos1" + File.separator  + merchant.getUserId());
//    	File productPhotos2 = new File("/media/Rihup/Merchant/ProductPhotos2"  + File.separator + merchant.getUserId());
//    	File productPhotos3 = new File("/media/Rihup/Merchant/ProductPhotos3" + File.separator + merchant.getUserId());
//    	File productPhotos4 = new File("/media/Rihup/Merchant/ProductPhotos4" + File.separator + merchant.getUserId());
//    	File productPhotos5 = new File("/media/Rihup/Merchant/ProductPhotos5" + File.separator  + merchant.getUserId());
//
//    	try{
//        	storePhotos.mkdir();
//        	storeBanners.mkdir();
//        	productPhotos1.mkdir();
//        	productPhotos2.mkdir();
//        	productPhotos3.mkdir();
//        	productPhotos4.mkdir();
//        	productPhotos5.mkdir();
//    	}catch(Exception e){ System.out.println("");}
//    }
    	
    	Path storePhotos = Paths.get("/media/Rihup/Merchant/StorePhotos" + File.separator +  merchant.getUserId());
    	Path storeBanners = Paths.get("/media/Rihup/Merchant/StoreBanners"  + File.separator + merchant.getUserId());
    	Path productPhotos1 = Paths.get("/media/Rihup/Merchant/ProductPhotos1" + File.separator  + merchant.getUserId());
    	Path productPhotos2 = Paths.get("/media/Rihup/Merchant/ProductPhotos2" + File.separator  + merchant.getUserId());   	  
    	Path productPhotos3 = Paths.get("/media/Rihup/Merchant/ProductPhotos3" + File.separator  + merchant.getUserId());    	  
    	Path productPhotos4 = Paths.get("/media/Rihup/Merchant/ProductPhotos4" + File.separator  + merchant.getUserId());   	  
    	Path productPhotos5 = Paths.get("/media/Rihup/Merchant/ProductPhotos5" + File.separator  + merchant.getUserId());
    	  
    	Path defaultPhoto = Paths.get("/media/Rihup/Merchant/StorePhotos/94333aa537b6e5cb945b67bd58988e43.jpg");
    	Path defaultBanner = Paths.get("/media/Rihup/Merchant/StoreBanners/weed.jpg");
    	
    	Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
        //add owners permission
        perms.add(PosixFilePermission.OWNER_READ); perms.add(PosixFilePermission.OWNER_WRITE);perms.add(PosixFilePermission.OWNER_EXECUTE);
        //add group permissions
        perms.add(PosixFilePermission.GROUP_READ);perms.add(PosixFilePermission.GROUP_WRITE); perms.add(PosixFilePermission.GROUP_EXECUTE);
        //add others permissions
        perms.add(PosixFilePermission.OTHERS_READ);  perms.add(PosixFilePermission.OTHERS_WRITE); perms.add(PosixFilePermission.OTHERS_EXECUTE);  
        
    	if(!Files.exists(storePhotos)){
    		try{
    			Files.createDirectories(storePhotos);
    			Files.setPosixFilePermissions(storePhotos, perms);
    			Files.copy(defaultPhoto, storePhotos.resolve(defaultPhoto.getFileName()));
    			Files.setPosixFilePermissions(Paths.get("/media/Rihup/Merchant/StorePhotos/" + merchant.getUserId() + "/94333aa537b6e5cb945b67bd58988e43.jpg"), perms);
    		}catch(IOException io){
    			io.printStackTrace();
    		}
    	}
    	
    	if(!Files.exists(storeBanners)){
    		try{
    			Files.createDirectories(storeBanners);
    			Files.setPosixFilePermissions(storeBanners, perms);
    			Files.copy(defaultBanner, storeBanners.resolve(defaultBanner.getFileName()) );
    			Files.setPosixFilePermissions(Paths.get("/media/Rihup/Merchant/StoreBanners/" + merchant.getUserId() + "/weed.jpg"), perms);
    		}catch(IOException io){
    			io.printStackTrace();
    		}
    	}
    	
    	if(!Files.exists(productPhotos1)){
    		try{
    			Files.createDirectories(productPhotos1);
    			Files.setPosixFilePermissions(productPhotos1, perms);
    		}catch(IOException io){
    			io.printStackTrace();
    		}
    	}
    	
    	if(!Files.exists(productPhotos2)){
    		try{
    			Files.createDirectories(productPhotos2);
    			Files.setPosixFilePermissions(productPhotos2, perms);
    		}catch(IOException io){
    			io.printStackTrace();
    		}
    	}
    	
    	if(!Files.exists(productPhotos3)){
    		try{
    			Files.createDirectories(productPhotos3);
    			Files.setPosixFilePermissions(productPhotos3, perms);
    		}catch(IOException io){
    			io.printStackTrace();
    		}
    	}
    	
    	if(!Files.exists(productPhotos4)){
    		try{
    			Files.createDirectories(productPhotos4);
    			Files.setPosixFilePermissions(productPhotos4, perms);
    		}catch(IOException io){
    			io.printStackTrace();
    		}
    	}
    	
    	if(!Files.exists(productPhotos5)){
    		try{
    			Files.createDirectories(productPhotos5);
    			Files.setPosixFilePermissions(productPhotos5, perms);
    		}catch(IOException io){
    			io.printStackTrace();
    		}
    	}
    	
    	
    	
    }
    
    public List<Merchant> findAllMerchants() {
        return merchantDao.findAllMerchants();
    }
    
    public Merchant findByPhone(String phone) {
        return merchantDao.findByPhone(phone);
    }
    
    public boolean isMerchantPhoneUnique(String phone) {
        Merchant merchant = findByPhone(phone);
        
        return ( merchant == null );
    }
    
   
    public Merchant findByStoreName(String storeName) {
        return merchantDao.findByStoreName(storeName);
    }
    
	  public Merchant findByUsername(String username){
		return merchantDao.findByUsername(username);
	}
	
	  public Merchant findByMerchantId(String userId){
		return merchantDao.findByMerchantId(userId);
	}
	
    
    public boolean isStoreNameUnique(String storeName) {
        Merchant merchant = findByStoreName(storeName);
        
        return ( merchant == null );
    }
    
    public boolean isUsernameUnique(String username) {
        Merchant merchant = findByUsername(username);
        
        return ( merchant == null );
    }
      
    public List<Merchant> findMerchantsVisibleToCustomer(String userId){
    	List<Merchant> publicOnlineMerchants = merchantDao.findMerchantsByVisibilityAndStatus("public", "online");
    	List<Merchant> privateOnlineMerchants = merchantDao.findMerchantsByVisibilityAndStatus("private", "online");
    	List<Merchant> allVisibleMerchants = new ArrayList<Merchant>();
    	
    	
    	//add all private merchants that the user is connected to
    	for(Merchant curr: privateOnlineMerchants){
    		Connection c = connectionDao.findConnectionByUsers(userId, curr.getUserId(), "accepted");	
    		if(c!=null){
    			System.out.println(c.getConnectionId() + curr.getUserId());
    			allVisibleMerchants.add(curr);
    		}
    	}
    	
    	//add all public merchants - none
    	for(Merchant curr: publicOnlineMerchants){	
    		allVisibleMerchants.add(curr);
    	}
    	  		
    	return allVisibleMerchants;
    		
    }
    
    public List<Merchant> findMerchantsVisibleToMerchant(String userId){
    	List<Merchant> publicOnlineMerchants = merchantDao.findMerchantsByVisibilityAndStatus("public", "online");
    	List<Merchant> privateOnlineMerchants = merchantDao.findMerchantsByVisibilityAndStatus("private", "online");
    	List<Merchant> allVisibleMerchants = new ArrayList<Merchant>();
    	   	
    	//add all private merchants that the user is connected to
    	for(Merchant curr: privateOnlineMerchants){
    		Connection c = connectionDao.findConnectionByUsers(userId, curr.getUserId(), "accepted");	
    		if(c!=null){
    			System.out.println(c.getConnectionId() + curr.getUserId());
    			allVisibleMerchants.add(curr);
    		}
    	}
    	
    	//add all public merchants - none
    	for(Merchant curr: publicOnlineMerchants){	
    		allVisibleMerchants.add(curr);
    	}
    	  		
    	return allVisibleMerchants;
    		
    }
    	
	  public Merchant setMerchantStoreDataToModel(HttpServletRequest model, String storeName){
		Merchant merchant = findByStoreName(storeName);
		try{
		
			List<Merchant> connectedMerchants = connectionService.findUserConnectedMerchants(merchant.getUserId());		
			List<Customer> connectedFriends = connectionService.findUserConnectedFriends(merchant.getUserId());
			Integer connFriendsSize = connectedFriends.size();
			Integer connMerchantsSize = connectedMerchants.size();
			Integer productsSize = merchant.getProducts().size();
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
    	
    if(merchant.getProducts()==null){
    		model.setAttribute("itemsSize", 0);
    }else{
        	model.setAttribute("itemsSize", productsSize);
        	System.out.println("size of userPhotos:" + productsSize);
    	}
    	model.setAttribute("profile", merchant);
    	model.setAttribute("profilePhotoForm", new ProfilePhotoForm());
    	model.setAttribute("profileBannerForm", new ProfileBannerForm());
    	model.setAttribute("profileTagLineForm", new ProfileTagLineForm());
		
	}catch(NullPointerException npe){
		System.out.println("caught npe#########");
		throw new ClassCastException();
		
	
	}
	return merchant;
}
    
	  public void updateMerchantLocation(String userId, String location ,String lat, String lng){
		merchantDao.updateMerchantLocation(userId, location, lat, lng);
		
	}
	
    public List<Merchant> findOnlineMerchantListVisibleToCustomer(String userId){
    	
	    List<Merchant> allVisibleMerchants = new ArrayList<Merchant>();    	  	
	    List<Merchant> privateOnlineMerchants = new ArrayList<Merchant>();
	    List<Merchant> publicOnlineMerchants = merchantDao.findMerchantsByVisibilityAndStatus("public", "online");
    	
	    for(Merchant m: publicOnlineMerchants){
	    	System.out.println("^^^^^^^^ " + m.getStoreName());
	    }
    	if(publicOnlineMerchants!=null && publicOnlineMerchants.size()>0){
    		allVisibleMerchants.addAll(publicOnlineMerchants);
    	}
    	
    	
	    List<Connection> userConnections = connectionDao.findUserConnectionsByType(userId, "merchant");	
    	List<String> connectedMerchantIds = new ArrayList<String>();
	    if(userConnections !=null && userConnections.size()>0){
	    	for(Connection c: userConnections){
	    			if(!c.getRequesterId().equals(userId)){
	    				connectedMerchantIds.add(c.getRequesterId());
	    			}else{
	    				connectedMerchantIds.add(c.getRequesteeId());
	    			}
	    	}
	    	privateOnlineMerchants = merchantDao.findMerchantsByVisibilityAndStatus("private", "online", connectedMerchantIds);	    		
	    	if(privateOnlineMerchants!=null && privateOnlineMerchants.size() > 0){
	    		allVisibleMerchants.addAll(privateOnlineMerchants);
	    	}
	    	
    	}
     		
	    
	    for(Merchant m: allVisibleMerchants){
	    	System.out.println("$$$$$$$$$$ " + m.getStoreName());
	    }
    	return allVisibleMerchants;
    		
    }	
 
    public List<Merchant> findOnlineMerchantListVisibleToMerchant(String userId){
    	List<Merchant> allVisibleMerchants = new ArrayList<Merchant>();    	  	
    	List<Merchant> privateOnlineMerchants = new ArrayList<Merchant>();
    	List<Merchant> publicOnlineMerchants = merchantDao.findMerchantsByVisibilityAndStatus("public", "online");
    	if(publicOnlineMerchants!=null && publicOnlineMerchants.size()>0){
    		allVisibleMerchants.addAll(publicOnlineMerchants);
    	}
    	
    	
	    List<Connection> userConnections = connectionDao.findUserConnectionsByType(userId, "merchant");	
    	List<String> connectedMerchantIds = new ArrayList<String>();
	    if(userConnections !=null && userConnections.size()>0){
	    	for(Connection c: userConnections){
	    			if(!c.getRequesterId().equals(userId)){
	    				connectedMerchantIds.add(c.getRequesterId());
	    			}else{
	    				connectedMerchantIds.add(c.getRequesteeId());
	    			}
	    	}
	    	
	    //add the merchant so they can be shown on the map too
	    connectedMerchantIds.add(userId);
	    privateOnlineMerchants = merchantDao.findMerchantsByVisibilityAndStatus("private", "online", connectedMerchantIds);	    		
	    if(privateOnlineMerchants!=null && privateOnlineMerchants.size() > 0){
	    	allVisibleMerchants.addAll(privateOnlineMerchants);
	    	}
	    	
    	}
     		
    	return allVisibleMerchants;
    		
    }
    
    public Merchant updateMerchantVisibility(String userId, String newVisibility){
    	Merchant merchant = merchantDao.updateMerchantVisibility(userId, newVisibility);
    	merchantDao.updateMerchantVisibility(userId, newVisibility);
    	return merchant;
    }
    
    public Merchant updateProfilePhoto(String userId, String profilePhoto){
    	Merchant merchant = merchantDao.updateProfilePhoto(userId, profilePhoto);
    	return merchant;
    }
    
    public Merchant updateStoreBanner(String userId, String storeBanner){
    	Merchant merchant = merchantDao.updateStoreBanner(userId, storeBanner);
    	return merchant;
    }
    
    public Merchant updateStoreTagLine(String userId, String storeTagLine){
    	Merchant merchant = merchantDao.updateStoreTagLine(userId, storeTagLine);
    	return merchant;
    }
    
    public void decrementMerchantNotifications(String userId){
    	merchantDao.decrementNotifications(userId);
    }
    
    public void incrementMerchantNotifications(String userId){
    	merchantDao.incrementNotifications(userId);
    }
  
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Merchant merchant = null;
    	try{
    		merchant = merchantDao.findByUsername(username);
    	}catch(ClassCastException ce){ System.out.println("Class cast exception caught. cannot convert value from findMerchantByStoreName(username) to Merchant"); }
    	if(merchant != null){
    		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    		authorities.add(new SimpleGrantedAuthority("ROLE_MERCHANT"));
    		return new org.springframework.security.core.userdetails.User(merchant.getUsername(), merchant.getPassword(), authorities);
    	}
    	
    	throw new UsernameNotFoundException("Merchant '" + username + "' not found.");
    	
    }
    
	public Merchant setMerchantUserDetailsToModel(HttpServletRequest model, Principal principal){
		Merchant merchant = null;
		String  username = "";
		try{
			username = principal.getName();			
		}catch(ClassCastException cce){
			System.out.println("not logged in");
			throw cce;
		}
		

		merchant = findByUsername(username);
		//}
		
		System.out.println("called merchnt daouser namemethod 1st time");
		
		List<Product> hotProducts = productService.findProductsVisibleToMerchant(merchant.getUserId());
		HashMap<String,String> productImpressions = productImpressionService.findProductImpressionsByProductIdsAndUserId(hotProducts, merchant.getUserId(), "merchant");
			
		List<UserPhoto> userPhotos = userPhotoService.findUserPhotosVisibleToMerchant(merchant.getUserId());
		HashMap<String, String> impressions = impressionService.findImpressionsByUserPhotoIdsAndUserId(userPhotos, merchant.getUserId(), "merchant");
			

		for (Map.Entry<String, String> entry : productImpressions.entrySet()) { 
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
		}
		Integer productsSize = merchant.getProducts().size();
	
		model.getSession().setAttribute("home", "/sell/");
		model.getSession().setAttribute("itemSize", productsSize);
		model.getSession().setAttribute("userPhotos", userPhotos);
		model.getSession().setAttribute("impressions", impressions);
		model.getSession().setAttribute("productImpressions", productImpressions);
		model.getSession().setAttribute("hotProducts", hotProducts);
		model.getSession().setAttribute("user", merchant);
		model.getSession().setAttribute("userType", "merchant");
		model.getSession().setAttribute("userVisibleName", merchant.getStoreName());
		
		return merchant;
	}
	
	@Cacheable("visibleMerchants")
	public void setVisibleMerchantsToSession(String userId, HttpServletRequest model){
		List<Merchant> visibleMerchants = findOnlineMerchantListVisibleToCustomer(userId);
		//set visible angels in session
		model.setAttribute("visibleMerchants", visibleMerchants);
	}
    
 
 
 
}