package com.yanya.springmvc.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.List;
import java.util.Locale;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Errors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.NestedServletException;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.yanya.springmvc.event.NotificationEventProducer;
import com.yanya.springmvc.exception.SessionExpiredException;
import com.yanya.springmvc.model.Appointment;
import com.yanya.springmvc.model.AppointmentItem;
import com.yanya.springmvc.model.Connection;
import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.LikeMessage;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.MerchantControlMenuForm;
import com.yanya.springmvc.model.MerchantForm;
import com.yanya.springmvc.model.MerchantStatus;
import com.yanya.springmvc.model.MerchantStatusForm;
import com.yanya.springmvc.model.MerchantVisibilityForm;
import com.yanya.springmvc.model.CustomerVisibilityForm;
import com.yanya.springmvc.model.Message;
import com.yanya.springmvc.model.Note;
import com.yanya.springmvc.model.PasswordForm;
import com.yanya.springmvc.model.Product;
import com.yanya.springmvc.model.ProductForm;
import com.yanya.springmvc.model.RoleEnum;
import com.yanya.springmvc.model.ProfileBannerForm;
import com.yanya.springmvc.model.StoreProfilePhotoForm;
import com.yanya.springmvc.model.ProfileTagLineForm;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.UserPhoto;
import com.yanya.springmvc.service.AppointmentService;
//import com.yanya.springmvc.service.CartService;
import com.yanya.springmvc.service.ConnectionService;
import com.yanya.springmvc.service.CustomerService;
import com.yanya.springmvc.service.LikeMessageService;
import com.yanya.springmvc.service.MerchantService;
import com.yanya.springmvc.service.MerchantStatusService;
import com.yanya.springmvc.service.MessageService;
import com.yanya.springmvc.service.NoteService;
import com.yanya.springmvc.service.ProductService;
import com.yanya.springmvc.service.UserPhotoService;
import com.yanya.springmvc.service.UserService;
import com.yanya.springmvc.model.ReadTimeForm;

@Controller
@RequestMapping("/")
public class MerchantController{
	
	//Save the uploaded file to this folder
    private static String PROFILE_PHOTO_SAVE_PATH = "/media/Rihup/Merchant/StorePhotos/";
    private static String STORE_BANNER_SAVE_PATH = "/media/Rihup/Merchant/StoreBanners/";
    private static String PRODUCT_PHOTO_SAVE_PATH1 = "/media/Rihup/Merchant/ProductPhotos1/";
    private static String PRODUCT_PHOTO_SAVE_PATH2 = "/media/Rihup/Merchant/ProductPhotos2/";
    private static String PRODUCT_PHOTO_SAVE_PATH3 = "/media/Rihup/Merchant/ProductPhotos3/";
    private static String PRODUCT_PHOTO_SAVE_PATH4 = "/media/Rihup/Merchant/ProductPhotos4/";
    private static String PRODUCT_PHOTO_SAVE_PATH5 = "/media/Rihup/Merchant/ProductPhotos5/";
    
    
	@Autowired
	private MerchantService merchantService;
	
    @Autowired
    private MerchantStatusService merchantStatusService;
    
	@Autowired
	private MessageService messageService;	
    
	@Autowired
    private UserService userService;
	
	@Autowired
  private UserPhotoService userPhotoService;
    
	@Autowired
	private CustomerService customerService;
//	
//	@Autowired
//	private CartService cartService;
	@Autowired
	private ProductService productService;
	
	@Autowired
	private LikeMessageService likeMessageService;
	
	@Autowired
	private NoteService noteService;
	
    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private ConnectionService connectionService;
    @Autowired
    NotificationEventProducer producer;
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    protected AuthenticationManager authenticationManager;
	
	@ModelAttribute("stateList")
	public List<String> getState(){
		List<String> stateList = new ArrayList<String>();
		stateList.add("California");
		return stateList;
	}

	
	@ModelAttribute("strainList")
	public List<String> getStrainList(){
		List<String> strainList = new ArrayList<String>();
	    strainList.add("n/a");
	    strainList.add("Indica");
		strainList.add("Sativa");
		strainList.add("Hybrid");
		return strainList;
	}
	
	
	@ModelAttribute("productTypeList")
	public List<String> getProductTypeList(){
		List<String> productTypeList = new ArrayList<String>();
	    productTypeList.add("Flower");
		productTypeList.add("Concentrates");
		productTypeList.add("Edibles");
		productTypeList.add("Pipes");
		productTypeList.add("Pens");
		productTypeList.add("Bongs");
		productTypeList.add("Papers");
		productTypeList.add("Cigars");
		productTypeList.add("Grinders");
		return productTypeList;
	}
	
    @RequestMapping(value = {"/sell" }, method = { RequestMethod.GET, RequestMethod.POST })
    public String showMerchantLoginForm(@RequestParam(required=false) boolean success, Model model, HttpServletRequest request) {
    	System.out.println("inside sell");
    	HttpSession session = request.getSession();
     	session.setMaxInactiveInterval(24*60*60);
    	System.out.println("inside login get method in merchant controller");
    	System.out.println("*******");
    	try{
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
    		if(authentication != null){
    			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    			for(GrantedAuthority ga: authorities){
    				System.out.println("&&&&&&&&&&&&&" + ga.getAuthority() + "&&&&&&&&&&&");
    				if (ga.getAuthority().equals(RoleEnum.ROLE_MERCHANT.toString())) {
    					System.out.println("successfully rememberme authenticated");
    					return "forward:/sell/home";
    				}
    			}
    		}
    	}catch(ClassCastException cce){
    		System.out.println("caught classcast exception!");
    		return "merchantMain";
		}
    	if(success){
    		request.setAttribute("success", "success");
    	}
    	
    	System.out.println("not authenticated.  returning login page.");
    	return "merchantMain";
    }
    
	
 	@RequestMapping(value="/sell/deleteMessage/{messageId}" )
 	public String deleteMerchantMessage(@PathVariable("messageId") String messageId, HttpServletRequest model, Principal principal) throws SessionExpiredException {
     	Merchant merchant = null;
 		try{
     		//set user in session
 			merchant = merchantService.setMerchantUserDetailsToModel(model, principal);
     	}catch(ClassCastException cce){ 
     			System.out.println("User not logged in");
     			return "merchantMain";
     	}
 		
 		if(merchant==null){
 			return "merchantMain";
 		}
 		
 		//delete the message
 		messageService.deleteMessage(messageId, model);
 		
 		//set cart in session
// 		setAppointmentsToSession(merchant.getUserId(), model);
// 			
// 		//set inbox messages in session
// 		setInboxMsgsToSession(merchant.getUserId(), model);	
// 		
// 		//set like messages in session
// 		setLikeMsgsToSession(merchant.getUserId(), model);
// 		model.setAttribute("viewType", "inbox");
 		return "redirect:/sell/inbox";
 				
 	}
    
    private boolean isRememberMeAuthenticated() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
           return false;
        }

        return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
     }
    
    @PostMapping("/sell/saveProfilePhoto") 
    public String saveStoreProfilePhoto(@Valid StoreProfilePhotoForm profilePhotoForm,Principal principal, HttpServletRequest model, Errors errors, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
    	try{
    		Merchant user = merchantService.setMerchantUserDetailsToModel(model, principal);

        
    		//check if photo is empty
    		if (profilePhotoForm.getProfilePhoto().isEmpty()) {
    			System.out.println("The file was empty!");
    			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
    			return "redirect:/sell/manage";
    		}
    		
    		
    		try{
    				//delete the previous if it exists 			
	    			Path path = Paths.get(PROFILE_PHOTO_SAVE_PATH +  profilePhotoForm.getUserId() + File.separator + profilePhotoForm.getProfilePhoto().getOriginalFilename());
	    	
	    			System.out.println("^^^^^^^^" + path.toString());
	    			System.out.println("^^^^^^^^ + deleting the file!");
	    			Files.deleteIfExists(path);
	    			

	    			// Get the file and save it to filesystem
	    			byte[] bytes = profilePhotoForm.getProfilePhoto().getBytes();
	    			Files.write(path, bytes);
	    			System.out.println("^^^^^^^^ about to attempt to ipdate the profile photo.");
	    			merchantService.updateProfilePhoto(user.getUserId(),  profilePhotoForm.getProfilePhoto().getOriginalFilename());
	    			redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" +  profilePhotoForm.getProfilePhoto().getOriginalFilename() + "'");
    		} catch (IOException e) {
    			e.printStackTrace();
    			System.out.println("*****IO Exceptoin caught");
    		}
    		
		}catch(ClassCastException cce){
			System.out.println("not logged in");
			return "merchantMain";
		}
    		     
        return "redirect:/sell/manage";
    }
    
    @RequestMapping(value="/sell/saveTagLine",method=POST,produces="text/plain;charset=UTF-8") // //new annotation since 4.3
    public String saveStoreStoreTagLine(@Valid ProfileTagLineForm profileTagLineForm, HttpServletRequest model, Errors errors, Principal principal,RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
    	try{
    	Merchant user = merchantService.setMerchantUserDetailsToModel(model, principal);

        merchantService.updateStoreTagLine(user.getUserId(),  profileTagLineForm.getProfileTagLine());
		}catch(ClassCastException cce){
			System.out.println("not logged in");
			return "merchantMain";
		}
        return "redirect:/sell/manage";
    }
    
    @RequestMapping( value={"/sell/store/{storename}"})
    public String showPublicStoreViewToMerchant(@PathVariable("storename") String storeName, HttpServletRequest model, Principal principal){
  		Merchant merchant = null;
  		Merchant profile = null;

		//Set user details
		try{
			merchant= merchantService.setMerchantUserDetailsToModel(model, principal);
			profile=merchantService.setMerchantStoreDataToModel(model, storeName);

		}catch(Exception e){
			System.out.println("user not logged in" + e);
			
			return "merchantMain";
		}
			
	 	
			Connection connection = null;
			connection = connectionService.findConnectionByUsers(merchant.getUserId(), profile.getUserId());
	    	if(connection !=null){
	    		model.setAttribute("connection", connection);
	    	}
	    	
	  		model.getSession().setAttribute("home", "/sell/");
	  		model.setAttribute("viewType", "home");
	  		model.setAttribute("profileType", "store");
	  		model.setAttribute("banner", "storeBanner");
	    	return "userProfile";
    }
    
	@RequestMapping(value={"/sell/profile/{userId}/{username}"}, method=GET)
	public String showCustomerProfileToMerchant(@PathVariable("userId") String userId, @PathVariable("username") String username, HttpServletRequest model, Principal principal){
		Merchant merchant = null;
		Customer profile = null;
		
		try{
				merchant = merchantService.setMerchantUserDetailsToModel(model, principal);
				profile = customerService.setCustomerProfileDataToModel(model, username);	
		}catch(ClassCastException | NullPointerException cce){
			System.out.println("user not logged in");
			return "main";
		}		
 	
		Connection connection = null;
		connection = connectionService.findConnectionByUsers(merchant.getUserId(), profile.getUserId());
    	if(connection !=null){
    		model.setAttribute("connection", connection);
    	}
    	
		model.setAttribute("viewType", "home");
		model.getSession().setAttribute("home", "/sell/");
		model.setAttribute("profileType", "profile");
		model.setAttribute("banner", "profileBanner");
    	return "userProfile";
	}
    
    @PostMapping("/sell/updateProfileBanner") // //new annotation since 4.3
    public String saveStoreProfileBanner(@Valid ProfileBannerForm storeBannerForm, HttpServletRequest model, Errors errors, RedirectAttributes redirectAttributes, Principal principal) throws IllegalStateException, IOException {
    
		try{
	    	Merchant user = merchantService.setMerchantUserDetailsToModel(model, principal);

	    	model.setAttribute("user", user);
        
		if (storeBannerForm.getProfileBanner().isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/sell/manage";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = storeBannerForm.getProfileBanner().getBytes();
            Path path = Paths.get(STORE_BANNER_SAVE_PATH +  storeBannerForm.getUserId() + File.separator +  storeBannerForm.getProfileBanner().getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded '" +  storeBannerForm.getProfileBanner().getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }catch (MultipartException me){ 
        		System.out.println("");
        		model.setAttribute("sizeExceeded", "size must be less than 5 mb");
        		return "redirect:/sell/manage";
        }catch(Exception e){
    		System.out.println("");
    		model.setAttribute("sizeExceeded", "size must be less than 5 mb");
    		return "redirect:/sell/manage";
        }
        merchantService.updateStoreBanner(user.getUserId(),  storeBannerForm.getProfileBanner().getOriginalFilename());
		}catch(ClassCastException cce){
			System.out.println("user not logged in");
			return "merchantMain";
		}
        return "redirect:/sell/manage";
    }
	
	@RequestMapping(value="/sell/register", method=GET)
	public String showRegistrationForm(Model model){
		
		model.addAttribute("merchantForm", new MerchantForm());
		return "merchantRegister";	
	}
	
	@RequestMapping(value="/sell/register", method=POST)
	public String processMerchantRegistration(@Valid MerchantForm merchant, Errors errors, BindingResult result, RedirectAttributes model, HttpServletRequest request){
		System.out.println("inside merchant registration");
		boolean hasErrors = false;
		
		//1.  Check if phone and store name are unique
		//2.  If either are  not unique send error msgs
		//3.  Check if all other field criteria is met
		//4.  If not, send error msgs
		//5.  Otherwise, save the merchant and redirect to merchant page
		
		//check if username is unique across all users
		if(!merchantService.isUsernameUnique(merchant.getUsername())){
			System.out.println("username not unique");
			FieldError userNameError = new FieldError("merchant", "username", messageSource.getMessage("non.unique.username",  new String[]{merchant.getUsername()}, Locale.getDefault()));
			result.addError(userNameError);
			hasErrors=true;
		}
		
		//check if phone is unique
        if(!merchantService.isMerchantPhoneUnique(merchant.getPhone())){
            FieldError phoneError =new FieldError("merchant","phone",messageSource.getMessage("non.unique.phone", new String[]{merchant.getPhone()}, Locale.getDefault()));
            result.addError(phoneError);
            hasErrors = true;
        }
        //check if store name is unique
        if(!merchantService.isStoreNameUnique(merchant.getStoreName())){
            FieldError storeNameError =new FieldError("merchant","storeName",messageSource.getMessage("non.unique.storeName", new String[]{merchant.getStoreName()}, Locale.getDefault()));
            result.addError(storeNameError);
            hasErrors = true;
        }
        
        if(hasErrors|errors.hasErrors()){
        	System.out.println(errors.getObjectName());
       		System.out.println(errors.getFieldError());
       		System.out.println(errors.getGlobalError());
       		List<ObjectError> l = errors.getAllErrors();
       		for(ObjectError oe : l){
       			System.out.println(oe.getObjectName());
   				System.out.println(oe.getDefaultMessage());
       		}
        	return "merchantRegister";
        }

		
        System.out.println("registration form has no errors");
       
        Merchant merch = new Merchant(merchant);
        merch.setProfilePhoto("94333aa537b6e5cb945b67bd58988e43.jpg");
        merch.setProfileBanner("weed.jpg");
        
        merchantService.saveMerchant(merch);
	    System.out.println("user has been saved in db");
	    merchantStatusService.saveMerchantStatus(
	    			new MerchantStatus(
	    					merch.getUserId(), 
	    					new String("New User Registration"), 
	    					new String("offline"), 
	    					new Date(), 
	    					new String(""), 
	    					new String("")));
	    System.out.println("merchant status has been saved in db");
		model.addFlashAttribute("merchant", merch);
		System.out.println("flash attributes added");
		try {
			request.login(merchant.getUsername(),merchant.getPassword());
		} catch(ServletException e) {
			// fail to authenticate
			}
	    
		System.out.println("merchant successfully authenticated");
		return "redirect:/sell/manage"; 
				
	}
    
	@RequestMapping(value="/sell/home", produces="text/plain;charset=UTF-8")
	public String showMerchantHome(HttpServletRequest model, Principal principal){
		System.out.println("inside merchant home");
 
		try{
			Merchant merchant =	merchantService.setMerchantUserDetailsToModel(model, principal);
			merchantService.setMerchantStoreDataToModel(model, merchant.getStoreName());	
			//cartService.heartIconStatusCheck(merchant.getUserId(), "merchant", model);
	 		producer.createHeartNotification(model, "increment", merchant.getUserId(), "merchant");	
		}catch(ClassCastException cce){
			System.out.println("user not logged in");
			return "merchantMain";
		}
		
		model.getSession().setAttribute("home", "/sell/");
		model.setAttribute("viewType", "profile");
		model.setAttribute("profileType", "store");
		model.setAttribute("mode", "edit");
		model.setAttribute("banner", "storeBanner");
		model.setAttribute("upload", "/sell/uploadProduct");
		return "userProfile";
	 
		
	}

	@RequestMapping(value="/sell/merchantControlMenu", method=POST)
	public String processMerchantControlMenuChoice(@Valid MerchantControlMenuForm form, Errors errors, BindingResult result,  HttpServletRequest model,  RedirectAttributes redirectAttributes){
			System.out.println("inside mercant control menu");
			String choice = form.getMerchantControlMenuChoice();
			redirectAttributes.addFlashAttribute("merchantControlMenuForm", form);

			if(choice.equals("Turn Store On/Off")){	
				MerchantStatus currentStatus = merchantStatusService.findMerchantStatusByMerchantId(((Merchant)model.getSession().getAttribute("merchant")).getUserId());
				model.getSession().setAttribute("currentStatus", currentStatus);
				 return "redirect:/sell/status";
				 
			}else if(choice.equals("Privacy")){
				return "redirect:/sell/privacy";
			
			}else if(choice.equals("Add a New Product")){
				System.out.println("add a new product was chosen");
				return "redirect:/sell/uploadProduct";
			
			}else if(choice.equals("Delete Product")){	
				return "showProductDelete";
			
			}else if(choice.equals("View All Inventory")){
				return "merchantViewInventory";
			
			}else if(choice.equals("Search Inventory")){
				return "merchantSearchInventory";
			
			}else if(choice.equals("View/Edit Notebook")){	
				return "merchantNotebook";
			}else if(choice.equals("Connection Requests")){
				return "redirect:/sell/connectionRequests";	
			}
			
			return "merchantHome";			
	}
	
    @GetMapping("/sell/status")
    public String changeStoreStatus() {

        return "toggleStoreOnOff";
    }
    
    //merchant show new product upload form
    @RequestMapping(value="/sell/uploadProduct", method=GET)
    public String enterNewProduct(HttpServletRequest model, RedirectAttributes redirectAttributes, Principal principal) {
    	try{
	    	Merchant merchant = merchantService.setMerchantUserDetailsToModel(model, principal);
			model.setAttribute("merchantControlMenuForm", new MerchantControlMenuForm("Upload Product"));
			System.out.println("In merchant controller, merchant " + merchant.getUserId() + " is going to make a new product.");
			model.setAttribute("productForm", new ProductForm());
			System.out.println("set product form");
		}catch(ClassCastException cce){
			System.out.println("user not logged in");
			return "merchantMain";
		}
		return "showProductUpload";
    }
    
    //merchant submit new product upload form
    @RequestMapping(value="/sell/uploadProduct", method=POST) 
    public String saveNewProduct(@Valid ProductForm productForm, Errors errors, HttpServletRequest model, Principal principal) throws IllegalStateException, IOException {
    	
    	System.out.println("Inside saveNewProduct method");
		
    try{
    	Merchant merchant = merchantService.findByUsername(principal.getName());
    	merchantService.setMerchantStoreDataToModel(model, merchant.getStoreName() );
    	
 		System.out.println("In controller, userId in productForm is: " + productForm.getUserId());
        if (errors.hasErrors()) {
        	System.out.println("Errors were found in error object");
            return "showProductUpload";
        }
          
        Product product = productForm.toProduct(merchant);
        System.out.println("Merchant id inside product object is: " + product.getMerchant().getUserId());
        System.out.println("Product name inside product object is: " + product.getProductName());
        System.out.println("Product descrip inside product object is: " + product.getProductDescription());
        System.out.println("price1 inside product object is: " + product.getPrice1());
        System.out.println("price2 inside product object is: " + product.getPrice2());
        System.out.println("price3 inside product object is: " + product.getPrice3());
        System.out.println("price4 inside product object is: " + product.getPrice4());
        System.out.println("price5 inside product object is: " + product.getPrice5());
        System.out.println("imagePath inside product object is: " + product.getImagePath1());        
        System.out.println("imagePath inside product object is: " + product.getImagePath2());
        System.out.println("imagePath inside product object is: " + product.getImagePath3());
        System.out.println("imagePath inside product object is: " + product.getImagePath4());
        System.out.println("imagePath inside product object is: " + product.getImagePath5());
        System.out.println("quantity inside product object is: " + product.getQuantity1());        
        System.out.println("quantity inside product object is: " + product.getQuantity2()); 
        System.out.println("quantity inside product object is: " + product.getQuantity3()); 
        System.out.println("quantity inside product object is: " + product.getQuantity4()); 
        System.out.println("quantity inside product object is: " + product.getQuantity5()); 
        for(int i=1; i<=5;i++){
        	saveProductPhotoToFilesystem(productForm, product, i);
        	
        }
        productService.saveNewProduct(product);
		}catch(ClassCastException cce){
			System.out.println("not logged in");
			return "merchantMain";
		}
 
  		return "redirect:/sell/home";
      }
     
    
    private void saveProductPhotoToFilesystem(ProductForm productForm, Product product, int index){
        
    	//Product previous = productService.findProductByProductId(product.getProductId());
        
        if(index==1){
	        	try{
	        		System.out.println("!!!!!!!!!!!!#1");
	        		String path = PRODUCT_PHOTO_SAVE_PATH1 + product.getMerchant().getUserId() + File.separator + product.getImagePath1();
	        		File file = new File(path);
	        		int fileNo = 0;
	        		String newFileName = path;
	        	    if (file.exists() && !file.isDirectory()) {
	        	    	
	        	        while(file.exists()){
	        	            fileNo++;
	        	            newFileName = path + "(" + fileNo + ").png";
	        	            file = new File(newFileName);
	        	        }
	        	        productForm.getProductPhoto1().transferTo(file);


	        	    } else if (!file.exists()) {
	        	    	productForm.getProductPhoto1().transferTo(file);
	        	    }
	        		
	 
	            }catch(MultipartException | IOException  io){ 
	            		System.out.println("Could not save the files"+ io); 
	            }
        }else if(index==2){
        	try{
        		System.out.println("!!!!!!!!!!!!#2");
        		String path = PRODUCT_PHOTO_SAVE_PATH2 + product.getMerchant().getUserId() + File.separator + product.getImagePath2();
        		File file = new File(path);
        		int fileNo = 0;
        		String newFileName = path;
        	    if (file.exists() && !file.isDirectory()) {
        	    	
        	        while(file.exists()){
        	            fileNo++;
        	            newFileName = path + "(" + fileNo + ").png";
        	            file = new File(newFileName);
        	        }
        	        productForm.getProductPhoto2().transferTo(file);


        	    } else if (!file.exists()) {
        	    	productForm.getProductPhoto2().transferTo(file);
        	    }
        		
 
            }catch(MultipartException | IOException  io){ 
            		System.out.println("Could not save the files"+ io); 
            }
        }else if(index==3){
    	try{
    		System.out.println("!!!!!!!!!!!!#3");
    		String path = PRODUCT_PHOTO_SAVE_PATH3 + product.getMerchant().getUserId() + File.separator + product.getImagePath3();
    		File file = new File(path);
    		int fileNo = 0;
    		String newFileName = path;
    	    if (file.exists() && !file.isDirectory()) {
    	    	
    	        while(file.exists()){
    	            fileNo++;
    	            newFileName = path + "(" + fileNo + ").png";
    	            file = new File(newFileName);
    	        }
    	        productForm.getProductPhoto3().transferTo(file);


    	    } else if (!file.exists()) {
    	    	productForm.getProductPhoto3().transferTo(file);
    	    }
    		

        }catch(MultipartException | IOException  io){ 
        		System.out.println("Could not save the files"+ io); 
        }
    }else if(index==4){
        	try{
        		System.out.println("!!!!!!!!!!!!#4");
        		String path = PRODUCT_PHOTO_SAVE_PATH4 + product.getMerchant().getUserId() + File.separator + product.getImagePath4();
        		File file = new File(path);
        		int fileNo = 0;
        		String newFileName = path;
        	    if (file.exists() && !file.isDirectory()) {
        	    	
        	        while(file.exists()){
        	            fileNo++;
        	            newFileName = path + "(" + fileNo + ").png";
        	            file = new File(newFileName);
        	        }
        	        productForm.getProductPhoto4().transferTo(file);


        	    } else if (!file.exists()) {
        	    	productForm.getProductPhoto4().transferTo(file);
        	    }
        		
 
 
            }catch(MultipartException | IOException  io){ 
            		System.out.println("Could not save the files"+ io); 
            }
    }else if(index==5){
    	try{
    		System.out.println("!!!!!!!!!!!!#1");
    		String path = PRODUCT_PHOTO_SAVE_PATH5 + product.getMerchant().getUserId() + File.separator + product.getImagePath5();
    		File file = new File(path);
    		int fileNo = 0;
    		String newFileName = path;
    	    if (file.exists() && !file.isDirectory()) {
    	    	
    	        while(file.exists()){
    	            fileNo++;
    	            newFileName = path + "(" + fileNo + ").png";
    	            file = new File(newFileName);
    	        }
    	        productForm.getProductPhoto5().transferTo(file);


    	    } else if (!file.exists()) {
    	    	productForm.getProductPhoto5().transferTo(file);
    	    }
    		

        }catch(MultipartException | IOException  io){ 
        		System.out.println("Could not save the files"+ io); 
        }
     }
    }
    
    

	
	@RequestMapping(value={"/sell/settings"}, method=GET)
	public  String updateMerchantStoreSettings(HttpServletRequest request){			
			Merchant merchant = null;
			
			
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			//Set user details
			try{
						merchant  = merchantService.findByUsername(userDetails.getUsername());
						if(merchant!=null){
							MerchantStatus currentStatus = merchantStatusService.findMerchantStatusByMerchantId(merchant.getUserId());
							request.setAttribute("currentStatus", currentStatus);
							request.setAttribute("merchantVisibilityForm", new MerchantVisibilityForm(merchant.getUserId(), merchant.getVisibility()));
							request.setAttribute("merchantStatusForm",  new MerchantStatusForm());
							request.setAttribute("merchant", merchant);
							request.setAttribute("userType", "merchant");					
							
						}
			}catch(ClassCastException e){
				System.out.println("user not logged in" + e);
				
				return "merchantMain";
			}
			request.setAttribute("viewType", "profile");
	
		 	return "merchantSettings";
	 }
    
	@RequestMapping(value="/sell/product/delete/{productId}", method=GET)
	public  String submitProductEditForm(@PathVariable("productId") String productId, RedirectAttributes redirect, HttpServletRequest request, Principal principal){
			try{
				Merchant m = merchantService.setMerchantUserDetailsToModel(request, principal);
			}catch(ClassCastException cce){
				System.out.println("not  logged in" + cce);
				return "merchantMain";
			}
			Product product = productService.findProductByProductId(productId);
		    productService.deleteProduct(product);
		    redirect.addFlashAttribute("deleteSuccess", "true");
		    	
		 	return "redirect:/sell/manage/";
	  }
		         
    
	@RequestMapping(value="/sell/product/editProductSave", method=POST)
	public  String submitProductEditForm(@Valid ProductForm productForm, Errors errors, HttpServletRequest request){
		
		   try{
		    	Merchant merchant = merchantService.findByMerchantId(productForm.getUserId());
		    	
		 		System.out.println("In controller, userId in productForm is: " + productForm.getUserId());
		        if (errors.hasErrors()) {
		        	System.out.println("Errors were found in error object");
		            return "showProductUpload";
		        }
		          
		        Product product = productForm.toProduct(merchant, productForm.getProductId());
		        System.out.println("Merchant id inside product object is: " + product.getMerchant().getUserId());
		        System.out.println("Product name inside product object is: " + product.getProductName());
		        System.out.println("Product descrip inside product object is: " + product.getProductDescription());
		        System.out.println("price1 inside product object is: " + product.getPrice1());
		        System.out.println("price2 inside product object is: " + product.getPrice2());
		        System.out.println("price3 inside product object is: " + product.getPrice3());
		        System.out.println("price4 inside product object is: " + product.getPrice4());
		        System.out.println("price5 inside product object is: " + product.getPrice5());
		        System.out.println("imagePath inside product object is: " + product.getImagePath1());        
		        System.out.println("imagePath inside product object is: " + product.getImagePath2());
		        System.out.println("imagePath inside product object is: " + product.getImagePath3());
		        System.out.println("imagePath inside product object is: " + product.getImagePath4());
		        System.out.println("imagePath inside product object is: " + product.getImagePath5());
		        System.out.println("quantity inside product object is: " + product.getQuantity1());        
		        System.out.println("quantity inside product object is: " + product.getQuantity2()); 
		        System.out.println("quantity inside product object is: " + product.getQuantity3()); 
		        System.out.println("quantity inside product object is: " + product.getQuantity4()); 
		        System.out.println("quantity inside product object is: " + product.getQuantity5()); 
		        Product previous = productService.findProductByProductId(productForm.getProductId());
		        
		        if(!product.getImagePath1().isEmpty()){
		        	saveProductPhotoToFilesystem(productForm, product, 1);
		        }else{
		        	product.setImagePath1(previous.getImagePath1());
		        }
		        
		        if(!product.getImagePath2().isEmpty()){
		        	saveProductPhotoToFilesystem(productForm, product, 2);
		        }else{
		        	product.setImagePath2(previous.getImagePath2());
		        }
		        
		        if(!product.getImagePath3().isEmpty()){
		        	saveProductPhotoToFilesystem(productForm, product, 3);
		        }else{
		        	product.setImagePath3(previous.getImagePath3());
		        }
		        if(!product.getImagePath4().isEmpty()){
		        	saveProductPhotoToFilesystem(productForm, product, 4);
		        }else{
		        	product.setImagePath4(previous.getImagePath4());
		        }
		        if(!product.getImagePath5().isEmpty()){
		        	saveProductPhotoToFilesystem(productForm, product, 5);
		        }else{
		        	product.setImagePath5(previous.getImagePath5());
		        }
		        Product newProduct = productService.updateProduct(product);
			}catch(ClassCastException cce){
					System.out.println("not logged in");
					return "merchantMain";
				}
		return "redirect:/sell/product/" + productForm.getProductId();
		
	}
    
	
	@RequestMapping(value="/sell/connectionRequests", method=GET)
	public String viewConnectionRequests(HttpServletRequest model,Principal principal){
		try{
			Merchant user = merchantService.setMerchantUserDetailsToModel(model, principal);
	 		List<Connection> connections = connectionService.findConnectionRequestsByRequesteeId(user.getUserId());
	 		model.getSession().setAttribute("connections", connections);
	 		
	 		model.setAttribute("merchantControlMenuForm", new MerchantControlMenuForm());
		}catch(ClassCastException cce){
			System.out.println("not logged in");
			return "merchantMain";
		}
	 		return "connectionRequests";	
	}
	
	@RequestMapping(value="/sell/privacy")
	public String changePrivacy(HttpServletRequest model, RedirectAttributes redirectAttributes, Principal principal){
		try{
			Merchant user = merchantService.setMerchantUserDetailsToModel(model, principal);

			if(!redirectAttributes.containsAttribute("merchantControlMenuForm")){ 
			model.setAttribute("merchantControlMenuForm", new MerchantControlMenuForm("Privacy"));
			}
			model.setAttribute("merchantVisibilityForm", new MerchantVisibilityForm(user.getUserId(), user.getVisibility()));
		}catch(ClassCastException cce){
			System.out.println("not logged in");
			return "merchantMain";
		}
 		return "merchantPrivacy";
 		
	}
	
	@RequestMapping(value="/sell/appointments")
	public String showAppointmentQueue(HttpServletRequest model, Principal principal) throws SessionExpiredException {
    	Merchant merchant = null;
		try{
    		//set user in session
			merchant = merchantService.setMerchantUserDetailsToModel(model, principal);
    	}catch(ClassCastException cce){ 
    			System.out.println("User not logged in");
    			return "merchantMain";
    	}
		
		if(merchant==null){
			return "merchantMain";
		}
			
		//set inbox messages in session
		setInboxMsgsToSession(merchant.getUserId(), model);
			
		//set like messages in session
		setLikeMsgsToSession(merchant.getUserId(), model);
		
		//set appointments in session
		setAppointmentsToSession(merchant.getUserId(), model);
		
 		//set connections in session
 		setConnectionsToSession(merchant.getUserId(), model);
 		model.setAttribute("navButtonSelected", "cart");
 		model.setAttribute("viewType", "cart");
 		
 		
 		
		return "merchantCart";	
	}
	
	@RequestMapping(value="/sell/appointment/{appointmentId}")
	public String viewAppointment(@PathVariable String appointmentId, HttpServletRequest model, Principal principal){
			try{
				merchantService.setMerchantUserDetailsToModel(model, principal);

	 		
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
	 		String formattedTimeReceived = appointmentService.formatDate(appointment.getTimeReceived()); 
	 		model.setAttribute("formattedTimeReceived", formattedTimeReceived);
	 		model.getSession().setAttribute("appointment", appointment);
	 		//List<AppointmentItem> appointmentItems = appointmentService.findAppointmentItemsByAppointmentId(appointment.getAppointmentId());
	 		//model.setAttribute("appointmentItems" , appointmentItems);
	 		Customer c = customerService.findCustomerByCustomerId(appointment.getUserId());
	 		model.setAttribute("customerProfilePic", c.getProfilePhoto());
	 		model.setAttribute("formattedAddress", appointment.getSearchTextField().replace("," , "<br/>"));
	 		model.setAttribute("merchantControlMenuForm", new MerchantControlMenuForm());
			}catch(ClassCastException cce){
				System.out.println("not logged in");
				return "merchantMain";
			}
	 		model.setAttribute("navButtonSelected", "inbox");
			model.setAttribute("viewType", "cart");
	 		return "merchantViewAppointment";	
	}
	
	@RequestMapping(value="/sell/notebook/{userId}")
	public String showNotebook(@PathVariable String userId, HttpServletRequest model, Principal principal){
		try{
			merchantService.setMerchantUserDetailsToModel(model, principal);
		}catch(ClassCastException cce){
			System.out.println("not logged in");
			return "merchantMain";
		}
		Customer profile = customerService.findCustomerByCustomerId(userId);
		List<Appointment> appointments =  appointmentService.findAppointmentsByCustomerId(userId);
 		List<Note> notes = noteService.findNotesByCustomerId(userId);
 		model.setAttribute("profile", profile);
 		model.setAttribute("notes", notes);
 		model.setAttribute("appointments", appointments);
 		model.setAttribute("note", new Note());
		model.setAttribute("viewType", "cart");
		return "merchantViewNotebook";
	}
	
	
	
	@RequestMapping(value="/sell/notebook/addNote/{userId}", method=GET)
	public String showNewNoteForm(@PathVariable("userId") String userId, HttpServletRequest model, Principal principal){
		Merchant user = null;
		try{
			user = merchantService.setMerchantUserDetailsToModel(model, principal);
		}catch(ClassCastException cce){
			System.out.println("not logged in");
			return "merchantMain";
		}
		Customer profile = customerService.findCustomerByCustomerId(userId);
 		List<Note> notes = noteService.findNotesByCustomerId(userId);
 		model.setAttribute("profile", profile);
 		model.setAttribute("user", user);
 		model.setAttribute("note", new Note());
		model.setAttribute("viewType", "cart");
		return "merchantNotebook";
	}
	
	@RequestMapping(value="/sell/notebook/addNote", method=POST)
	public String submitNoteToDb(@Valid Note note, Errors errors, HttpServletRequest model, Principal principal){
		try{
			merchantService.setMerchantUserDetailsToModel(model, principal);
		}catch(ClassCastException cce){
			System.out.println("not logged in");
			return "merchantMain";
		}
		
 		Date dt = new Date();
 		note.setEntryDate(dt);
 		noteService.saveNewNote(note);
		
		return "redirect:/sell/notebook/" + note.getUserId();
	}
	

	@RequestMapping(value="/sell/manage", method=GET)
	public String showCustomerProfileManagementConsole(HttpServletRequest model, Principal principal){
		Merchant merchant = null;
		try{
			merchant = merchantService.findByUsername(principal.getName());
			merchantService.setMerchantStoreDataToModel(model, merchant.getStoreName());
		}catch(ClassCastException cce){
			System.out.println("not logged in");
			return "merchantMain";
		}
		return "forward:/sell/home";
	}
	
	@RequestMapping(value={"/sell/photo/{userPhotoId}"})
	public String merchantViewUserPhoto(@PathVariable("userPhotoId") String userPhotoId, HttpServletRequest request , Principal principal){
		try{
			Merchant merchant = merchantService.setMerchantUserDetailsToModel(request, principal);
		}catch(ClassCastException | NullPointerException cce){
				System.out.println("user was not  logged in");
				return "main";
		};
		
		UserPhoto photo = userPhotoService.findPhotoByPhotoId(userPhotoId);
		System.out.println("User photo is: " + photo.getUserPhotoId());
		request.setAttribute("photo", photo);	
		return "photo";	
	}
	
    @ExceptionHandler(HttpSessionRequiredException.class)
	@RequestMapping(value="/sell/inbox")
	public String showMerchantInbox(HttpServletRequest model, Principal principal) throws SessionExpiredException {
    	Merchant merchant = null;
		try{
    		//set user in session
			merchant = merchantService.setMerchantUserDetailsToModel(model, principal);
    	}catch(ClassCastException cce){ 
    			System.out.println("User not logged in");
    			return "merchantMain";
    	}
		
		if(merchant==null){
			return "merchantMain";
		}
			
		//set inbox messages in session
		setInboxMsgsToSession(merchant.getUserId(), model);
			
		//set like messages in session
		setLikeMsgsToSession(merchant.getUserId(), model);
		
		//set appointments in session
		setAppointmentsToSession(merchant.getUserId(), model);
		
 		//set connections in session
 		setConnectionsToSession(merchant.getUserId(), model);
 		producer.createHeartNotification(model, "increment", merchant.getUserId(), "merchant");	
 		

 		
 		model.setAttribute("viewType", "cart");
 		
 		
		return "merchantCart";	
		
	}
    
 	@RequestMapping(value="/sell/message/{messageId}" )
 	public String showMessage(@PathVariable("messageId") String messageId, HttpServletRequest model, Principal principal) throws SessionExpiredException {
     	Merchant merchant = null;
 		try{
     		//set user in session
 			merchant = merchantService.setMerchantUserDetailsToModel(model, principal);
     	}catch(ClassCastException cce){ 
     			System.out.println("User not logged in");
     			return "main";
     	}
 		
 		if(merchant==null){
 			return "main";
 		}
 			
 		//set cart in session
 		setAppointmentsToSession(merchant.getUserId(), model);
 			
 		//set inbox messages in session
 		setInboxMsgToSession(merchant.getUserId(), messageId, model);
 			
 		
 		//set like messages in session
 		setLikeMsgsToSession(merchant.getUserId(), model);
 		
 		//set connections in session
 		setConnectionsToSession(merchant.getUserId(), model);
 		model.setAttribute("navButtonSelected", "inbox");
 		model.setAttribute("viewType", "cart");
 		return "merchantViewInboxMessage";	
 		
 	}
 	
	
	private Merchant setMerchantUserDetailsToModel(HttpServletRequest model){
		Merchant merchant;
		try{
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		merchant  = merchantService.findByUsername(userDetails.getUsername());
		}catch(ClassCastException cce){
			System.out.println("not logged in");
			throw cce;
		}
		model.getSession().setAttribute("merchant", merchant);
 		producer.createHeartNotification(model, "increment", merchant.getUserId(), "merchant");	
		return merchant;
	}
	
	private Merchant addMerchantUserDetailsToModel(Model model){
		Merchant merchant;
		try{
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		merchant  = merchantService.findByUsername(userDetails.getUsername());
		}catch(ClassCastException cce){
			System.out.println("not logged in");
			throw cce;
		}
		model.addAttribute("merchant", merchant);

		return merchant;
	}
	
//	private Merchant addMerchantStoreDataToModel(Model model, Principal principal){
//		Merchant merchant = merchantService.addMerchantUserDetailsToModel(model, principal);
//		List<Connection> connectedMerchants = connectionService.findUserConnectionsByType(merchant.getUserId(), "merchant");	
//    	List<Connection> connectedFriends = connectionService.findUserConnectionsByType(merchant.getUserId(), "customer");
//    	Integer connFriendsSize = connectedFriends.size();
//    	Integer connMerchantsSize = connectedMerchants.size();
//    	Integer productsSize = merchant.getProducts().size();
//    	model.addAttribute("connectedMerchants", connectedMerchants);
//    	model.addAttribute("connectedFriends", connectedFriends);
//    	model.addAttribute("connFriendsSize", connFriendsSize);
//    	model.addAttribute("connMerchantsSize", connMerchantsSize);
//    	model.addAttribute("productsSize", productsSize);
//    	System.out.println("size of connected merchants:" + connectedMerchants.size());
//    	System.out.println("size of connected friends:" + connectedFriends.size());
//    	model.addAttribute("profilePhotoForm", new ProfileBannerForm());
//		model.addAttribute("merchantControlMenuForm", new MerchantControlMenuForm());
//		return merchant;
//	}
	

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
    private void setLikeMsgsToSession(String userId, HttpServletRequest model){
		//set inbox messages in session
		List<LikeMessage> likeMsgs = likeMessageService.findLikeMessagesByMerchantId(userId);
		
		model.getSession().setAttribute("likeMsgs", likeMsgs);
		
    }
    
    private void setAppointmentsToSession(String userId, HttpServletRequest model){
		//set inbox messages in session
    	System.out.println("Inside set Appointments To Session");
    	System.out.println("user id is:" + userId);
		List<Appointment> appointments = appointmentService.findAppointmentByMerchantId(userId);
		System.out.println("appointments size is:  "  + appointments.size());
		model.getSession().setAttribute("appointments", appointments);
		Integer queueSize = 0;
		for(Appointment a : appointments){
			if(a.getQueueNumber()>0){
				queueSize = queueSize + 1;
			}
		}
		model.getSession().setAttribute("queueSize", queueSize);
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
				messageService.markMessageRead(new ReadTimeForm("merchant", userId, messageId, formattedDate), model);
//				Merchant merchant = (Merchant)model.getAttribute("merchant");
//				merchant.setNotifications(merchant.getNotifications() - 1);
//				model.getSession().setAttribute("merchant", "merchant");
	        }catch(ParseException p){System.out.println("could not parse the date"); }
	     
		}
		
		model.setAttribute("message", message);

    }
    
    private void  setInboxMsgsToSession(String userId, HttpServletRequest model){
		//set inbox messages in session
		List<Message> messages = messageService.findMessagesByMerchantId(userId);
		model.getSession().setAttribute("messages", messages);
    }
	
  	@RequestMapping(value="/sell/changePassword", method = RequestMethod.POST)
  	public String changePassword(@Valid PasswordForm form, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirect){
  		
  		String phone = (String)request.getSession().getAttribute("phone");
  		System.out.println("^^^^^^^ Inside change password, PHONE IS: " + phone);
  	  Merchant merchant = merchantService.findByPhone(phone);
  	  System.out.println("merchant name is: " + merchant.getUsername());
  	  System.out.println("password is: " + form.getPassword());
  	  userService.updatePassword(merchant.getUsername(), form.getPassword());
  	  merchant.setPassword(form.getPassword());
  	  //authenticate the user and log in.
  	  //authenticateUserAndSetSession(merchant, request);
  		try {
  			request.login(merchant.getUsername(),merchant.getPassword());
  		} catch(ServletException e) {
  			// fail to authenticate
  		}
  	    
  		System.out.println("merchant successfully authenticated");
  		
  		redirect.addFlashAttribute("merchant", merchant);
  		System.out.println("flash attribute added");
  	  return "redirect:/sell/home";
  		
  	}
  	  
	

	
}