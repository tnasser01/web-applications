package com.yanya.springmvc.controller;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.ui.Model;


import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.service.CustomerService;
import com.yanya.springmvc.service.AppointmentService;
import com.yanya.springmvc.service.CartItemService;
import com.yanya.springmvc.service.MerchantService;
import com.yanya.springmvc.service.MessageService;
import com.yanya.springmvc.service.NotificationService;
import com.yanya.springmvc.service.ProductService;
import com.twilio.notifications.domain.twilio.Client;
import com.twilio.notifications.domain.twilio.CustomerClient;
import com.yanya.springmvc.exception.SessionExpiredException;
import com.yanya.springmvc.model.Appointment;
import com.yanya.springmvc.model.AppointmentRequest;
import com.yanya.springmvc.model.CartItem;
import com.yanya.springmvc.model.Appointment;
import com.yanya.springmvc.model.AppointmentItem;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.MerchantControlMenuForm;
import com.yanya.springmvc.model.Notification;
import com.yanya.springmvc.model.Product;

@Controller
@RequestMapping("/")
public class AppointmentController {
    
    @Autowired
    AppointmentService appointmentService;
    
    @Autowired
    CartItemService cartItemService;
    
    @Autowired
    MessageService messageService;
       
    @Autowired
    CustomerService customerService;
        
    @Autowired
    MerchantService merchantService;
    
    @Autowired
    NotificationService notificationService;

    @Autowired
    MessageSource messageSource;
	
	@RequestMapping(value="/checkout")
	public String submitAppointmentRequest(@Valid AppointmentRequest appointmentRequest, Errors errors, Principal principal,BindingResult result, RedirectAttributes model, HttpServletRequest request, HttpServletResponse response){
		customerService.setCustomerUserDetailsToModel(request, principal);
		//1. Store AppointmentRequest in DB
		appointmentService.saveNewAppointmentRequest(appointmentRequest);
			
		//2. Pull Cart Items from DB
		List<CartItem> cartItems = cartItemService.findItemsByUserId(appointmentRequest.getUserId());
		
		System.out.println("***#1***");
		System.out.println("All cart items in this appointment Request:");
		for(CartItem c: cartItems){
			System.out.println(c.getProductName());
		}
		System.out.println();
		
		//3. Create new appointments
		Map <String, String> uniqueMerchants = new HashMap<String, String>();
		for(CartItem c: cartItems){
			if(!uniqueMerchants.containsKey(c.getMerchantId())){
				uniqueMerchants.put(c.getMerchantId(), c.getStoreName());
			}
		}
		
		Map <String, String> merchantPhones = new HashMap<String, String>();
		for(Map.Entry<String, String> merchant: uniqueMerchants.entrySet()){
			Merchant m = merchantService.findByMerchantId(merchant.getKey());
			merchantPhones.put(merchant.getKey(), m.getPhone());
			System.out.println("merchant userid: " + merchant.getKey());
			System.out.println("merchant phone: " + m.getPhone());
			
			
		
		}

		List<Appointment> appointments = appointmentService.createNewAppointments(appointmentRequest, uniqueMerchants, merchantPhones, cartItems);
		
		//4. NOTIFY MERCHANTS
			//Create new Message for merchant inbox
			//Send message notification 
		for(Appointment appt : appointments){
			messageService.sendNewAppointmentMessage(appt, request);
			Notification notification = notificationService.createNewAppointmentNotification(appt);
			
			String phone = "+1" + appt.getMerchantPhone();
			System.out.println(phone);
			new Client().sendMessage(phone, notification.getNotificationText());
		}
		
		//5. NOTIFY CUSTOMER 
			messageService.sendAppointmentRequestSubmissionConfirmation(appointmentRequest, appointments, cartItems, request);
			Notification notification = notificationService.createAppointmentRequestSubmissionConfirmationNotification(appointmentRequest, appointments);
			//messageService.incrementCustomerNotifications(appointmentRequest.getUserId());
			String phone = "+1" + appointmentRequest.getPhone();
			new CustomerClient().sendMessage(phone, notification.getNotificationText());
			String successMsg = "Buzzzz.  Your appointment request has been recieved.";
			
			model.addAttribute("successMsg", successMsg);
		
		
		//6.Empty cart
		Integer decrementNumber  = 0;
		for(CartItem ci: cartItems){
			decrementNumber = decrementNumber + ci.getQuantity();
		}
		cartItemService.emptyCart(appointmentRequest.getUserId(), request, decrementNumber );
		//SHOW ACTIVITY PAGE
		//7. Return customer activity page with confirmation msg as latest msg.
		return "redirect:/inbox";		
	}
	
	@RequestMapping(value="/sell/updateApptStatus/{appointmentId}/{status}")
	public String updateAppointmentStatus(@PathVariable("appointmentId") String appointmentId, @PathVariable("status") String status, Principal principal, HttpServletRequest request, HttpServletResponse response){
		try{
				merchantService.setMerchantUserDetailsToModel(request, principal);
				appointmentService.updateAppointmentStatus(appointmentId, status);
				
				Appointment appt = appointmentService.findAppointmentByAppointmentId(appointmentId);
				if(status.equals("confirmed")){
					appointmentService.updateTimeAccepted(appointmentId, appointmentService.parseDate(new Date()));
					Integer queueNumber = appointmentService.placeConfirmedAppointmentInQueue(appointmentService.findAppointmentByAppointmentId(appointmentId));
					appt.setQueueNumber(queueNumber);
					messageService.sendCustomerAppointmentAcceptedMessage(appt, request);
					
					//send customer text to tell them appt confirmed
					Notification notification = notificationService.createAppointmentConfirmationNotification(appt);
					String phone = "+1" + appt.getCustomerPhone();
					System.out.println(phone);
					new CustomerClient().sendMessage(phone, notification.getNotificationText());
			
				}else if(status.equals("rejected")){
					appointmentService.updateTimeRejected(appointmentId, appointmentService.parseDate(new Date()));
					messageService.sendCustomerAppointmentRejectedMessage(appt, request);
				}else if(status.equals("cancelled")){
					appointmentService.updateTimeRejected(appointmentId, appointmentService.parseDate(new Date()));
					appointmentService.removeAppointmentFromQueue(appointmentService.findAppointmentByAppointmentId(appointmentId));
					messageService.sendCustomerAppointmentRejectedMessage(appt, request);
				}else if(status.equals("completed")){
					appointmentService.updateTimeCompleted(appointmentId, appointmentService.parseDate(new Date()));
					messageService.sendCustomerAppointmentCompletedMessage(appt, request);
					appointmentService.removeAppointmentFromQueue(appointmentService.findAppointmentByAppointmentId(appointmentId));
				}
			}catch(ClassCastException cce){
			System.out.println("not logged in");
			return "merchantMain";
			}
			return "redirect:/sell/appointment/" + appointmentId;
	}
		
	


	
	
}
    