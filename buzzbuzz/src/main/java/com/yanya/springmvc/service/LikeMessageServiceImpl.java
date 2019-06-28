package com.yanya.springmvc.service;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanya.springmvc.dao.LikeMessageDao;
import com.yanya.springmvc.event.LikeMessageNotificationEvent;
import com.yanya.springmvc.event.NotificationEventProducer;
import com.yanya.springmvc.model.LikeMessage;
import com.yanya.springmvc.model.Appointment;
import com.yanya.springmvc.model.AppointmentItem;
import com.yanya.springmvc.model.AppointmentRequest;
import com.yanya.springmvc.model.CartItem;
import com.yanya.springmvc.service.LikeMessageService;

import com.yanya.springmvc.model.ReadTimeForm;

import java.lang.Integer;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service("likeMessageService")
@Transactional
public class LikeMessageServiceImpl implements LikeMessageService {
 
    @Autowired
    private LikeMessageDao messageDao;
    
    @Autowired
    private NotificationEventProducer producer;
  
    public LikeMessage saveNewLikeMessage(LikeMessage likeMsg, HttpServletRequest model) {
    	
    	System.out.println(likeMsg.getLikeMessageId());
    	System.out.println(likeMsg.getSenderName());
    	System.out.println(likeMsg.getRecipientName());
    	System.out.println(likeMsg.getSenderId());
    	System.out.println(likeMsg.getRecipientId());
    	System.out.println(likeMsg.getMessageText());
    	LikeMessage savedLikeMsg =  messageDao.saveNewLikeMessage(likeMsg);
    	return savedLikeMsg;
    }
    
    public void deleteLikeMessage(LikeMessage likeMsg, HttpServletRequest model) {
    	
    	System.out.println(likeMsg.getLikeMessageId());
    	System.out.println(likeMsg.getSenderName());
    	System.out.println(likeMsg.getRecipientName());
    	System.out.println(likeMsg.getSenderId());
    	System.out.println(likeMsg.getRecipientId());
    	System.out.println(likeMsg.getMessageText());
    	messageDao.deleteLikeMessage(likeMsg);
     }
    
    public List<LikeMessage> findLikeMessagesByMerchantId(String userId){
    	return messageDao.findLikeMessagesByMerchantId(userId);
    }
    
    
    public List<LikeMessage> findLikeMessagesByCustomerId(String userId){
    	return messageDao.findLikeMessagesByCustomerId(userId);
    }
    
    public LikeMessage findLikeMessageByLikeMessageId(String likeMessageId){
    	return messageDao.findLikeMessageByLikeMessageId(likeMessageId);
    }
 
    public LikeMessage findLikeMessageByImpressionId(String impressionId){
    	return messageDao.findLikeMessageByImpressionId(impressionId);
    }
    
    
    public LikeMessage findLikeMessageByProductImpressionId(String productImpressionId){
    	return messageDao.findLikeMessageByProductImpressionId(productImpressionId);
    }
    
    public void readLikeMessages(String userId, String userType, Date date, HttpServletRequest model){
    	Integer numOfMsgRead = messageDao.readLikeMessages(userId, userType, date);
    	
    	//for(int i =0;  i< numOfMsgRead; i++){
    	//	producer.createNotification(model, "decrement", userId, userType);
    	//}	
    }
     
 
}