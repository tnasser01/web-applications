package com.yanya.springmvc.service;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.yanya.springmvc.dao.CurrentMerchantStatusDao;
import com.yanya.springmvc.dao.MerchantDao;
import com.yanya.springmvc.dao.MerchantStatusDao;
//import com.yanya.springmvc.model.CurrentMerchantStatus;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.MerchantStatus;
import com.yanya.springmvc.model.MerchantStatusForm;
import com.yanya.springmvc.service.MerchantStatusService;
import java.lang.Integer;

@Service("merchantStatusService")
@Transactional
public class MerchantStatusServiceImpl implements MerchantStatusService {
 
    @Autowired
    private MerchantStatusDao dao;
    
    @Autowired
    private MerchantDao merchantDao;
    
//    @Autowired 
//    private CurrentMerchantStatusDao currentStatusDao;
    
    
     
    public MerchantStatus saveMerchantStatus(MerchantStatus status) {
    	MerchantStatus merch = dao.saveMerchantStatus(status);
    	Merchant merchant = merchantDao.updateMerchantStatus(status.getUserId(), status.getAngelStatus());
    	return merch;

    }
    
    public MerchantStatus findMerchantStatusByMerchantId(String userId) {
        return dao.findMerchantStatusByMerchantId(userId);
    }
    
//    public CurrentMerchantStatus updateCurrentMerchantStatus(CurrentMerchantStatus currentStatus) {
//    	return currentStatusDao.updateCurrentStatus(currentStatus);
//    }

//    public List<CurrentMerchantStatus> findAllCurrentMerchantStatuses(){
//    	return currentStatusDao.findAllCurrentMerchantStatuses();
//    }
 

 
 
}
