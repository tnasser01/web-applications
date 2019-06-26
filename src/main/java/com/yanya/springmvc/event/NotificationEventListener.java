package com.yanya.springmvc.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.yanya.springmvc.event.LikeMessageNotificationEvent;
import com.yanya.springmvc.event.NotificationHeartEvent;
import com.yanya.springmvc.model.Connection;
import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.LikeMessage;
import com.yanya.springmvc.model.Message;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.service.ConnectionService;
import com.yanya.springmvc.service.CustomerService;
import com.yanya.springmvc.service.LikeMessageService;
import com.yanya.springmvc.service.MerchantService;
import com.yanya.springmvc.service.MessageService;

@Component
public class NotificationEventListener{
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	MerchantService merchantService;
	
	@Autowired
	LikeMessageService likeMessageService;
	
	@Autowired
	ConnectionService connectionService;
	
	@Autowired
	MessageService inboxMessageService;
		

	
	@EventListener
	void updateLikeMessages(LikeMessageNotificationEvent event){
		
		System.out.println("********** inside update like message notification method*********");
		//save or delete the like message
		if(event.getUpdateType().equals("increment")){
			likeMessageService.saveNewLikeMessage(event.getLikeMessage(), event.getModel());			
		}else if(event.getUpdateType().equals("decrement")){
			LikeMessage prevMsg = likeMessageService.findLikeMessageByLikeMessageId(event.getLikeMessage().getLikeMessageId());
			if(prevMsg!=null)
				likeMessageService.deleteLikeMessage(prevMsg, event.getModel());
		}
		
		//get the new list of like messages
		List<LikeMessage> updatedLikeMessages = new ArrayList<LikeMessage>();
		if(event.getUserType().equals("customer")){
			updatedLikeMessages  = (ArrayList<LikeMessage>) likeMessageService.findLikeMessagesByCustomerId(event.getUserId()); 
		}else if(event.getUserType().equals("merchant")){
			updatedLikeMessages  = (ArrayList<LikeMessage>) likeMessageService.findLikeMessagesByMerchantId(event.getUserId()); 
		}
		
		//if the list is not null set it to session
		event.getModel().getSession().setAttribute("likeMsgs", updatedLikeMessages);
		System.out.println("updatedLikeMessages size: " + updatedLikeMessages.size());

	
	}
	
	@EventListener
	void checkIfHeartIconShouldBeUpdated(NotificationHeartEvent event){
		
		System.out.println("********** inside update heart message notification method*********");
		
		Boolean allAcked = false;
		
		if(event.getUserType().equals("merchant")){
			Merchant merchant = merchantService.findByMerchantId(event.getUserId());
			System.out.println("merchant is:  "  + merchant.getUserId());
			if(merchant!=null){
				List<LikeMessage> likeMsgs = likeMessageService.findLikeMessagesByMerchantId(merchant.getUserId());
				List<Message> inboxMsgs = inboxMessageService.findMessagesByMerchantId(merchant.getUserId());
				List<Connection> connectionRequests = connectionService.findConnectionRequestsByRequesteeId(merchant.getUserId());
				System.out.println("size of like msgs" + likeMsgs);
				System.out.println("size of inbox msgs" + inboxMsgs);
				System.out.println("size of conn reqs" + connectionRequests);
				allAcked = checkAllEventsAcknowledged(likeMsgs, inboxMsgs, connectionRequests, event.getModel());
				System.out.println("ALL ACKED STATUS IS: " +  allAcked);
			
			}
		}else if(event.getUserType().equals("customer")){
			Customer customer = customerService.findCustomerByCustomerId(event.getUserId());
			System.out.println("customer is:  "  + customer.getUserId());
			if(customer!=null){
				List<LikeMessage> likeMsgs = likeMessageService.findLikeMessagesByCustomerId(customer.getUserId());
				List<Message> inboxMsgs = inboxMessageService.findMessagesByCustomerId(customer.getUserId());
				List<Connection> connectionRequests = connectionService.findConnectionRequestsByRequesteeId(customer.getUserId());
				System.out.println("size of like msgs" + likeMsgs);
				System.out.println("size of inbox msgs" + inboxMsgs);
				System.out.println("size of conn reqs" + connectionRequests);
				allAcked = checkAllEventsAcknowledged(likeMsgs, inboxMsgs,connectionRequests, event.getModel());
				System.out.println("ALL ACKED STATUS IS: " +  allAcked);
			}
		}
		
		event.getModel().getSession().setAttribute("allAcked", allAcked);
		
	
	}
	
	//check if likes or inbox or  cart has data in it
	private Boolean checkAllEventsAcknowledged(List<LikeMessage> likeMsgs, List<Message> inboxMsgs, List<Connection> connectionRequests,HttpServletRequest model){

		
		model.getSession().setAttribute("navButtonSelected", "cart");
			
		Boolean allAcked = true;	
		
		for(LikeMessage lk:  likeMsgs){
			if(lk.getReadTime()==null){	
				allAcked = false;
				model.getSession().setAttribute("navButtonSelected", "love");
				System.out.println("unread like msgs(s) were found");
				return allAcked;
			}
		}
			
		for(Connection c:  connectionRequests){
			if(c.getReadTime()==null){	
				allAcked = false;
				model.getSession().setAttribute("navButtonSelected", "love");
				System.out.println("unread connection request(s) were found");
				return allAcked;
			}
		}
		
		for(Message m: inboxMsgs){
			if(m.getRead_time()==null){
				allAcked = false;
				model.getSession().setAttribute("navButtonSelected", "inbox");
				System.out.println("unread inbox msg(s) were found");
				return allAcked;
			}
		}
		
		
		return allAcked;
	}
		
	@EventListener
	void updateUserNotification(NotificationEventCartItem event){
		System.out.println("********** inside update user notification method*********");
		if(event.getUpdateType().equals("increment")  && event.getUserType().equals("merchant") ){
			merchantService.incrementMerchantNotifications(event.getUserId());			
			Merchant merch = (Merchant)event.getModel().getSession().getAttribute("merchant");
			if(merch!=null){
				merch.setNotifications(merch.getNotifications() + 1);
				event.getModel().getSession().setAttribute("merchant", merch);
			}
			System.out.println("incrementing merchant" + event.getUserId());
		}else if(event.getUpdateType().equals("increment")  && event.getUserType().equals("customer")){
			customerService.incrementCustomerNotifications(event.getUserId());
			Customer cust = (Customer)event.getModel().getSession().getAttribute("customer");
			if(cust !=null){
				cust.setNotifications(cust.getNotifications() + 1);
				event.getModel().getSession().setAttribute("customer", cust);
			}
			System.out.println("incrementing customer" + event.getUserId());
		}else if(event.getUpdateType().equals("decrement") && event.getUserType().equals("merchant")){
			
			merchantService.decrementMerchantNotifications(event.getUserId());	
			Merchant merch = (Merchant)event.getModel().getSession().getAttribute("merchant");
			if(merch!=null){
				merch.setNotifications(merch.getNotifications() - 1);
				event.getModel().getSession().setAttribute("merchant", merch);
			}
			System.out.println("decrementing merchant" + event.getUserId());
			
		}else if(event.getUpdateType().equals("decrement") && event.getUserType().equals("customer")){
			
			customerService.decrementCustomerNotifications(event.getUserId());
			Customer cust = (Customer)event.getModel().getSession().getAttribute("customer");
			if(cust !=null){
				System.out.println("&&&&&&&&&&&& the customer was found in session");
				
				
				cust.setNotifications(cust.getNotifications() - 1);
				event.getModel().getSession().setAttribute("customer", cust);
			}
			System.out.println("decrementing customer" + event.getUserId());
		}else{
			System.out.println("none of the options were chosen!");
			System.out.println("userType : " +  event.getUserType());
			System.out.println("userId : " +  event.getUserId());
			System.out.println("updateType : " +  event.getUpdateType());
			
		}
		
		
	}

	
}