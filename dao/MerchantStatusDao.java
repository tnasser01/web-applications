package com.yanya.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.yanya.springmvc.model.MerchantStatus;
import com.yanya.springmvc.model.MerchantStatusForm;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.ZipCode;
 
public interface MerchantStatusDao {
 
	@Autowired
	MerchantStatus saveMerchantStatus(MerchantStatus status);
	
	@Autowired
    MerchantStatus findMerchantStatusByMerchantId(String userId);
	

}
