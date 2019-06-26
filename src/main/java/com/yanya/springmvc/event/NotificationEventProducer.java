package com.yanya.springmvc.event;

import java.util.Date;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.yanya.springmvc.model.Impression;
import com.yanya.springmvc.model.LikeMessage;
import com.yanya.springmvc.model.ProductImpression;
import com.yanya.springmvc.service.UserPhotoService;
import com.yanya.springmvc.service.CustomerService;
import com.yanya.springmvc.service.LikeMessageService;
import com.yanya.springmvc.service.MerchantService;
import com.yanya.springmvc.event.NotificationHeartEvent;
import com.yanya.springmvc.model.Connection;
@Component 
public class NotificationEventProducer{
	
    @Autowired
    private ApplicationEventPublisher publisher;
    
    @Autowired
    LikeMessageService likeMessageService;
    
    @Autowired
    CustomerService customerService;
    
    @Autowired
    MerchantService merchantService;
	
	public void createNotification(HttpServletRequest request, String updateType, String userId, String userType){
		publisher.publishEvent(new NotificationEventCartItem(request, updateType, userId, userType));
		System.out.println("********** NEW EVENT CREATED*********");
	}
	
	public void createLikeNotification(Impression impression, HttpServletRequest request, String updateType, String userId, String userType){
		String msgText = impression.getUsername() + " has liked your photo.";
		LikeMessage likeMsg = likeMessageService.findLikeMessageByImpressionId(impression.getImpressionId());
		
		if(likeMsg==null){
			likeMsg = new LikeMessage(impression.getUserId(),impression.getUsername(), impression.getUserType(), userId, impression.getUserPhoto().getCustomer().getUsername(), userType, msgText, impression.getUserPhoto().getImagePath(), new Date(), impression.getImpressionId(), null, null);
		}
		
 		publisher.publishEvent(new LikeMessageNotificationEvent(likeMsg, request, updateType, userId, userType));
		System.out.println("********** NEW LIKE MSG EVENT CREATED*********");
	}
	
	public void createHeartNotification(HttpServletRequest request, String updateType, String userId, String userType){
		publisher.publishEvent(new NotificationHeartEvent(request, updateType, userId, userType));
		System.out.println("********** NEW HEART EVENT CREATED*********");
	}
	
	public void createProductLikeNotification(ProductImpression impression, HttpServletRequest request, String updateType, String userId, String userType){
		String msgText = impression.getUsername() + " has liked your product " + impression.getProduct().getProductName();
		LikeMessage likeMsg = likeMessageService.findLikeMessageByProductImpressionId(impression.getProductImpressionId());
		if(likeMsg==null){
			System.out.println("no previous likeMsg found so creating a new one");
			likeMsg = new LikeMessage(impression.getUserId(),impression.getUsername(), impression.getUserType(), userId, impression.getProduct().getMerchant().getUsername(), userType, msgText, impression.getProduct().getImagePath1(), new Date(), null, impression.getProductImpressionId(), null);
		} 
		System.out.println("likeMsg sender userId: " + impression.getUserId() );
		System.out.println("likeMsg sender username: " + impression.getUsername() );
		System.out.println("likeMsg sender userType: " + impression.getUserType() );
		System.out.println("likeMsg recipient userId: " + userId );
		System.out.println("likeMsg recipient username: " + impression.getProduct().getMerchant().getStoreName());
		System.out.println("likeMsg recipient userType: " + userType );
		System.out.println("likeMsg msgText: " + msgText );
		System.out.println("likeMsg image path: " + impression.getProduct().getImagePath1() );
		System.out.println("likeMsg recieve time: " + likeMsg.getRecieveTime());
	
		//add or remove the like msg
		publisher.publishEvent(new LikeMessageNotificationEvent(likeMsg, request, updateType, userId, userType));
		System.out.println("********** NEW LIKE MSG EVENT CREATED*********");
	}
	
	public void createConnectionAcceptNotification(Connection conn, HttpServletRequest request, String updateType, String userId, String userType){
		String msgText = conn.getRequesteeName() + " has accepted your connection request";
		String requesteeImagePath = conn.getRequesteeType().equals("merchant")? merchantService.findByMerchantId(conn.getRequesteeId()).getProfilePhoto(): customerService.findCustomerByCustomerId(conn.getRequesteeId()).getProfilePhoto();
		LikeMessage likeMsg = new LikeMessage(conn.getRequesteeId(), conn.getRequesteeName(), conn.getRequesteeType(), conn.getRequesterId(),conn.getRequesterName(), conn.getRequesterType(), msgText, requesteeImagePath, new Date(), null, null, null);
		
 		publisher.publishEvent(new LikeMessageNotificationEvent(likeMsg, request, updateType, userId, userType));
		System.out.println("********** NEW CONN ACCEPT EVENT CREATED*********");
	}
	
}