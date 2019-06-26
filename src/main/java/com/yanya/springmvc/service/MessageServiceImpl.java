package com.yanya.springmvc.service;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanya.springmvc.dao.MessageDao;
import com.yanya.springmvc.model.Message;
import com.yanya.springmvc.model.ReadTimeForm;
import com.yanya.springmvc.model.Appointment;
import com.yanya.springmvc.model.AppointmentItem;
import com.yanya.springmvc.model.AppointmentRequest;
import com.yanya.springmvc.model.CartItem;
import com.yanya.springmvc.service.MessageService;
import com.yanya.springmvc.event.NotificationEventProducer;
import java.lang.Integer;

@Service("messageService")
@Transactional
public class MessageServiceImpl implements MessageService {
 
    @Autowired
    private MessageDao messageDao;
    
    @Autowired
    private NotificationEventProducer producer;
   
    
    public Message sendNewAppointmentMessage(Appointment appointment, HttpServletRequest request) {
    	String msg = "You have a new appointment request. <br><br> "
    				+ " User:  " + appointment.getCustomerName() + "<br>"
    				+ " Address: " + appointment.getSearchTextField() + "<br>" 
    				+  " Time Recieved: " + appointment.getTimeReceived() +  "<br> "
    				+ "<br> "
    				+  "<a href='/sell/appointment/" + appointment.getAppointmentId() + "' class='inbox' >View Appointment</a>";
    	Message message = new Message("system", appointment.getStoreName(), "system", appointment.getMerchantId(),  "system", "merchant", "New Appointment Request", msg, new Date(), null );
    	System.out.println(message.getMessageId());
    	System.out.println(message.getSenderName());
    	System.out.println(message.getRecipientName());
    	System.out.println(message.getSenderId());
    	System.out.println(message.getRecipientId());
    	System.out.println(message.getMessageText());
    	System.out.println(message.getRecieve_time());
    	System.out.println(message.getRead_time());
    	
    	Message savedMsg = messageDao.saveNewMessage(message);
		
    	producer.createHeartNotification(request, "increment", message.getRecipientId(), "merchant");
    	
    	return  savedMsg;
    }
    
    public Message sendAppointmentRequestSubmissionConfirmation(AppointmentRequest appointmentRequest, List<Appointment> appointments, List<CartItem> cartItems, HttpServletRequest request){
    	String msg = "Your appointment request #" + appointmentRequest.getAppointmentRequestId() + " has been received.<br>" + 
    				"You have requested the following appointments: <br>" ;
    	
    	StringBuffer sf = new StringBuffer(msg);
    	for(Appointment a: appointments){
    		sf.append(a.getStoreName() + "<br>");
    		for(CartItem ci: cartItems){
    			if(ci.getCustomer().getUserId().equals(a.getMerchantId()))
    			 sf.append(" &nbsp;&nbsp;&nbsp;" + ci.getProductName() + "<br>");
    		}
    		
    	}
    	sf.append("<br>");
		sf.append("Please give your angel 5 minutes to confirm or deny this appointment.");
    	msg = sf.toString();
    	
    	Message message = new Message("system", appointmentRequest.getCustomerName(), "system", appointmentRequest.getUserId(), "system", "customer", "New Appointment Request Sent", msg, new Date(), null );
    	System.out.println("Message messageID: " + message.getMessageId());
    	System.out.println("Message messageID: " + message.getSenderName());
    	System.out.println("Message messageID: " + message.getRecipientName());
    	System.out.println("Message messageID: " + message.getSenderId());
    	System.out.println("Message messageID: " + message.getRecipientId());
    	System.out.println("Message messageID: " + message.getMessageText());
    	System.out.println("Message messageID: " + message.getRecieve_time());
    	System.out.println("Message messageID: " + message.getRead_time());
    	
    	Message savedMsg =  messageDao.saveNewMessage(message);
    	producer.createHeartNotification(request, "increment", message.getRecipientId(), "customer");
    	
    	return savedMsg;
    }
    
    public Message sendCustomerAppointmentAcceptedMessage(Appointment appointment, HttpServletRequest request){
    	Integer waitTime =  appointment.getQueueNumber() * 30;
    	
    	String msg = "Rejoice!<br><br> Your appointment request was confirmed.  "
    			+ "Your angel is on the way! <br><br> "
    			+ "Your estimated wait time is " + waitTime + " mins.<br><br>"
    			   + "To cancel is appointment, <a href='/appointment/" + appointment.getAppointmentId() + "/cancel' class='inbox' >click here</a>";
    	
    	
    	Message message = new Message("system", appointment.getCustomerName(), "system", appointment.getUserId(), "system", "customer", "Appointment Confirmed", msg, new Date(), null );
    	System.out.println("Message messageID: " + message.getMessageId());
    	System.out.println("Message messageID: " + message.getSenderName());
    	System.out.println("Message messageID: " + message.getRecipientName());
    	System.out.println("Message messageID: " + message.getSenderId());
    	System.out.println("Message messageID: " + message.getRecipientId());
    	System.out.println("Message messageID: " + message.getMessageText());
    	System.out.println("Message messageID: " + message.getRecieve_time());
    	System.out.println("Message messageID: " + message.getRead_time());
    	
    	Message savedMsg =  messageDao.saveNewMessage(message);
    	producer.createHeartNotification(request, "increment", message.getRecipientId(), "customer");
    	
    	return savedMsg;
    }
    
    public Message sendCustomerAppointmentRejectedMessage(Appointment appointment, HttpServletRequest request){
    	String msg = "Sorry,<br><br>" + appointment.getStoreName() + " is unable to accept your appointment at this time.<br><br>";

    	
    	
    	Message message = new Message("system", appointment.getCustomerName(), "system", appointment.getUserId(), "system", "customer", "Appointment Rejected", msg, new Date(), null );
    	System.out.println("Message messageID: " + message.getMessageId());
    	System.out.println("Message messageID: " + message.getSenderName());
    	System.out.println("Message messageID: " + message.getRecipientName());
    	System.out.println("Message messageID: " + message.getSenderId());
    	System.out.println("Message messageID: " + message.getRecipientId());
    	System.out.println("Message messageID: " + message.getMessageText());
    	System.out.println("Message messageID: " + message.getRecieve_time());
    	System.out.println("Message messageID: " + message.getRead_time());
    	
    	Message savedMsg =  messageDao.saveNewMessage(message);
    	producer.createHeartNotification(request, "increment", message.getRecipientId(), "customer");
    	
    	return savedMsg;
    }
    
    public Message sendCustomerAppointmentCompletedMessage(Appointment appointment, HttpServletRequest request){
    	String msg = "Thank you for using BuzzBuzz! We hope you enjoy your products. <br><br>Please come again soon!";

    	
    	
    	Message message = new Message("system", appointment.getCustomerName(), "system", appointment.getUserId(), "system", "customer", "Thank you for your order!", msg, new Date(), null );
    	System.out.println("Message messageID: " + message.getMessageId());
    	System.out.println("Message messageID: " + message.getSenderName());
    	System.out.println("Message messageID: " + message.getRecipientName());
    	System.out.println("Message messageID: " + message.getSenderId());
    	System.out.println("Message messageID: " + message.getRecipientId());
    	System.out.println("Message messageID: " + message.getMessageText());
    	System.out.println("Message messageID: " + message.getRecieve_time());
    	System.out.println("Message messageID: " + message.getRead_time());
    	
    	Message savedMsg =  messageDao.saveNewMessage(message);
    	producer.createHeartNotification(request, "increment", message.getRecipientId(), "customer");
    	
    	return savedMsg;
    }
    
    public Message sendAppointmentCancellationMessageToMerchant(Appointment appointment,  HttpServletRequest request) {
    	String msg = "Your upcoming appointment with " + appointment.getCustomerName() + " at " +  appointment.getSearchTextField() + " has been cancelled.  Click to view more details";
    	Message message = new Message("system", appointment.getStoreName(), "system", appointment.getMerchantId(),  "system", "merchant", "Appointment Cancellation", msg, new Date(), null );
    	System.out.println(message.getMessageId());
    	System.out.println(message.getSenderName());
    	System.out.println(message.getRecipientName());
    	System.out.println(message.getSenderId());
    	System.out.println(message.getRecipientId());
    	System.out.println(message.getMessageText());
    	System.out.println(message.getRecieve_time());
    	System.out.println(message.getRead_time());
    	Message savedMsg = messageDao.saveNewMessage(message);
    	//producer.createNotification(request, "increment", message.getRecipientId(), "merchant");
    	return  savedMsg;
    }
    
    public Message sendAppointmentCancellationMessageToCustomer(Appointment appointment, HttpServletRequest request) {
    	String msg = "Your upcoming appointment with " + appointment.getStoreName() + " at " +  appointment.getSearchTextField() + " has been cancelled.  Click to view more details";
    	Message message = new Message("system", appointment.getCustomerName(), "system", appointment.getUserId(), "Appointment Cancellation", msg, new Date(), null );
    	System.out.println(message.getMessageId());
    	System.out.println(message.getSenderName());
    	System.out.println(message.getRecipientName());
    	System.out.println(message.getSenderId());
    	System.out.println(message.getRecipientId());
    	System.out.println(message.getMessageText());
    	System.out.println(message.getRecieve_time());
    	System.out.println(message.getRead_time());
    	Message savedMsg = messageDao.saveNewMessage(message);
    	//producer.createNotification(request, "increment", message.getRecipientId(), "customer");
    	return  savedMsg;
    }
    
    public List<Message> findMessagesByMerchantId(String userId){
    	return messageDao.findMessagesByMerchantId(userId);
    }
    
    
    public List<Message> findMessagesByCustomerId(String userId){
    	return messageDao.findMessagesByCustomerId(userId);
    }
    
	public  Message findMessageByMessageId(String messageId){
		return messageDao.findMessageByMessageId(messageId);
		
	}
    
	public void deleteMessage(Message message){
		messageDao.deleteMessage(message);
	}
	
	public void deleteMessage(String messageId, HttpServletRequest request){
		Message message = messageDao.findMessageByMessageId(messageId);
		if(message.getRead_time()==null){
			producer.createHeartNotification(request, "decrement", message.getRecipientId(), message.getRecipientType());
		}
		messageDao.deleteMessage(message);
		
	}
	
	
    public void markMessageRead(ReadTimeForm form, HttpServletRequest request){   	
    	messageDao.markMessageRead(form);
    	producer.createHeartNotification(request, "decrement", form.getUserId(), form.getRecipientType());
    }
 
 
}