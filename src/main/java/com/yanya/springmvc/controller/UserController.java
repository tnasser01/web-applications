 package com.yanya.springmvc.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.List;
import java.util.Locale;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.PasswordForm;
import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.VerifyPasswordCodeForm;
import com.yanya.springmvc.service.CustomerService;
import com.yanya.springmvc.service.MerchantService;
import com.yanya.springmvc.service.UserService;
import com.authy.AuthyApiClient;
import com.authy.api.Params;
import com.authy.api.PhoneVerification;
import com.authy.api.Tokens;
import com.authy.api.Users;
import com.authy.api.Verification;
import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.ForgotPasswordForm;

@Controller
@RequestMapping("/")
public class UserController{

    
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private MerchantService merchantService;
	
    @Autowired
    MessageSource messageSource;
    
    @Autowired
    protected AuthenticationManager authenticationManager;

    
	@RequestMapping(value="/loginDirector")
	public String showUserHome(HttpServletRequest model, HttpServletRequest request){
		User user;
		try{
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user  = userService.findUserByUsername(userDetails.getUsername());
		}catch(ClassCastException cce){
			System.out.println("not authenticated");
			return "main";
		}
		model.getSession().setAttribute("user", user);	
		
		if (request.isUserInRole("ROLE_CUSTOMER")) {

		    return "redirect:/home";
		}else if(request.isUserInRole("ROLE_MERCHANT")) {

		    return "redirect:/merchant/home";
		}
 		return "merchantHome";
	 
	}
	
	@RequestMapping(value={"/logout"}, method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	   
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){   
	    	UserDetails userDetails= (UserDetails)auth.getPrincipal();
	    	Customer customer = customerService.findCustomerByUsername(userDetails.getUsername());
	    	if(customer!=null){
		        new SecurityContextLogoutHandler().logout(request, response, auth);
		      
	    	}    
	    }
	    return "redirect:/login?success=true";
	}
	
	@RequestMapping(value={"/sell/logout"}, method = RequestMethod.GET)
	public String sellerlogoutPage (HttpServletRequest request, HttpServletResponse response) {
	   
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){   
	    	UserDetails userDetails= (UserDetails)auth.getPrincipal();
	    	Merchant merchant = merchantService.findByUsername(userDetails.getUsername());
	    		if(merchant!=null){
			        new SecurityContextLogoutHandler().logout(request, response, auth);
			        
			        return "redirect:/sell?success=true";			  	
	    		}    
	    }
	    
	    return "redirect:/sell/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    
	}
	
	@RequestMapping(value="/forgotPassword", method = RequestMethod.GET)
	public String showForgotPasswordForm(HttpServletRequest request, HttpServletResponse response){
			request.setAttribute("forgotPasswordForm", new ForgotPasswordForm());		
			request.getSession().setAttribute("home", "/");
			return "forgotPassword";
		
	}
	
	@RequestMapping(value="/sell/forgotPassword", method = RequestMethod.GET)
	public String showSellerForgotPasswordForm(HttpServletRequest request, HttpServletResponse response){
			request.setAttribute("forgotPasswordForm", new ForgotPasswordForm());
			request.getSession().setAttribute("home", "/sell/");
			return "forgotPassword";
		
	}
	
	@RequestMapping(value={"/submitForgotPassword", "/sell/submitForgotPassword"}, method = RequestMethod.POST)
	public String showForgotPasswordForm(@Valid ForgotPasswordForm form, Errors errors, HttpServletRequest request, HttpServletResponse response){
			
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

        		return "forgotPassword";
       }

    System.out.println("registration form has no errors");
    AuthyApiClient client = new AuthyApiClient("I8m2t7gk5MFDuTxJ7jtQv8UvP6i9N6pO", "https://api.authy.com/");
    String phone = form.getPhone();
    String formattedPhone = phone.substring(0, 3) + "-" + phone.substring(3, 6) + "-" + phone.substring(6);

    System.out.println("*******PHONE IS: " + formattedPhone);
    System.out.println("######### sending the verificfation sms to the user#######");
    //SEND AN SMS TO THE NEW USER
    PhoneVerification phoneVerification  = client.getPhoneVerification();
    request.getSession().setAttribute("formattedPhone", formattedPhone);
    System.out.println("Setting phone attribute in session :" + form.getPhone());
    request.getSession().setAttribute("phone", form.getPhone());
    Verification verification;
    Params params = new Params();
    params.setAttribute("locale", "en");
    verification = phoneVerification.start(formattedPhone, "1", "sms", params);
    System.out.println(verification.getMessage());
    System.out.println(verification.getIsPorted());
    System.out.println(verification.getSuccess());
    System.out.println(verification.isOk());	
		//request.setAttribute("msgSent", "Password reset text sent");
    request.setAttribute("verifyPasswordCodeForm", new VerifyPasswordCodeForm());
    return "verifyForgotPasswordCode";
		
	}
	
	@RequestMapping(value={"/verifyPasswordCode",  "/sell/verifyPasswordCode"} , method = RequestMethod.POST)
	public String showForgotPasswordForm(@Valid VerifyPasswordCodeForm form, Errors errors, HttpServletRequest request, HttpServletResponse response){
		
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

  		return "verifyForgotPasswordCode";
 }
		
	  AuthyApiClient client = new AuthyApiClient("I8m2t7gk5MFDuTxJ7jtQv8UvP6i9N6pO", "https://api.authy.com/");
		PhoneVerification phoneVerification = client.getPhoneVerification();

		Verification verificationCode;
		String formattedPhone = (String)request.getSession().getAttribute("formattedPhone");
		
		System.out.println("***** PHONE is: " + formattedPhone);
		System.out.println("***** Verification code is: " + form.getVerificationCode());
		
		verificationCode = phoneVerification.check(formattedPhone, "1", form.getVerificationCode());
		
		request.setAttribute("passwordForm", new PasswordForm());
		System.out.println(verificationCode.getMessage());
		System.out.println(verificationCode.getIsPorted());
		System.out.println(verificationCode.getSuccess());
		
		if(!Boolean.parseBoolean(verificationCode.getSuccess())){
			request.setAttribute("verifyPasswordCodeForm", new VerifyPasswordCodeForm());
			return "verifyForgotPasswordCode";
		}
  	
			return "resetPassword";
	}
	
		
	
	
}

