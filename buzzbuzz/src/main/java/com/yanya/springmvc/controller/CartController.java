package com.yanya.springmvc.controller;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.yanya.springmvc.model.LikeMessage;
import com.yanya.springmvc.model.Connection;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.service.CustomerService;
import com.yanya.springmvc.service.LikeMessageService;
import com.yanya.springmvc.service.CartItemService;
import com.yanya.springmvc.service.MerchantStatusService;
import com.yanya.springmvc.model.MerchantStatus;
//import com.yanya.springmvc.service.CartService;
import com.yanya.springmvc.service.ConnectionService;
import com.yanya.springmvc.service.MerchantService;
import com.yanya.springmvc.service.ProductService;
import com.yanya.springmvc.service.MerchantStatusService;
import com.yanya.springmvc.service.AppointmentService;
import com.yanya.springmvc.service.MessageService;
import com.yanya.springmvc.event.NotificationEventProducer;
import com.yanya.springmvc.exception.SessionExpiredException;
import com.yanya.springmvc.model.Appointment;
import com.yanya.springmvc.model.AppointmentRequest;
//import com.yanya.springmvc.model.Cart;
import com.yanya.springmvc.model.CartItem;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.Message;
import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.ReadTimeForm;

@Controller
@RequestMapping("/")
public class CartController {
    
    @Autowired
    CustomerService customerService;

    @Autowired
    MerchantService merchantService;
    
    @Autowired
    CartItemService cartItemService;
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    MerchantStatusService merchantStatusService;
    @Autowired
    MessageService messageService;
    @Autowired
    LikeMessageService likeMessageService;
    @Autowired
    ConnectionService connectionService;
    @Autowired
    MessageSource messageSource;
    @Autowired
    NotificationEventProducer producer;
    @Autowired
    protected AuthenticationManager authenticationManager;
    
  @ExceptionHandler(HttpSessionRequiredException.class)
  @RequestMapping(value="/cart")
	public String showCart(HttpServletRequest model) throws SessionExpiredException {
    	  	
    Customer customer = null;
		try{
    		//set user in session
			customer = setCustomerUserDetailsToModel(model);
    }catch(ClassCastException cce){ 
    			System.out.println("User not logged in");
    			return "main";
    }
		
		if(customer==null){
			return "main";
		}
		//set inbox messages in session
		setInboxMsgsToSession(customer.getUserId(), model);
		
		//set like messages in session
		setLikeMsgsToSession(customer.getUserId(), model);
		
		//set cart in session
		setCartToSession(customer, model);	
				
		//set connections in session
		setConnectionsToSession(customer.getUserId(), model);
 		
 		model.setAttribute("viewType", "cart");
 		producer.createHeartNotification(model, "increment", customer.getUserId(), "customer");	 		
 		if(customer.getCartItems().size() > 0 ){
 			model.setAttribute("navButtonSelected", "cart");
 		}
		return "cart";	
		
	}
    
 	@RequestMapping(value="/message/{messageId}" )
 	public String showMessage(@PathVariable("messageId") String messageId, HttpServletRequest model) throws SessionExpiredException {
     	Customer customer = null;
 		try{
     		//set user in session
 			customer = setCustomerUserDetailsToModel(model);
     	}catch(ClassCastException cce){ 
     			System.out.println("User not logged in");
     			return "main";
     	}
 		
 		if(customer==null){
 			return "main";
 		}
 			
// 		//set cart in session
// 		cartService.setCartToSession(customer.getUserId(), model);
// 			
 		//set inbox messages in session
 		setInboxMsgToSession(customer.getUserId(), messageId, model);
 			
 		
 		//set like messages in session
 		setLikeMsgsToSession(customer.getUserId(), model);
 		
		//set connections in session
		setConnectionsToSession(customer.getUserId(), model);
//		cartService.heartIconStatusCheck(customer.getUserId(),"customer", model);
 		producer.createHeartNotification(model, "increment", customer.getUserId(), "customer");	
		model.setAttribute("navButtonSelected", "inbox");
 		model.setAttribute("viewType", "cart");
 		return "customerViewInboxMessage";	
 		
 	}
 	
 	@RequestMapping(value="/inbox" )
 	public String showCustomerInbox(HttpServletRequest model) throws SessionExpiredException {
     	Customer customer = null;
 		try{
     		//set user in session
 			customer = setCustomerUserDetailsToModel(model);
     	}catch(ClassCastException cce){ 
     			System.out.println("User not logged in");
     			return "main";
     	}
 		
 		if(customer==null){
 			return "main";
 		}
 			
 		//set cart in session
// 		cartService.setCartToSession(customer.getUserId(), model);
 			
 		//set inbox messages in session
 		setInboxMsgsToSession(customer.getUserId(), model);
 			
 		
 		//set like messages in session
 		setLikeMsgsToSession(customer.getUserId(), model);
// 		cartService.heartIconStatusCheck(customer.getUserId(),"customer", model);
 		producer.createHeartNotification(model, "increment", customer.getUserId(), "customer");	
		//set connectionRequests in session
		setConnectionsToSession(customer.getUserId(), model);
		
 		model.setAttribute("viewType", "cart");
 		
 		return "cart";			
 	}
   
    private void setCartToSession(Customer customer, HttpServletRequest model){

 		//set cart items in session
		List<CartItem> items = customer.getCartItems();
		if(items.size() == 0){
				String emptyMsg = String.format("your cart </br> is sooooo </br> empty");
				model.getSession().setAttribute("emptyMsg", emptyMsg);
		}
		
		List<List<CartItem>> carts = new ArrayList<List<CartItem>>();
		List<String> names  = new ArrayList<String>();
		List<Integer> waitTimes = new ArrayList<Integer>();
		List<MerchantStatus> merchantStatuses = new ArrayList<MerchantStatus>();
		for(CartItem ci: items){
			System.out.println(ci.getStoreName());
			
			//if names does not contain the store name, make a new list and add it
			if(!names.contains(ci.getStoreName())){
				List<CartItem> newCart = new ArrayList<CartItem>();
				newCart.add(ci);
				carts.add(newCart);
				names.add(ci.getStoreName());
				MerchantStatus currentStatus =  merchantStatusService.findMerchantStatusByMerchantId(customer.getUserId());
				if(currentStatus!=null){
					merchantStatuses.add(currentStatus);
				}
				
				Appointment lastAppointment = appointmentService.findQueueSizeByMerchantId(customer.getUserId());
				if(lastAppointment==null){
					waitTimes.add(30);
				}else{
				waitTimes.add(lastAppointment.getQueueNumber() * 30);  //est. 30 mins per appointment
				}
				//else if names does contain the  store name, find it in carts and add it
			}else{
				for(List<CartItem> c: carts){
					if(c.get(0).getStoreName().equals(ci.getStoreName())){
						c.add(ci);
					}
				}	
			}
		}
			
		System.out.println("****** size of carts list is: " + carts.size());
		System.out.println("****** size of waitTime list is: " + waitTimes.size());
		System.out.println("****** size of currentStatuses list is: " + merchantStatuses.size());
		for(List<CartItem> cc: carts){
			System.out.println("#####" + cc.get(0).getStoreName());
			for(CartItem ci: cc ){
				System.out.println("          @@@@@@"  + ci.getProductName());	
			}
		}
	
		for(Integer wait:  waitTimes){
			System.out.println("wait time is: " + wait);
		}
		model.getSession().setAttribute("waitTimes", waitTimes);
		model.getSession().setAttribute("merchantStatuses", merchantStatuses);
		model.getSession().setAttribute("carts",carts);
		model.getSession().setAttribute("items", items);
		model.getSession().setAttribute("userId", customer.getUserId());
    
    }
    
    private void setLikeMsgsToSession(String userId, HttpServletRequest model){
		//set inbox messages in session
		List<LikeMessage> likeMsgs = likeMessageService.findLikeMessagesByCustomerId(userId);
		model.getSession().setAttribute("likeMsgs", likeMsgs);
    }
    
    private void  setConnectionsToSession(String userId, HttpServletRequest model){
		//find connections in DB for user
		List<Connection> connections = connectionService.findConnectionRequestsByRequesteeId(userId);
		//update the profile pic to current
		if(connections!=null){
			for(Connection lm: connections){
				if(lm.getRequesterType().equals("merchant")){
					Merchant m = merchantService.findByMerchantId(lm.getRequesterId());
					lm.setRequesterImage(m.getProfilePhoto());
				}else if(lm.getRequesterType().equals("customer")){
					Customer c = customerService.findCustomerByCustomerId(lm.getRequesterId());
					lm.setRequesterImage(c.getProfilePhoto());		
				}
			}
		}
		
		List<Connection> connectionRequests = new ArrayList<Connection>();
		for(Connection c: connections){
			if(c.getStatus().equals("requested")){
				connectionRequests.add(c);
			}
		}
		Integer size = connectionRequests.size();
		model.getSession().setAttribute("connRequests", connectionRequests);
		model.getSession().setAttribute("size", size);
		System.out.println("connections size: " + connections.size());
		System.out.println("connection requests size: " + connectionRequests.size());
   
    }
    
    private void setInboxMsgToSession(String userId, String messageId, HttpServletRequest model){
		//set inbox messages in session
		Message message = messageService.findMessageByMessageId(messageId);
		if(message.getRead_time()==null){
	        try{
				String input = new Date().toString();
		        SimpleDateFormat parser = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
		        Date date = parser.parse(input);
		        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		        String formattedDate = formatter.format(date);
				messageService.markMessageRead(new ReadTimeForm("customer", userId, messageId, formattedDate), model);
//				Customer customer = (Customer)model.getAttribute("customer");
//				customer.setNotifications(customer.getNotifications() - 1);
//				model.getSession().setAttribute("customer", "customer");
	        }catch(ParseException p){System.out.println("could not parse the date"); }
	     
		}
		
		model.setAttribute("message", message);

    }
    
    private void  setInboxMsgsToSession(String userId, HttpServletRequest model){
		//set inbox messages in session
		List<Message> messages = messageService.findMessagesByCustomerId(userId);
		model.getSession().setAttribute("messages", messages);
    }

	@RequestMapping(value="/addToCart", method=POST)
	public String addItemToCart(@Valid CartItem cartItem, Errors errors, BindingResult result, HttpServletRequest request, HttpServletResponse response, RedirectAttributes model, Principal principal) throws IllegalStateException, IOException {

		Customer customer = null;
		try{
			customer = customerService.setCustomerUserDetailsToModel(request, principal);
		}catch(ClassCastException | NullPointerException cce){
			System.out.println("user not logged in");
			return "main";
		}
	   if (errors.hasErrors()) {
	        	System.out.println("Errors were found in error object");
	            return "productPage";
	    }         
	    cartItem.setPrice(cartItem.getPrice() * cartItem.getQuantity());
	    cartItem.setCustomer(customer);
	    String productName = cartItem.getProductName();
	    cartItem.formatProductNameSpaces();
	    System.out.println("cart item id: " + cartItem.getCartItemId() );
	    System.out.println("cart item user id: " + customer.getUserId() );
	    cartItemService.addItem(cartItem, request);
	    model.addFlashAttribute("cartItem", cartItem);
    	String encodedPath = cartItem.getStoreName();
    	encodedPath = encodedPath.replaceAll(" ", "%20"); 
	    return "redirect:/cart";
	 }
	
 	@RequestMapping(value="/deleteMessage/{messageId}" )
 	public String deleteCustomerMessage(@PathVariable("messageId") String messageId, HttpServletRequest model) throws SessionExpiredException {
     	Customer customer = null;
 		try{
     		//set user in session
 			customer = setCustomerUserDetailsToModel(model);
     	}catch(ClassCastException cce){ 
     			System.out.println("User not logged in");
     			return "main";
     	}
 		
 		if(customer==null){
 			return "main";
 		}
 		
 		//delete the message
 		messageService.deleteMessage(messageId, model);
 		
// 		//set cart in session
// 		setAToSession(customer.getUserId(), model);
// 			
// 		//set inbox messages in session
// 		setInboxMsgsToSession(customer.getUserId(), model);	
// 		
// 		//set like messages in session
// 		setLikeMsgsToSession(customer.getUserId(), model);
// 		model.setAttribute("navButtonSelected", "inbox");		
// 		model.setAttribute("viewType", "cart");
 	    
 		return "redirect:/inbox";			
 	}
	
	@RequestMapping(value="/deleteFromCart/{cartItemId}", method=GET)
	public String deleteFromCart( @PathVariable("cartItemId") String cartItemId, HttpServletRequest request, HttpServletResponse response, RedirectAttributes model) throws IllegalStateException, IOException {
		System.out.println("Inside deleteFromCart method");    	
	  cartItemService.deleteItem(cartItemId, request);
	    return "redirect:/cart";
	 }
	
	@RequestMapping(value="/deleteFromReviewForm/{cartItemId}", method=GET)
	public String deleteFromReviewForm( @PathVariable("cartItemId") String cartItemId, HttpServletRequest request, HttpServletResponse response, RedirectAttributes model) throws IllegalStateException, IOException {
		System.out.println("Inside deleteFromCart method");    	
	  cartItemService.deleteItem(cartItemId, request);
	    return "redirect:/cart";
	 } 
	
	
	@RequestMapping(value="/reviewOrder")
	public String reviewOrder(HttpServletRequest model){
	  	Customer customer = null;
			try{
	    		//set user in session
				customer = setCustomerUserDetailsToModel(model);
	    	}catch(ClassCastException cce){ 
	    			System.out.println("User not logged in");
	    			return "main";
	    	}
			
			if(customer==null){
				System.out.println("User was not found");
				return "main";
				
			}
				
			AppointmentRequest lastApptReq = appointmentService.findCustomersLastAppointmentRequest(customer.getUserId());
			if(lastApptReq!=null){
				model.getSession().setAttribute("lastAddress", lastApptReq.getSearchTextField());
				model.getSession().setAttribute("lastLat", lastApptReq.getLat());
				model.getSession().setAttribute("lastLng", lastApptReq.getLng());
			}
//			//set cart in session
//			cartService.setCartToSession(customer.getUserId(), model);
				
			//set inbox messages in session
			setInboxMsgsToSession(customer.getUserId(), model);
				
			//set like messages in session
			setLikeMsgsToSession(customer.getUserId(), model);
			
			//set new appointment request to model.
			model.setAttribute("appointmentRequest", new AppointmentRequest());
	 		model.setAttribute("viewType", "cart");
//	 		cartService.heartIconStatusCheck(customer.getUserId(),"customer", model);
	 		producer.createHeartNotification(model, "increment", customer.getUserId(), "customer");	
		return "reviewOrder";			
	}
	

	@RequestMapping(value="/emptyCart", method=GET)
	public String emptyShoppingCart(@SessionAttribute String userId, @SessionAttribute List<CartItem> items, Model model, HttpServletRequest request){
		Integer decrementNumber = 0;
		for(CartItem ci: items){
			decrementNumber = decrementNumber + ci.getQuantity();
		}
		
		int recordsDeleted = cartItemService.emptyCart(userId, request, decrementNumber);
		System.out.println("number of items deleted: " + recordsDeleted );
		String emptyMsg = String.format("your cart </br> is sooooo </br> empty");
		model.addAttribute("emptyMsg", emptyMsg);
		return "redirect:/cart";
		
	}
   	
	private Customer setCustomerUserDetailsToModel(HttpServletRequest model){
		Customer customer;
		try{
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		customer  = customerService.findCustomerByUsername(userDetails.getUsername());
		}catch(ClassCastException cce){
			System.out.println("not logged in");
			throw cce;
		}
		model.getSession().setAttribute("customer", customer);
		return customer;
	}


    	
}
    