package com.yanya.springmvc.controller;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.ui.Model;


import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.service.AppointmentService;
import com.yanya.springmvc.service.CustomerService;
import com.yanya.springmvc.service.NotificationService;
import com.yanya.springmvc.service.MerchantService;
import com.yanya.springmvc.service.ProductService;
import com.twilio.twiml.Body;
import com.twilio.notifications.domain.twilio.Client;
import com.twilio.notifications.domain.twilio.CustomerClient;
import com.twilio.twiml.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;
import com.yanya.springmvc.exception.SessionExpiredException;
import com.yanya.springmvc.model.Appointment;
import com.yanya.springmvc.model.AppointmentRequest;
import com.yanya.springmvc.model.Notification;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.Product;
import javax.persistence.NonUniqueResultException;


@RestController
@RequestMapping("/")
public class NotificationController {
    
    @Autowired
    CustomerService customerService;
    
    @Autowired
    MerchantService merchantService;

    @Autowired
    NotificationService notificationService;
    
    @Autowired
    AppointmentService appointmentService;
    
    @Autowired
    MessageSource messageSource;
    
    @Autowired
    protected AuthenticationManager authenticationManager;

	@RequestMapping(value="/merchantSms")
    public void processIncomingMerchantText(HttpServletRequest model, HttpServletResponse response) throws IOException {
    	String incomingText = model.getParameter("Body");
    	System.out.println("contents of merchant response to app: " + incomingText);
    	String outgoingText = "";
    	Appointment acctAppt = null;
    	Appointment rejAppt = null;
    	Appointment completeAppt = null;
    	
    	try{
    		acctAppt = appointmentService.findByAcceptId(incomingText); 
    	}catch(NonUniqueResultException nu){ System.out.println("Non unique result for findByAcceptId");}
    	try{
    		rejAppt = appointmentService.findByRejectId(incomingText);
    	}catch(NonUniqueResultException nu){ System.out.println("Non unique result for findByRejectId");}
    	try{
    		completeAppt = appointmentService.findByCompleteId(incomingText);
    	}catch(NonUniqueResultException nu){ System.out.println("Non unique result for findByCompleteId");}
    	if(acctAppt!=null){
    		//change appointment status to confirmed
    			acctAppt.setAppointmentStatus("confirmed");
    			appointmentService.updateAppointmentStatus(acctAppt.getAppointmentId(), "confirmed");
    		//assign appointment a queue number
    			 appointmentService.placeConfirmedAppointmentInQueue(acctAppt);
    			 Appointment confirmedAppt = null;
    			 try{
    				confirmedAppt = appointmentService.findAppointmentByAppointmentId(acctAppt.getAppointmentId());
    			 }catch(NonUniqueResultException nu){ System.out.println("Non unique result for findApptByApptId");}
    			//notify merchant that the appointment is confirmed.
     			outgoingText = "You have just confirmed your appointment at " + confirmedAppt.getSearchTextField() + ".  Text " + confirmedAppt.getCompleteId() + " to complete this appointment." ;
    		
     			//notify customer that their appointment is confirmed
    			Notification confirmNotification = notificationService.createAppointmentConfirmationNotification(confirmedAppt);
    			String phone = "+1" + acctAppt.getCustomerPhone();
    			System.out.println(phone);
    			new CustomerClient().sendMessage(phone, confirmNotification.getNotificationText());
    	}else if(rejAppt!=null){
    		//change appointment status to rejected
    			rejAppt.setAppointmentStatus("rejected");
    			appointmentService.updateAppointmentStatus(rejAppt.getAppointmentId(), "rejected");
    		//notify customer that their angel cannot make this appointment
    			outgoingText = "Unfortunately " +  rejAppt.getStoreName() + " cannot accept your appointment at this time.";
    			String phone = "+1" + rejAppt.getCustomerPhone();
    			new CustomerClient().sendMessage(phone, outgoingText);	   		
       }else if(completeAppt!=null){
    	   //complete the appointment and remove it from the queue
    	   		completeAppt.setAppointmentStatus("completed");
    			appointmentService.updateAppointmentStatus(completeAppt.getAppointmentId(), "completed");
    	   		appointmentService.removeAppointmentFromQueue(completeAppt);
    	   	
    	   		//text customer that the appt was completed.	   	   		
    	   		outgoingText = "Thank you for using Buzzbuzz.  See you again soon! ";
    	   		String phone = "+1" + completeAppt.getCustomerPhone();
    	   		new CustomerClient().sendMessage(phone, outgoingText);	
    	   		//thank the customer for the completed appointment
    	   //send a request to review this weed 24 hours later.
       
       }else{
    	    outgoingText = "response not recognized. try again.";
       }
    	Message sms = new Message.Builder().body(new Body(outgoingText)).build();
    	MessagingResponse twiml = new MessagingResponse.Builder().message(sms).build();
    	response.setContentType("application/xml");
    		try {
    		      response.getWriter().print(twiml.toXml());
    		    } catch (TwiMLException e) {
    		      e.printStackTrace();
    		    } 
    	
    	 
    }
    



	
	
	

	
}
    