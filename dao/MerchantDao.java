package com.yanya.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.yanya.springmvc.model.Merchant;

@Repository
public interface MerchantDao {
 
	@Autowired
	Merchant saveMerchant(Merchant merchant);
	
	@Autowired
  List<Merchant> findAllMerchants();

	@Autowired
	Merchant findByStoreName(String storeName);
	
	@Autowired
	Merchant findByUsername(String username);
	
	@Autowired
	Merchant findByMerchantId(String userId);
	
	@Autowired
  Merchant findByPhone(String phone);
	
	@Autowired
	void updateMerchantLocation(String userId, String location, String lat, String lng);
	
	@Autowired
	Merchant updateMerchantVisibility(String userId, String newVisibility);
	
	@Autowired
	Merchant updateMerchantStatus(String userId, String status);
	
	@Autowired
	List<Merchant> findMerchantsByVisibility(String visibility);
	
	@Autowired
	List<Merchant> findMerchantsByVisibilityAndStatus(String visibility, String status);
	
	@Autowired
	List<Merchant> findMerchantsByVisibilityAndStatus(String visibility, String status, List<String> merchantIds);
	
	@Autowired
	List<Merchant> findMerchantsByMerchantIds(List<String> merchantIds);
	
	@Autowired
  Merchant updateProfilePhoto(String userId, String profilePhoto);
	
	@Autowired
  Merchant updateStoreBanner(String userId, String storeBanner);
	
	@Autowired
  Merchant updateStoreTagLine(String userId, String storeTagLine);
	
	@Autowired
	Merchant incrementNotifications(String userId);
	
	@Autowired
	Merchant decrementNotifications(String userId);
}