package com.yanya.springmvc.controller;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.security.core.context.SecurityContext;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.ui.Model;


import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.CustomerControlMenuForm;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.service.CustomerService;
import com.yanya.springmvc.service.CartItemService;
//import com.yanya.springmvc.service.CartService;
import com.yanya.springmvc.service.MerchantService;
import com.yanya.springmvc.service.ProductService;
//import com.yanya.springmvc.model.Cart;
import com.yanya.springmvc.model.CartItem;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.Product;

@Controller
@RequestMapping("/")
public class LikeController {
    
//    @Autowired
//    LikeService likeService;
//
//    @Autowired
//    MessageSource messageSource;
//
//    @ResponseBody
//	@RequestMapping(value = "/search/api/getSearchResult")
//	public AjaxResponseBody getSearchResultViaAjax(@RequestBody SearchCriteria search) {
//
//		AjaxResponseBody result = new AjaxResponseBody();
//		//logic
//		return result;
//
//	}

	
	
 
}