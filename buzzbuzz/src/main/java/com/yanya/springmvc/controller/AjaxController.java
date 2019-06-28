package com.yanya.springmvc.controller;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.SynthesizedAnnotation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.ui.Model;

import com.yanya.springmvc.service.CommentService;
import com.yanya.springmvc.service.ConnectionService;
import com.yanya.springmvc.service.CustomerService;
import com.yanya.springmvc.service.MerchantService;
import com.yanya.springmvc.service.MerchantStatusService;
import com.yanya.springmvc.service.ProductImpressionService;
import com.yanya.springmvc.service.ProductService;
import com.yanya.springmvc.service.UserPhotoService;
import com.yanya.springmvc.service.UserService;
import com.yanya.springmvc.model.MerchantStatus;
import com.yanya.springmvc.model.MerchantStatusForm;
import com.yanya.springmvc.model.MerchantVisibilityForm;
import com.yanya.springmvc.model.CustomerVisibilityForm;
import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.Message;
import com.yanya.springmvc.model.ProductImpression;
import com.yanya.springmvc.model.ProductImpressionForm;
import com.yanya.springmvc.model.UserPhoto;
import com.yanya.springmvc.model.AjaxResponseBody;
import com.yanya.springmvc.model.Comment;
import com.yanya.springmvc.model.CommentForm;
import com.yanya.springmvc.model.Connection;
import com.yanya.springmvc.model.FilterForm;
import com.yanya.springmvc.model.Impression;
import com.yanya.springmvc.service.ImpressionService;
import com.yanya.springmvc.service.LikeMessageService;
import com.yanya.springmvc.model.ImpressionForm;
import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.ReadTimeForm;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.jsonview.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@RestController
public class AjaxController {
	
	@Autowired
	MerchantStatusService service;
	
	@Autowired
	UserService userService;
	
	@Autowired
	MerchantService merchantService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ConnectionService connectionService;
	
	@Autowired
	UserPhotoService userPhotoService;
	
	@Autowired 
	ProductImpressionService productImpressionService;
	
	@Autowired
	ImpressionService impressionService;
	
	@Autowired
	LikeMessageService likeMessageService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	ProductService productService;
	
	List<MerchantStatus> statusList;
	
	List<Merchant> merchantList;
	
	List<Customer> customerList;
	
	List<Connection> connectionList;

	List<Comment> commentList;
	
	List<Integer> stringList;
	
	// @ResponseBody, not necessary, since class is annotated with @RestController
	// @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
	// @JsonView(Views.Public.class) - Optional, filters json data to display.
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/{impressionType}/photo/{userPhotoId}/{userId}", method = RequestMethod.POST)
	public AjaxResponseBody saveOrUpdateImpression(@PathVariable("userPhotoId") String userPhotoId, @PathVariable("userId") String userId, @PathVariable("impressionType") String impressionType, HttpServletRequest request, Principal principal) {
		AjaxResponseBody result = new AjaxResponseBody();
		System.out.println("User id to serch is: " + userId + "**************");
//		Customer user = customerService.findUserByUserId(userId);
//		System.out.println("hello" + user.getUsername() );
//		System.out.println(user.getUserType());
		UserPhoto photo = userPhotoService.findPhotoByPhotoId(userPhotoId);
		Impression imp = impressionService.findImpressionByPhotoIdAndUserId(userPhotoId, userId);

		if(imp!=null){
				imp.setImpressionType(impressionType);
				impressionService.updateImpression(imp, request);
		}else{
				impressionService.saveImpression(new Impression(photo, userId,  principal.getName(), impressionType, "customer"), request);

		} 
		List impressionList = new ArrayList<Impression>();
		impressionList.add(userId);
		if (impressionList.size() > 0) {
				result.setCode("200");
				result.setMsg("Status has been set");
				result.setResult(impressionList);
		} else {
				result.setCode("204");
				result.setMsg("No user!");
		}


		//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}
	
	// @ResponseBody, not necessary, since class is annotated with @RestController
	// @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
	// @JsonView(Views.Public.class) - Optional, filters json data to display.
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/sell/{impressionType}/photo/{userPhotoId}/{userId}", method = RequestMethod.POST)
	public AjaxResponseBody saveOrUpdateImpressionFromMerchant(@PathVariable("userPhotoId") String userPhotoId, @PathVariable("userId") String userId, @PathVariable("impressionType") String impressionType, HttpServletRequest request, Principal principal) {
		AjaxResponseBody result = new AjaxResponseBody();
//		System.out.println("User id to serch is: " + userId + "**************");
//		Merchant merchant = merchantService.findByUserId(userId);
//		System.out.println("hello" + merchant.getUsername() );
		UserPhoto photo = userPhotoService.findPhotoByPhotoId(userPhotoId);
		Impression imp = impressionService.findImpressionByPhotoIdAndUserId(userPhotoId, userId);

		if(imp!=null){
				imp.setImpressionType(impressionType);
				impressionService.updateImpression(imp, request);
		}else{
				impressionService.saveImpression(new Impression(photo, userId,  principal.getName(), impressionType, "merchant"), request);

		} 
		List impressionList = new ArrayList<Impression>();
		impressionList.add(userId);
		if (impressionList.size() > 0) {
				result.setCode("200");
				result.setMsg("Status has been set");
				result.setResult(impressionList);
		} else {
				result.setCode("204");
				result.setMsg("No user!");
		}


		//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}
	
	// @ResponseBody, not necessary, since class is annotated with @RestController
	// @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
	// @JsonView(Views.Public.class) - Optional, filters json data to display.
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/{impressionType}/product/{productId}/{userId}", method = RequestMethod.POST)
	public AjaxResponseBody saveOrUpdateProductImpression(@PathVariable("productId") String productId, @PathVariable("userId") String userId, @PathVariable("impressionType") String impressionType, HttpServletRequest request,  Principal principal) {
		AjaxResponseBody result = new AjaxResponseBody();
	
		Product product = productService.findProductByProductId(productId);
		System.out.println("&&&&&&&&&& " + product.getProductId() + " " + product.getProductDescription());
		if(product==null){
			return result;
		}
		ProductImpression imp = productImpressionService.findProductImpressionByProductIdAndUserId(productId, userId);
		if(imp!=null){
			imp.setProductImpressionType(impressionType);
			productImpressionService.updateProductImpression(imp, request);
		}else{
			productImpressionService.saveProductImpression(new ProductImpression(product, userId, principal.getName(), impressionType, "customer"), request);
		}			
			
		List<String> impressionList = new ArrayList<String>();
		impressionList.add(userId);
		if (impressionList.size() > 0) {
				result.setCode("200");
				result.setMsg("Status has been set");
				result.setResult(impressionList);
		} else {
				result.setCode("204");
				result.setMsg("No user!");
		}
	//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}
	
	// @ResponseBody, not necessary, since class is annotated with @RestController
	// @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
	// @JsonView(Views.Public.class) - Optional, filters json data to display.
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/sell/{impressionType}/product/{productId}/{userId}", method = RequestMethod.POST)
	public AjaxResponseBody saveOrUpdateProductImpressionFromMerchant(@PathVariable("productId") String productId, @PathVariable("userId") String userId, @PathVariable("impressionType") String impressionType, HttpServletRequest request,  Principal principal) {
		AjaxResponseBody result = new AjaxResponseBody();
	
		Product product = productService.findProductByProductId(productId);
		System.out.println("&&&&&&&&&& " + product.getProductId() + " " + product.getProductDescription());
		if(product==null){
			return result;
		}
		ProductImpression imp = productImpressionService.findProductImpressionByProductIdAndUserId(productId, userId);
		if(imp!=null){
			imp.setProductImpressionType(impressionType);
			productImpressionService.updateProductImpression(imp, request);
		}else{
			productImpressionService.saveProductImpression(new ProductImpression(product, userId, principal.getName(), impressionType, "merchant"), request);
		}			
			
		List<String> impressionList = new ArrayList<String>();
		impressionList.add(userId);
		if (impressionList.size() > 0) {
				result.setCode("200");
				result.setMsg("Status has been set");
				result.setResult(impressionList);
		} else {
				result.setCode("204");
				result.setMsg("No user!");
		}
	//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}
	
	// @ResponseBody, not necessary, since class is annotated with @RestController
	// @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
	// @JsonView(Views.Public.class) - Optional, filters json data to display.
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/sell/updateStoreStatus", method = RequestMethod.POST)
	public AjaxResponseBody updateStoreStatus(@RequestBody MerchantStatus status) {
		AjaxResponseBody result = new AjaxResponseBody();
		if (isValidMerchantStatus(status)) {
			merchantService.updateMerchantLocation(status.getUserId(), status.getSearchTextField(), status.getCityLat(), status.getCityLng());
			MerchantStatus savedStatus = service.saveMerchantStatus(status);
	
			statusList = new ArrayList<MerchantStatus>();
			statusList.add(savedStatus);
			
			if (statusList.size() > 0) {
				result.setCode("200");
				result.setMsg("Status has been set");
				result.setResult(statusList);
			} else {
				result.setCode("204");
				result.setMsg("No user!");
			}

		} else {
			result.setCode("400");
			result.setMsg("Search criteria is empty!");
		}
		//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}
	
	// @ResponseBody, not necessary, since class is annotated with @RestController
	// @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
	// @JsonView(Views.Public.class) - Optional, filters json data to display.
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/api/readLikeMessages/{userId}/{userType}", method = RequestMethod.POST)
	public AjaxResponseBody readLikeMessages(@PathVariable("userId") String userId, @PathVariable("userType") String userType, HttpServletRequest model) {
		List<Integer> list= new ArrayList<Integer>();
		likeMessageService.readLikeMessages(userId, userType, new Date(), model);
		connectionService.readConnectionRequests(userId,userType,model);
		if(userType.equals("merchant")){
			Merchant m = merchantService.findByMerchantId(userId);
			list.add(m.getNotifications());
			System.out.println("((((((((((( Notifications is: " + m.getNotifications());
		}else if(userType.equals("customer")){
			Customer c = customerService.findCustomerByCustomerId(userId);
			list.add(c.getNotifications());
			System.out.println("((((((((((( Notifications is: " + c.getNotifications());
		}
		AjaxResponseBody result = new AjaxResponseBody();
		
		result.setCode("200");
		result.setMsg("Status has been set");
		result.setResult(list);

		//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/api/filterSearchResults", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public @ResponseBody AjaxResponseBody filterSearchResults(@RequestBody FilterForm filterForm, HttpServletRequest request) {
		System.out.println("inside submitComment method in ajax Controller");
		System.out.println(filterForm.toString());
		List<Product> searchResults = (List<Product>)request.getSession().getAttribute("searchResults");
		System.out.println("&&&&&&&" + searchResults.size());
		System.out.println(searchResults.get(0).getProductName());
//		List<Product> filteredSearchResults = filterForm.filterSearchResults(searchResults);
		List<Product> filteredSearchResults = productService.filterSearchResults(searchResults, filterForm);
		System.out.println("@@@@@@@" + filteredSearchResults.size());
		AjaxResponseBody result = new AjaxResponseBody();
		result.setCode("200");
		result.setMsg("msg set");
		List<String> productIds = new ArrayList<String>();
		for(Product p: filteredSearchResults){
			productIds.add(p.getProductId());
		}
		result.setResult(productIds);
		
		
		return result;
	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/api/addComment", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public @ResponseBody AjaxResponseBody submitComment(@RequestBody CommentForm comment) {
		System.out.println("inside submitComment method in ajax Controller");
		System.out.println("userphotoId: " + comment.getUserPhotoId());
		System.out.println("userphotoId: " + comment.getCommentText());
		System.out.println("userphotoId: " + comment.getUserId());
		System.out.println("userphotoId: " + comment.getUsername());
		UserPhoto photo = userPhotoService.findPhotoByPhotoId(comment.getUserPhotoId());
		System.out.println("photo ID from photo is: " + photo.getUserPhotoId());
		AjaxResponseBody result = new AjaxResponseBody();
		commentList = new ArrayList<Comment>();
		try{
			Comment savedComment = commentService.saveNewComment(new Comment(photo,comment.getCommentText(), comment.getUserId(), comment.getUsername(), comment.getTimeStamp()));	
			System.out.println(savedComment.getCommentText());
			System.out.println("userphotoId: " + savedComment.getUserPhoto().getUserPhotoId());
			System.out.println("userphotoId: " + savedComment.getCommentText());
			System.out.println("userphotoId: " + savedComment.getUserId());
			System.out.println("userphotoId: " + savedComment.getUsername());
			if (isValidComment(savedComment)) {
					commentList.add(savedComment);
					System.out.println("comment text after db save: " + savedComment.getCommentText());
			if (commentList.size() > 0) {
				result.setCode("200");
				result.setMsg("msg set");
				result.setResult(commentList);
			} else {
				result.setCode("204");
				result.setMsg("No comment!");
			}
	
		} else {
			result.setCode("400");
			result.setMsg("There was an error setting this comment");
		}	
	}catch(Exception e){ 
			result.setCode("400");
			result.setMsg("There was an error saving this comment");
			return result;
	}
		//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/sell/api/updateVisibility", method = RequestMethod.POST)
	public AjaxResponseBody updateMerchantVisibility(@RequestBody MerchantVisibilityForm visibility) {
		System.out.println("inside ajax controller, merchId: " + visibility.getUserId());
		System.out.println("inside ajax controller, visib: " + visibility.getVisibility());
		Merchant merchant = merchantService.updateMerchantVisibility(visibility.getUserId(), visibility.getVisibility());
	
		AjaxResponseBody result = new AjaxResponseBody();
		merchantList = new ArrayList<Merchant>();
		merchantList.add(merchant);
		
		result.setCode("200");
		result.setMsg("Visibility has been updated");
		result.setResult(merchantList);
		

	//AjaxResponseBody will be converted into json format and send back to the request.
	return result;

	}
	
	
	
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/api/updateCustomerVisibility", method = RequestMethod.POST)
	public AjaxResponseBody updateCustomerVisibility(@RequestBody CustomerVisibilityForm visibility) {
		System.out.println("inside ajax controller, userId: " + visibility.getUserId());
		System.out.println("inside ajax controller, visib: " + visibility.getVisibility());
		Customer customer = customerService.updateCustomerVisibility(visibility.getUserId(), visibility.getVisibility());
	
		AjaxResponseBody result = new AjaxResponseBody();
		customerList = new ArrayList<Customer>();
		customerList.add(customer);
		
		result.setCode("200");
		result.setMsg("Visibility has been updated");
		result.setResult(customerList);
		

	//AjaxResponseBody will be converted into json format and send back to the request.
	return result;

	}
	
	
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/connect", method = RequestMethod.POST)
	public AjaxResponseBody sendConnectionRequest(@RequestBody com.yanya.springmvc.model.Connection connection) {
		System.out.println("inside ajax controller, merchId: " + connection.getRequesterId());
		System.out.println("inside ajax controller, visib: " + connection.getRequesteeId());
		System.out.println("inside ajax controller, merchId: " + connection.getRequesterType());
		System.out.println("inside ajax controller, visib: " + connection.getRequesteeType());
		
		Connection savedConn= connectionService.saveConnection(connection);
	
		AjaxResponseBody result = new AjaxResponseBody();
		connectionList = new ArrayList<Connection>();
		connectionList.add(savedConn);
		
		result.setCode("200");
		result.setMsg("Connection Request sent");
		result.setResult(connectionList);
		

	//AjaxResponseBody will be converted into json format and send back to the request.
	return result;

	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/connectionRequestResponse/{connectionId}/{response}", method = RequestMethod.POST)
	public AjaxResponseBody sendConnectionRequestResponse(@PathVariable("connectionId") String connectionId, @PathVariable("response") String response, HttpServletRequest request) {

		System.out.println("inside connection Request Response");
		Connection savedConn= connectionService.respondToConnectionRequest(connectionId, response, request);
	
		AjaxResponseBody result = new AjaxResponseBody();
		connectionList = new ArrayList<Connection>();
		connectionList.add(savedConn);
		
		result.setCode("200");
		result.setMsg("Connection Request sent");
		result.setResult(connectionList);
		

	//AjaxResponseBody will be converted into json format and send back to the request.
	return result;

	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/sell/connectionRequestResponse/{connectionId}/{response}", method = RequestMethod.POST)
	public AjaxResponseBody sendConnectionRequestResponseFromMerchant(@PathVariable("connectionId") String connectionId, @PathVariable("response") String response, HttpServletRequest request) {

		System.out.println("inside connection Request Response");
		Connection savedConn= connectionService.respondToConnectionRequest(connectionId, response, request);
	
		AjaxResponseBody result = new AjaxResponseBody();
		connectionList = new ArrayList<Connection>();
		connectionList.add(savedConn);
		
		result.setCode("200");
		result.setMsg("Connection Request sent");
		result.setResult(connectionList);
		

	//AjaxResponseBody will be converted into json format and send back to the request.
	return result;

	}

	private boolean isValidMerchantStatus(MerchantStatus status) {
		boolean valid = true;
		if (status == null) {
			valid = false;
		}
		if ((StringUtils.isEmpty(status.getUserId())) && (StringUtils.isEmpty(status.getSearchTextField()))) {
			valid = false;
		}
		return valid;
	}
	
		private boolean isValidComment(Comment comment) {
		boolean valid = true;
		if (comment == null) {
			valid = false;
		}
		if ((StringUtils.isEmpty(comment.getCommentText())) && (StringUtils.isEmpty(comment.getUsername()))) {
			valid = false;
		}
		return valid;
	}
		
}
    
