package com.yanya.springmvc.controller;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import com.authy.*;
import com.authy.api.*;
import com.fasterxml.jackson.annotation.JsonView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.ui.Model;
import com.twilio.notifications.domain.twilio.Client;
import com.twilio.rest.lookups.v1.PhoneNumber;
import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.CustomerControlMenuForm;
import com.yanya.springmvc.model.CustomerForm;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.UserPhoto;
import com.yanya.springmvc.model.UserPhotoForm;
import com.yanya.springmvc.model.VerificationCodeForm;
import com.yanya.springmvc.model.Message;
import com.yanya.springmvc.model.PasswordForm;
import com.yanya.springmvc.service.CustomerService;
import com.yanya.springmvc.service.InviteService;
import com.yanya.springmvc.service.AppointmentService;
import com.yanya.springmvc.service.CartItemService;
////import com.yanya.springmvc.service.CartService;
import com.yanya.springmvc.service.ConnectionService;
import com.yanya.springmvc.service.CustomerFormService;
import com.yanya.springmvc.service.MerchantService;
import com.yanya.springmvc.service.MessageService;
import com.yanya.springmvc.service.ProductService;
import com.yanya.springmvc.service.UserPhotoService;
import com.yanya.springmvc.service.UserService;
import com.yanya.springmvc.event.NotificationEventProducer;
import com.yanya.springmvc.model.Appointment;
////import com.yanya.springmvc.model.Cart;
import com.yanya.springmvc.model.CartItem;
import com.yanya.springmvc.model.Comment;
import com.yanya.springmvc.model.Connection;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.ProfileBannerForm;
import com.yanya.springmvc.model.ProfilePhotoForm;
import com.yanya.springmvc.model.ProfileTagLineForm;
import com.yanya.springmvc.model.RoleEnum;
import com.yanya.springmvc.model.FriendInvite;
import com.yanya.springmvc.model.FriendInviteForm;
import com.yanya.springmvc.model.CustomerVisibilityForm;
@Controller
@RequestMapping("/")
public class CustomerController {
    
    private static String USER_PHOTO_SAVE_PATH = "/media/Rihup/User/UserPhotos/";
    private static String PROFILE_PHOTO_SAVE_PATH = "/media/Rihup/User/ProfilePhotos/";
    private static String PROFILE_BANNER_SAVE_PATH = "/media/Rihup/User/ProfileBanners/";
    
    @Autowired
    CustomerService customerService;
    @Autowired
    UserService userService;
    @Autowired
    CustomerFormService customerFormService;
    @Autowired
    ProductService productService; 
    @Autowired
    MerchantService merchantService;
//    @Autowired
//    CartService cartService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    ConnectionService connectionService;
    @Autowired
    AppointmentService appointmentService;    
    @Autowired
    UserPhotoService userPhotoService;
    @Autowired
    MessageService messageService;
    @Autowired
    InviteService inviteService;
    @Autowired
    MessageSource messageSource;
    @Autowired
    NotificationEventProducer producer;
    @Autowired
    protected AuthenticationManager authenticationManager;
   
    /*
     * This method will return the registration page
     */
    @RequestMapping(value = {"/register" })
    public String showCustomerRegistrationForm(HttpServletRequest model) {
//    	model.setAttribute(new Customer());
    	model.setAttribute("customerForm", new CustomerForm());
        return "userRegister";
    }
    
	@RequestMapping(value="/register", method=POST)
	public String processCustomerRegistration(@Valid CustomerForm customer, Errors errors, BindingResult result, RedirectAttributes model, HttpServletRequest request, HttpServletResponse response){
		System.out.println("######### Inside process customer registration method in customer controller#######");
		
		System.out.println("######### Verifying that invite has not been used before#######");
		List<Customer> c = null;
		try{
			 c  = customerService.findCustomerByInvitation(customer.getInvitation());
		}catch(NullPointerException npe){}
		
		if(c!=null && c.size() > 0){
			System.out.println("An existing customer has alredy usedy this invite.");
			return  "main";
		}
		
		boolean hasErrors = false;
		
		//1.  Check if phone and user name are unique
		//2.  If either are  not unique send error msgs
		//3.  Check if all other field criteria is met
		//4.  If not, send error msgs
		//5.  Otherwise, save the merchant and redirect to merchant page
		
		System.out.println("customer:" + customer.toString());
		
		//check if phone is unique
		System.out.println("######### checking phone uniqueness#######");
        if(!customerService.isCustomerPhoneUnique(customer.getPhone())){
        	System.out.println("Customer Phone not unique");
            FieldError phoneError =new FieldError("customer","phone",messageSource.getMessage("non.unique.phone", new String[]{customer.getPhone()}, Locale.getDefault()));
            result.addError(phoneError);
            hasErrors = true;
        }
        System.out.println("######### checking username uniqueness#######");
        //check if user name is unique
        if(!customerService.isUsernameUnique(customer.getUsername())){
        	System.out.println("username not unique");
        	FieldError userNameError =new FieldError("customer","username",messageSource.getMessage("non.unique.username", new String[]{customer.getUsername()}, Locale.getDefault()));
            result.addError(userNameError);
            hasErrors = true;
        }
        System.out.println("######### checking username for errors#######");
        if(hasErrors|errors.hasErrors()){
        	if(hasErrors)
        		
        		System.out.println("has uniqueness error for phone or username");
        	if(errors.hasErrors()){
        		System.out.println("has length or null error");
        		List<ObjectError> errs = errors.getAllErrors();
        		System.out.println(errors.getErrorCount());
        		System.out.println(errors.getObjectName());    
        		for(ObjectError eo: errs){
        				System.out.println(eo.getDefaultMessage());
        				System.out.println(eo.getObjectName());
        				System.out.println(eo.getCode());
        				System.out.println(eo.getClass().getName());
        		}
        		System.out.println("Customers username is: " + customer.getUsername());
        		System.out.println("Customers password is: " + customer.getPassword());
        		System.out.println("Customers phone is: " + customer.getPhone());
        		System.out.println("Customers profilePhoto is: " + customer.getProfilePhoto());
        		System.out.println("Customers customerForm Id is : " + customer.getCustomerFormId());
        		System.out.println("Users username is: " + customer.getProfilePhoto());

        	}
        	return "userRegister";
        }
        System.out.println("registration form has no errors");
        System.out.println("######### creating friend invite#######");
		System.out.println("Customers username is: " + customer.getUsername());
		System.out.println("Customers password is: " + customer.getPassword());
		System.out.println("Customers phone is: " + customer.getPhone());
		System.out.println("Customers profilePhoto is: " + customer.getProfilePhoto());
		System.out.println("Customers customerForm Id is : " + customer.getCustomerFormId());
		System.out.println("Users username is: " + customer.getInvitation());
	    //Verify the invite code
        FriendInvite f = null;
        try{
        	 System.out.println("######### verify that the user was invited#######");
        	f = inviteService.findByInviteCode(customer.getInvitation());
        
        }catch(NullPointerException e){
        	System.out.println("no friend ivite was found in db");
        	return "main";
        }
        
        if((f.getInviteCode()==null) || (!f.getInviteCode().equals(customer.getInvitation()))){
        	System.out.println("inviteCode was invalid!");
        	return "redirect:/home";	
        }
        
        System.out.println("######### creating the authy id#######");
        
        //Create a new Authy user
        AuthyApiClient client = new AuthyApiClient("I8m2t7gk5MFDuTxJ7jtQv8UvP6i9N6pO", "https://api.authy.com/");
        Users users = client.getUsers();
        Tokens tokens = client.getTokens();
        String phone = customer.getPhone();
        String formattedPhone = phone.substring(0, 3) 
        					   + "-" + phone.substring(3, 6) 
        					   + "-" + phone.substring(6);

        com.authy.api.User user = users.createUser("new_user@email.com", formattedPhone);
        customer.setAuthyId(user.getId());
        
        System.out.println("*******PHONE IS: " + formattedPhone);
        if(user.isOk()){
            System.out.println("AUTHY USER CREATED SUCCESSFULLY!");
        }else{
        	  System.out.println(user.getError());
        	  return "redirect:/main";
        }
   	 System.out.println("######### sending the verificfation sms to the user#######");
        //SEND AN SMS TO THE NEW USER
        PhoneVerification phoneVerification  = client.getPhoneVerification();
        Verification verification;
        Params params = new Params();
        params.setAttribute("locale", "en");
        verification = phoneVerification.start(formattedPhone, "1", "sms", params);
        System.out.println(verification.getMessage());
        System.out.println(verification.getIsPorted());
        System.out.println(verification.getSuccess());
        System.out.println(verification.isOk());
        
      	 System.out.println("######### saving customerform in the db#######");
        //SAVE CUSTOMERFORM IN DATABASE
		//customerFormService.deletePreviousCustomerForms(customer.getPhone());
      	//customerFormService.saveCustomerForm(customer);	
      	 
      	request.getSession().setAttribute("customerForm", customer);
	    System.out.println("customerForm has been saved to model");
		request.setAttribute("verificationCodeForm", new VerificationCodeForm());
	    request.setAttribute("customerFormId", customer.getCustomerFormId());
	    request.setAttribute("phone", customer.getPhone());
        return "/enterVerificationCode"; 
	}   
    
    @RequestMapping(value="/verifyCode")
    public String verifyCodeEntered(@Valid VerificationCodeForm form, HttpServletRequest model, Errors errors, RedirectAttributes redirect){
    	AuthyApiClient client = new AuthyApiClient("I8m2t7gk5MFDuTxJ7jtQv8UvP6i9N6pO");
    	PhoneVerification phoneVerification = client.getPhoneVerification();
    	Verification verification;
        System.out.println("Verification Form code is: " + form.getVerificationCode());
        System.out.println("Verification Form Customer Id is: " + form.getCustomerFormId());
       
        String phone = form.getPhone();
        String formattedPhone = phone.substring(0, 3) 
				   + "-" + phone.substring(3, 6) 
				   + "-" + phone.substring(6);
    	verification = phoneVerification.check(formattedPhone, "1", form.getVerificationCode());
    	System.out.println(verification.getMessage());
    	System.out.println(verification.getIsPorted());
    	System.out.println(verification.getSuccess());
    	System.out.println("AUTHY SMS VERIFIED SUCCESSFULLY!!!!!!");
    	Customer customer = null;
    	if(Boolean.parseBoolean(verification.getSuccess())){
    		CustomerForm customerForm =   (CustomerForm)model.getSession().getAttribute("customerForm");//customerFormService.findByCustomerFormId(form.getCustomerFormId());
    		if(customerForm==null){
    			System.out.println("no customer form was found in model%%%%%%%%%%%%%%%%%%");
    			return "main";
    			

    		}
    		
    		customer = new Customer(customerForm);	
    	
    	
    	}
    	
    	if(customer!=null){
    		System.out.println("cuswtomer will be saved in db now");
    		System.out.println("saving user object");
    		customerService.saveCustomer(customer);
    		System.out.println("customer was saved");
    		model.getSession().removeAttribute("customerForm");
    		authenticateUserAndSetSession(customer, model);
    		System.out.println("user authenticated");
    		redirect.addFlashAttribute("customer", customer);
    		System.out.println("flash attribute added");
    	}
    	return "redirect:/home";
    	
    	}
    
    private void authenticateUserAndSetSession(Customer user, HttpServletRequest request) {
        String username = user.getUsername();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    } 
    
	@Transactional
	@RequestMapping(value="/home", produces="text/plain;charset=UTF-8")
	public String showCustomerHome(HttpServletRequest model, Principal principal){
		
		try{
			Customer customer = customerService.setCustomerUserDetailsToModel(model, principal);
			
//			//set cart		
//			Cart cart = model.getSession().getAttribute("cart");
//			if(cart==null){
//				cartService.setCartToSession(customer.getUserId(), model);
//			}
			
			//set angel marker data
			merchantService.setVisibleMerchantsToSession(customer.getUserId(), model);
			model.setAttribute("viewType", "home");

	 		producer.createHeartNotification(model, "increment", customer.getUserId(), "customer");
		}catch(ClassCastException | NullPointerException cce){
			System.out.println("user not logged in");
			return "main";
		}

		
		return "userHome";
	}
	
	
	@RequestMapping(value="/changePassword", method = RequestMethod.POST)
	public String changePassword(@Valid PasswordForm form, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirect){
		String phone = (String)request.getSession().getAttribute("phone");
		System.out.println("^^^^^^^ Inside change password, PHONE IS: " + phone);
	  Customer customer = customerService.findCustomerByPhone(phone);
	  System.out.println("customer name is: " + customer.getUsername());
	  System.out.println("password is: " + form.getPassword());
	  userService.updatePassword(customer.getUsername(), form.getPassword());
	  customer.setPassword(form.getPassword());
	  //authenticate the user and log in.
		authenticateUserAndSetSession(customer, request);
		System.out.println("user authenticated");
		redirect.addFlashAttribute("customer", customer);
		System.out.println("flash attribute added");
	  return "redirect:/home";
		
	}
	  
	@RequestMapping(value={"/settings"}, method=GET)
	public  String updateStoreSettings(HttpServletRequest request){			
			Customer customer= null;
			
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			//Set user details
			try{
				customer  = customerService.findCustomerByUsername(userDetails.getUsername());		
				if(customer!=null){
					request.setAttribute("userType", "customer");
					request.setAttribute("customer", customer);
					request.setAttribute("customerVisibilityForm", new CustomerVisibilityForm(customer.getUserId(), customer.getVisibility()));
				}	
			}catch(ClassCastException | NullPointerException e){
				System.out.println("user not logged in" + e);
				
				return "main";
			}
			request.setAttribute("viewType", "profile");
		
			return "settings";
			
	  }
	
    @RequestMapping(value="/saveTagLine",method=POST,produces="text/plain;charset=UTF-8") // //new annotation since 4.3
    public String updateProfileTagLine(@Valid ProfileTagLineForm profileTagLineForm, HttpServletRequest model, Errors errors, Principal principal, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
    	try{
    	   Customer customer = customerService.setCustomerUserDetailsToModel(model, principal);
           customerService.updateProfileTagLine(customer.getUserId(),  profileTagLineForm.getProfileTagLine());
		}catch(ClassCastException | NullPointerException cce){
			System.out.println("not logged in");
			return "main";
		}
        return "redirect:/manage";
    }
    
    @PostMapping("/updateProfileBanner") // //new annotation since 4.3
    public String saveProfileBanner(@Valid ProfileBannerForm profileBannerForm, HttpServletRequest model, Errors errors, Principal principal, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
    
		try{
	    	Customer customer = customerService.setCustomerUserDetailsToModel(model, principal);
	    	model.setAttribute("customer", customer);
        
	    	if (profileBannerForm.getProfileBanner().isEmpty()) {
	    		redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/manage";
	    	}

	    	try {

	    		// Get the file and save it somewhere
	    		byte[] bytes = profileBannerForm.getProfileBanner().getBytes();
	    		Path path = Paths.get(PROFILE_BANNER_SAVE_PATH +  profileBannerForm.getUserId() + File.separator +  profileBannerForm.getProfileBanner().getOriginalFilename());
	    		Files.write(path, bytes);

	    		redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" +  profileBannerForm.getProfileBanner().getOriginalFilename() + "'");

	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}catch (MultipartException me){ 
        		System.out.println("");
        		model.setAttribute("sizeExceeded", "size must be less than 5 mb");
        		return "redirect:/manage";
	    	}catch(Exception e){
	    			System.out.println("");
	    			model.setAttribute("sizeExceeded", "size must be less than 5 mb");
	    			return "redirect:/manage";
	    	}
	    	customerService.updateProfileBanner(customer.getUserId(),  profileBannerForm.getProfileBanner().getOriginalFilename());
		}catch(ClassCastException | NullPointerException cce){
			System.out.println("user not logged in");
			return "main";
		}
        return "redirect:/manage";
    }
    
	@RequestMapping(value={"/profile/{userId}/{username}"}, method=GET)
	public String showCustomerProfile(@PathVariable("username") String username, @PathVariable("userId") String userId, HttpServletRequest model, Principal principal){
		Customer customer= null;
		Customer profile = null;
			
		//Set user 
		try{
			customer = customerService.setCustomerUserDetailsToModel(model, principal);			
		//set the profile 
		profile = customerService.setCustomerProfileDataToModel(model, username);	

		}catch(ClassCastException | NullPointerException cce){
			System.out.println("user not logged in or profile data not found");
			return "main";
		}		
		
		//set the connection between user and profile info
		Connection connection = null;
		connection = connectionService.findConnectionByUsers(customer.getUserId(), profile.getUserId());				
    	if(connection !=null){
    		model.setAttribute("connection", connection);
    		
    		if(connection.getRequesterId().equals(profile.getUserId())){
    			model.setAttribute("connectionAction", "ACCEPT");   			
    		}else if(connection.getRequesterId().equals(customer.getUserId())){
    			model.setAttribute("connectionAction", "REQUESTED");
    		}
    		

    	}
    	
    //set the view type
		model.setAttribute("viewType", "home");
  	model.setAttribute("profileType", "profile");
		model.setAttribute("banner", "profileBanner");
    	return "userProfile";
	}
	
	
	@RequestMapping( value={"/invite", "/sell/invite"} )
	public String showReferFriendPage(HttpServletRequest model){             
		Customer customer= null;
		Merchant merchant = null;
		String userType = null;
		
		//Set user details
	   	try{
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
    		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
    		if(authentication != null){
    			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    			for(GrantedAuthority ga: authorities){
    				System.out.println("&&&&&&&&&&&&&" + ga.getAuthority() + "&&&&&&&&&&&");
    				if (ga.getAuthority().equals(RoleEnum.ROLE_MERCHANT.toString())) {
    					merchant  = merchantService.findByUsername(userDetails.getUsername());
    					model.setAttribute("userType", "merchant");
    					model.setAttribute("user", merchant);
    					userType = "merchant";
    				}else if (ga.getAuthority().equals(RoleEnum.ROLE_CUSTOMER.toString())) {
        				customer = customerService.findCustomerByUsername(userDetails.getUsername());
    					model.setAttribute("userType", "customer");
        					model.setAttribute("user", customer);
        					userType = "customer";	
        			}
    			}
    		}
    	}catch(ClassCastException | NullPointerException cce){
    		System.out.println("caught classcast exception!");
    		throw cce;
		}
		
		model.setAttribute("friendInviteForm", new FriendInviteForm());
		model.setAttribute("viewType", "profile");
		return  "invite";
	}
	
	@RequestMapping("/sendFriendReferrals")
	public String sendFriendReferrals(@Valid FriendInviteForm friendInviteForm, HttpServletRequest model, Errors errors){             
		Customer customer= null;
		Merchant merchant = null;
		String userType = null;
		
		//Set user details
	   	try{
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
    		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
    		if(authentication != null){
    			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    			for(GrantedAuthority ga: authorities){
    				System.out.println("&&&&&&&&&&&&&" + ga.getAuthority() + "&&&&&&&&&&&");
    				if (ga.getAuthority().equals(RoleEnum.ROLE_MERCHANT.toString())) {
    					merchant  = merchantService.findByUsername(userDetails.getUsername());
    					model.setAttribute("userType", "merchant");
    					model.setAttribute("user", merchant);
    					userType = "merchant";
    				}else if (ga.getAuthority().equals(RoleEnum.ROLE_CUSTOMER.toString())) {
        				customer = customerService.findCustomerByUsername(userDetails.getUsername());
    					model.setAttribute("userType", "customer");
        					model.setAttribute("user", customer);
        					userType = "customer";	
        			}
    			}
    		}
    	}catch(ClassCastException | NullPointerException cce){
    		System.out.println("caught classcast exception!");
    		throw cce;
		}
		System.out.println("phone1: " + friendInviteForm.getPhone1());
		System.out.println("phone2: " + friendInviteForm.getPhone2());
		System.out.println("phone3: " + friendInviteForm.getPhone3());
		System.out.println("phone4: " + friendInviteForm.getPhone4());
		System.out.println("phone5: " + friendInviteForm.getPhone5());
		List<String> phones = new ArrayList<String>();
		if(!friendInviteForm.getPhone1().isEmpty()){
			phones.add(friendInviteForm.getPhone1());
		}
		if(!friendInviteForm.getPhone2().isEmpty()){
			phones.add(friendInviteForm.getPhone2());
		}
		if(!friendInviteForm.getPhone3().isEmpty()){
			phones.add(friendInviteForm.getPhone3());
		}
		if(!friendInviteForm.getPhone4().isEmpty()){
			phones.add(friendInviteForm.getPhone4());
		}
		if(!friendInviteForm.getPhone5().isEmpty()){
			phones.add(friendInviteForm.getPhone5());
		}

		System.out.println("size of phones list is:" + phones.size());
		for(String phone: phones){
				FriendInvite friendInvite = new FriendInvite(friendInviteForm.getUserId(), phone, new Date(), "new");	
				inviteService.generateInviteCode(friendInvite);
				System.out.println(friendInvite.getUserId());
				System.out.println(friendInvite.getReferredPhoneNumber());
				System.out.println(friendInvite.getSendTime());
				System.out.println(friendInvite.getStatus());
				System.out.println(friendInvite.getInviteCode());
				inviteService.saveNewInvite(friendInvite);
				//SEND TEXT INVITE TO FRIEND
				String formattedPhone = "+1" + phone;
				System.out.println(phone);
				String text = "";
				if(String.valueOf(userType)=="customer"){
					text = customer.getUsername() + " has invited you to BuzzBuzz. http://www.yawya.xyz/register#invitation=" + friendInvite.getInviteCode() ; 
				}else if(String.valueOf(userType)=="merchant"){
					text = merchant.getStoreName() + " has invited you to BuzzBuzz. http://www.yawya.xyz/register#invitation=" + friendInvite.getInviteCode() ; 
				}
				
				new Client().sendImageMessage(formattedPhone, text, "http://www.yawya.xyz/static/png/buzzbuzzInvite3.png");				
		}
		
		model.setAttribute("friendInviteForm", new FriendInviteForm());
		model.setAttribute("viewType", "profile");
    	model.setAttribute("inviteSuccess", true);
		
    	if(String.valueOf(userType)=="customer"){
    		return  "redirect:/invite";
    	}else if(String.valueOf(userType)=="merchant"){
    		return "redirect:/sell/invite";
    		
    	}
    	
    	return "redirect:/invite";
    	
    		
	}
	
	@RequestMapping("/photo/delete/{userId}/{userPhotoId}")
	public String deleteUserPhoto(@PathVariable("userId") String userId, @PathVariable("userPhotoId") String userPhotoId, HttpServletRequest request,Principal principal){
		try{
			Customer customer = customerService.setCustomerUserDetailsToModel(request, principal);
		}catch(ClassCastException | NullPointerException cce){ System.out.println("user not logged in" + cce); }
		
		UserPhoto userPhoto = userPhotoService.findPhotoByPhotoId(userPhotoId);
		userPhotoService.deleteUserPhoto(userPhoto);
		request.setAttribute("deleteSuccess", true);
		
		return "redirect:/manage";
		
	}
	
	
	@RequestMapping(value="/photo/edit/{userId}/{userPhotoId}", method=RequestMethod.GET )
	public String editUserPhoto(@PathVariable("userId") String userId, @PathVariable("userPhotoId") String userPhotoId, Principal principal, HttpServletRequest request){
		try{
			Customer customer = customerService.setCustomerUserDetailsToModel(request, principal);
		}catch(ClassCastException | NullPointerException cce){ System.out.println("user not logged in" + cce); }
		
		UserPhoto userPhoto = userPhotoService.findPhotoByPhotoId(userPhotoId);
		request.setAttribute("userPhoto", userPhoto);
		request.setAttribute("userPhotoForm", new UserPhotoForm());
		
		return "editPhoto";
		
	}
	
	
	@RequestMapping(value="/photo/edit/{userId}/{userPhotoId}", method=RequestMethod.POST  )
	public String submitEditUserPhoto(@Valid UserPhotoForm userPhotoForm, HttpServletRequest request, Principal principal){
		try{
			Customer customer = customerService.setCustomerUserDetailsToModel(request, principal);
		}catch(ClassCastException | NullPointerException cce){ System.out.println("user not logged in" + cce); }
		
		userPhotoService.updatePhotoDescription(userPhotoForm.getPhotoId(), userPhotoForm.getDescription(), userPhotoForm.getUserId());
		
		return "redirect:/manage";
		
	}
	
	@RequestMapping(value={"/photo/{userPhotoId}"})
	public String viewUserPhoto(@PathVariable("userPhotoId") String userPhotoId, HttpServletRequest request, Principal principal ){
		try{
			Customer customer = customerService.setCustomerUserDetailsToModel(request, principal);
		}catch(ClassCastException | NullPointerException cce){
				System.out.println("user was not  logged in");
				return "main";
		};
		
		UserPhoto photo = userPhotoService.findPhotoByPhotoId(userPhotoId);
		System.out.println("User photo is: " + photo.getUserPhotoId());
		request.setAttribute("photo", photo);	
		return "photo";	
	}

	
	@RequestMapping(value="/uploadPhoto")
	public String showPhotoUploadForm(HttpServletRequest model, Principal principal){
		Customer customer;
		try{
			customer  = customerService.setCustomerUserDetailsToModel(model, principal);
		}catch(ClassCastException | NullPointerException cce){
			System.out.println("user not logged in");
			return "main";
		}
		model.setAttribute("customer", customer);		
 		model.setAttribute("customerControlMenuForm", new CustomerControlMenuForm());
 		model.setAttribute("userPhotoForm", new UserPhotoForm());
		model.setAttribute("viewType", "profile");
 		return "userShowPhotoUpload";
	}
	
	@RequestMapping(value="/uploadPhoto", method=POST)
    public String saveUserPhoto(@Valid UserPhotoForm userPhotoForm, HttpServletRequest model, Errors errors, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
    	System.out.println("Inside saveNewPhoto method");
    	Customer customer = customerService.findCustomerByCustomerId(userPhotoForm.getUserId());
    	System.out.println("In controller, userId in productForm is: " + userPhotoForm.getUserId());
        if (errors.hasErrors()) {
        	System.out.println("Errors were found in error object");
        	model.setAttribute("customer", customer);
            return "userShowPhotoUpload";
          }
        UserPhoto photo = userPhotoForm.toProduct(customer);
    	try{
    		System.out.println("SAVING USER PHOTO");
    		String path = USER_PHOTO_SAVE_PATH + File.separator + customer.getUserId() + File.separator  + photo.getImagePath();
    		File file = new File(path);
    		int fileNo = 0;
    		String newFileName = path;
    	    if (file.exists() && !file.isDirectory()) {
    	    	
    	        while(file.exists()){
    	            fileNo++;
    	            newFileName = path + "(" + fileNo + ").png";
    	            file = new File(newFileName);
    	        }
    	        userPhotoForm.getUserPhoto().transferTo(file);


    	    } else if (!file.exists()) {
    	        userPhotoForm.getUserPhoto().transferTo(file);
    	    }
    		
    	    userPhotoService.saveNewUserPhoto(photo);

        }catch(MultipartException | IOException  io){ 
        		System.out.println("Could not save the file"+ io); 
        		model.setAttribute("photoError", "true");
        		return "redirect:/uploadPhoto";
        }

          
//   		model.setAttribute("customerControlMenuForm", new CustomerControlMenuForm());
  		model.setAttribute("photoSuccess",  "true");
    	return "redirect:/manage";
      }
	
    @PostMapping("/saveProfilePhoto") // //new annotation since 4.3
    public String saveProfilePhoto(@Valid ProfilePhotoForm profilePhotoForm, HttpServletRequest model, Errors errors, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
    	Customer customer;
		try{
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			customer  = customerService.findCustomerByUsername(userDetails.getUsername());
		}catch(ClassCastException | NullPointerException cce){
			System.out.println("user not logged in");
			return "main";
		}
		model.setAttribute("customer", customer);
        
		if (profilePhotoForm.getProfilePhoto().isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/manage";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = profilePhotoForm.getProfilePhoto().getBytes();
            
            Path path = Paths.get(PROFILE_PHOTO_SAVE_PATH + profilePhotoForm.getUserId() + File.separator +  profilePhotoForm.getProfilePhoto().getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded '" +  profilePhotoForm.getProfilePhoto().getOriginalFilename() + "'");
            customerService.updateProfilePhoto(customer.getUserId(),  profilePhotoForm.getProfilePhoto().getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        return "redirect:/manage";
    }
    
	@RequestMapping(value="/manage")
	public String showCustomerProfileManagementConsole(HttpServletRequest model, Principal principal){
		try{
			Customer customer = customerService.setCustomerUserDetailsToModel(model, principal); 
			customerService.setCustomerProfileDataToModel(model, principal.getName());
		}catch(ClassCastException | NullPointerException cce){
			System.out.println("user not logged in");
			return "main";
		}
		model.getSession().setAttribute("home", "/");
		model.setAttribute("viewType", "profile");
		model.setAttribute("profileType", "profile");
		model.setAttribute("mode", "edit");
		model.setAttribute("banner", "profileBanner");
		model.setAttribute("upload", "/uploadPhoto");
		return "userProfile";
	}
   
	@RequestMapping(value="/activity", method=GET)
	public String showActivityPage(HttpServletRequest model, Principal principal){
		Customer customer;
		try{
			customer  = customerService.setCustomerUserDetailsToModel(model, principal);
		}catch(ClassCastException | NullPointerException cce){
			System.out.println("user not logged in");
			return "main";
		}
		model.getSession().setAttribute("customer", customer);
 		
 		return "activity";
	}
	
    @RequestMapping(value = {"/" },  method = { RequestMethod.GET, RequestMethod.POST })
    public String custAutoLogin(HttpServletRequest request) {
    	HttpSession session = request.getSession();
     	session.setMaxInactiveInterval(24*60*60);
    	System.out.println("inside login get method in customer controller");
    	System.out.println("*******");
    	try{
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
    		if(authentication != null){
    			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    			for(GrantedAuthority ga: authorities){
    				System.out.println("&&&&&&&&&&&&&" + ga.getAuthority() + "&&&&&&&&&&&");
    				if (ga.getAuthority().equals(RoleEnum.ROLE_CUSTOMER.toString())) {
    					System.out.println("successfully rememberme authenticated");
    					return "forward:/home";
    				}
    			}
    		}
    	}catch(ClassCastException | NullPointerException cce){
    		System.out.println("caught classcast exception!");
    		return "main";
		} 

    	
    	System.out.println("not authenticated.  returning login page.");
    	return "main";
    }

    
	
    /*
     * This method will return the login page
     */
    @RequestMapping(value = {"/login" },  method = { RequestMethod.GET, RequestMethod.POST })
    public String showCustomerLoginForm(@RequestParam(required=false) boolean success, HttpServletRequest request) {
    	HttpSession session = request.getSession();
     	session.setMaxInactiveInterval(24*60*60);
    	System.out.println("inside login get method in customer controller");
    	System.out.println("*******");
    	try{
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
    		if(authentication != null){
    			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    			for(GrantedAuthority ga: authorities){
    				System.out.println("&&&&&&&&&&&&&" + ga.getAuthority() + "&&&&&&&&&&&");
    				if (ga.getAuthority().equals(RoleEnum.ROLE_CUSTOMER.toString())) {
    					System.out.println("successfully rememberme authenticated");
    					return "forward:/home";
    				}
    			}
    		}

    	}catch(ClassCastException | NullPointerException cce){
    		System.out.println("caught classcast exception!");
    		return "main";
		} 
    	if(success){
    		request.setAttribute("success", "success");
    	}
    	System.out.println("not authenticated.  returning login page.");
    	return "main";
        
    } 
    
    @RequestMapping( value={"/store/{storename}"})
    public String showPublicStoreView(@PathVariable("storename") String storeName, HttpServletRequest model, Principal principal){
    	Customer user = null;
    	Merchant profile = null;
		//Set user details
		try{
			user = customerService.setCustomerUserDetailsToModel(model, principal);
			profile = merchantService.setMerchantStoreDataToModel(model, storeName);
		}catch(Exception e){
			System.out.println("user not logged in" + e);					
			return "main";
		}			
		Connection connection = null;
		connection = connectionService.findConnectionByUsers(user.getUserId(), profile.getUserId());
    	if(connection !=null){
    		model.setAttribute("connection", connection);
    	}
    	

		model.getSession().setAttribute("home", "/");
		model.setAttribute("viewType", "home");
		model.setAttribute("profileType", "store");
		model.setAttribute("banner", "storeBanner");
    	return "userProfile";
    }   
    

	@RequestMapping(value="/appointments")
	public String viewAppointments(HttpServletRequest model){
    
		Customer customer;
		try{
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			customer  = customerService.findCustomerByUsername(userDetails.getUsername());
		}catch(ClassCastException | NullPointerException cce){
			System.out.println("user not logged in");
			return "main";
		}
		model.getSession().setAttribute("customer", customer);
	 		
	 		List<Appointment> appointments = appointmentService.findAppointmentsByCustomerId(customer.getUserId());
	 		
	 		if(appointments!=null){
	 			String regex = "United States";
	 			List<String> times = new ArrayList<String>();
	 			SimpleDateFormat dt = new SimpleDateFormat("hh:mm a MM/dd/yy");
	 			for(Appointment appt: appointments){
	 				String addy = appt.getSearchTextField();
	 				int index = addy.indexOf(",", addy.indexOf(",") + 1);
	 				String shortAddy = addy.substring(0,index);
	 				appt.setSearchTextField(shortAddy.replaceAll(regex, "").replace(",", "<br>"));
	 				times.add(dt.format(appt.getTimeReceived()));
	 				
	 			}
	 			
	 			model.setAttribute("times", times);
	 		}
	 		
	 		
	 		
	 		model.setAttribute("appointments", appointments);
	 		model.setAttribute("customerControlMenuForm", new CustomerControlMenuForm());
			model.setAttribute("viewType", "cart");
	 		return "customerViewAppointments";	
	}
	
	@RequestMapping(value="/toolbar")
	public String getToolbar(){
		return "toolbar";
	}
	
	@RequestMapping(value="/appointment/{appointmentId}")
	public String viewAppointment(@PathVariable String appointmentId, HttpServletRequest model){
    
			Customer customer;
			try{
				UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				customer  = customerService.findCustomerByUsername(userDetails.getUsername());
			}catch(ClassCastException | NullPointerException cce){
				System.out.println("user not logged in");
				return "main";
			}
			model.getSession().setAttribute("customer", customer);
	 		
	 		Appointment appointment = appointmentService.findAppointmentByAppointmentId(appointmentId);
	 		String status = appointment.getAppointmentStatus();
	 		if(status.equals("new")){
	 			model.getSession().setAttribute("appointmentHeader", "NEW APPOINTMENT REQUEST");
	 		}else if(status.equals("confirmed")){
	 			model.getSession().setAttribute("appointmentHeader", "UPCOMING APPOINTMENT");
	 		}else if(status.equals("rejected")){
	 			model.getSession().setAttribute("appointmentHeader", "REJECTED APPOINTMENT");
	 		}else if(status.equals("completed")){
	 			model.getSession().setAttribute("appointmentHeader", "COMPLETED APPOINTMENT");
	 		}
	 		
	 		
	 		model.getSession().setAttribute("appointment", appointment);
	 		model.setAttribute("formattedAddress", appointment.getSearchTextField().replace("," , "<br/>"));
	 		model.setAttribute("customerControlMenuForm", new CustomerControlMenuForm());
	 		
			model.setAttribute("viewType", "cart");
	 		return "customerViewAppointment";	
	}
	
	
	


}