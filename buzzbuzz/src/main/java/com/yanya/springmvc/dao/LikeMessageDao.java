package com.yanya.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import com.yanya.springmvc.model.LikeMessage;
 
public interface LikeMessageDao {
	 
	@Autowired
	LikeMessage saveNewLikeMessage(LikeMessage message);
	
	@Autowired
	LikeMessage findLikeMessageByLikeMessageId(String likeMessageId);

	@Autowired
	List<LikeMessage> findLikeMessagesByMerchantId(String userId);
	
	@Autowired
	List<LikeMessage> findLikeMessagesByCustomerId(String userId);

	@Autowired
	void deleteLikeMessage(LikeMessage message);
	
	@Autowired
	LikeMessage findLikeMessageByImpressionId(String impressionId);
    
    @Autowired
    LikeMessage findLikeMessageByProductImpressionId(String productImpressionId);
    
	@Autowired
	Integer readLikeMessages(String userId, String userType, Date date);
}
