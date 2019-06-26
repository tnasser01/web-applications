package com.yanya.springmvc.service;
 
import java.util.Date;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanya.springmvc.dao.CustomerDao;
import com.yanya.springmvc.dao.MerchantDao;
import com.yanya.springmvc.dao.NotificationDao;
import com.yanya.springmvc.model.Appointment;
import com.yanya.springmvc.model.AppointmentRequest;
import com.yanya.springmvc.model.Connection;
import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.Notification;
import com.yanya.springmvc.service.NotificationService;
import com.yanya.springmvc.event.NotificationEventProducer;
import java.lang.Integer;

@Service("notificationService")
@Transactional
public class NotificationServiceImpl implements NotificationService {
 
    @Autowired
    private NotificationDao notificationDao;
    
    @Autowired
    private CustomerDao customerDao;
    
    @Autowired
    private MerchantDao merchantDao;
    
    
    public Notification createNewAppointmentNotification(Appointment appointment){
    	String notificationText = "You have a new appointment request from " + appointment.getCustomerName() + " at " + appointment.getSearchTextField() +  ". Type " + appointment.getAcceptId() + " to confirm or " + appointment.getRejectId() + " to reject this apppointment." ;
    	Notification notification = new Notification(appointment.getMerchantId(), notificationText, new Date(), null);
    	return notificationDao.saveNewNotification(notification);
    } 
    
    
    public Notification createAppointmentConfirmationNotification(Appointment appointment){
    	 int waitTime = appointment.getQueueNumber() * 30;

    	 String notificationText = "Good news...your appointment is confirmed.  Your angel will arrive in approx " + waitTime + " mins.";
       	 Notification notification = new Notification(appointment.getMerchantId(), notificationText, new Date(), null);	
       	 return notificationDao.saveNewNotification(notification);
    }	
    
    public Notification createAppointmentRequestSubmissionConfirmationNotification(AppointmentRequest appointmentRequest, List<Appointment> appointments ){
	   String notificationText = "Thank you.  Your appointment request was recieved.  Your angel will confirm or deny within 5 minutes.";
	   Notification notification = new Notification(appointmentRequest.getUserId(), notificationText, new Date(), null);   
	   return notificationDao.saveNewNotification(notification);
   
   }
    
	public Notification createConnectionRequestNotification(Connection conn ){
		Notification notification  = null;
		if(conn.getRequesterType().equals("customer")){
			   Customer customer = customerDao.findByCustomerId(conn.getRequesterId());
			   String notificationText = customer.getUsername() + " has requested to connect with you.";
			   notification = new Notification(conn.getRequesteeId(), notificationText, new Date(), null);   
			   
		}else if(conn.getRequesterType().equals("merchant")){
			   Merchant merchant = merchantDao.findByMerchantId(conn.getRequesterId());
			   String notificationText = merchant.getUsername() + " has requested to connect with you.";
			   notification = new Notification(conn.getRequesteeId(), notificationText, new Date(), null);   
			 
		}
		return notificationDao.saveNewNotification(notification);
	}
 
 
 
 
}