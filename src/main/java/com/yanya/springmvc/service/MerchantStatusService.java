package com.yanya.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

//import com.yanya.springmvc.model.CurrentMerchantStatus;
import com.yanya.springmvc.model.MerchantStatus;
import com.yanya.springmvc.model.MerchantStatusForm;

import java.lang.Integer;

@Service
public interface MerchantStatusService {
	
	MerchantStatus saveMerchantStatus(MerchantStatus status);
	MerchantStatus findMerchantStatusByMerchantId(String userId);
//	List<CurrentMerchantStatus> findAllCurrentMerchantStatuses();

}
