package com.yanya.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yanya.springmvc.model.LikeMessage;
import com.yanya.springmvc.model.Appointment;
import com.yanya.springmvc.model.AppointmentRequest;
//import com.yanya.springmvc.model.Cart;
import com.yanya.springmvc.model.CartItem;

import java.lang.Integer;

@Service
public interface LikeMessageService {
	
	LikeMessage saveNewLikeMessage(LikeMessage msg, HttpServletRequest request);
	
	void deleteLikeMessage(LikeMessage msg, HttpServletRequest request);
	
	List<LikeMessage> findLikeMessagesByMerchantId(String userId);
	LikeMessage findLikeMessageByLikeMessageId(String likeMessageId);	

	List<LikeMessage> findLikeMessagesByCustomerId(String userId);

     LikeMessage findLikeMessageByImpressionId(String impressionId);
    
    LikeMessage findLikeMessageByProductImpressionId(String productImpressionId);
    
    void readLikeMessages(String userId, String userType, Date date,  HttpServletRequest model);
}