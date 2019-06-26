package com.yanya.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.MerchantStatus;

import java.lang.Integer;
import java.security.Principal;

@Service
public interface MerchantService extends UserDetailsService {
   
	Merchant saveMerchant(Merchant merchant);
	Merchant findByStoreName(String storeName);
	Merchant findByMerchantId(String userId);
	Merchant findByUsername(String username);
	Merchant findByPhone(String phone);
    List<Merchant> findAllMerchants();  
    boolean isMerchantPhoneUnique(String phone);  
    boolean isStoreNameUnique(String storeName);  
    boolean isUsernameUnique(String username);
    Merchant updateMerchantVisibility(String userId, String newVisibility);
    List<Merchant> findMerchantsVisibleToCustomer(String userId);
	List<Merchant> findMerchantsVisibleToMerchant(String userId);
    List<Merchant> findOnlineMerchantListVisibleToCustomer(String userId);
	List<Merchant> findOnlineMerchantListVisibleToMerchant(String userId);
    Merchant updateProfilePhoto(String userId, String storePhoto); 
    Merchant updateStoreBanner(String userId, String storeBanner); 
    Merchant updateStoreTagLine(String userId, String storeTagLine);
	void updateMerchantLocation(String userId, String location ,String lat, String lng);
	void decrementMerchantNotifications(String userId);
	void incrementMerchantNotifications(String userId);  
	Merchant setMerchantUserDetailsToModel(HttpServletRequest model, Principal princiapl);
	void setVisibleMerchantsToSession(String userId, HttpServletRequest model);
	Merchant setMerchantStoreDataToModel(HttpServletRequest model, String username);
		
    
}