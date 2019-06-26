package com.yanya.springmvc.service;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanya.springmvc.dao.AppointmentDao;
import com.yanya.springmvc.model.AppointmentItem;
import com.yanya.springmvc.dao.AppointmentItemDao;
import com.yanya.springmvc.dao.AppointmentRequestDao;
import com.yanya.springmvc.model.Appointment;
import com.yanya.springmvc.model.AppointmentItem;
import com.yanya.springmvc.model.AppointmentRequest;
import com.yanya.springmvc.model.CartItem;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.service.AppointmentService;
import com.yanya.springmvc.service.MerchantService;
import com.yanya.springmvc.service.MerchantServiceImpl;

import java.lang.Integer;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service("appointmentService")
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
 
    @Autowired
    private AppointmentRequestDao appointmentRequestDao;
    
    @Autowired
    private AppointmentDao appointmentDao;
    
    @Autowired
    private AppointmentItemDao appointmentItemDao;
    
    
    public AppointmentRequest saveNewAppointmentRequest(AppointmentRequest appointmentRequest) {
    	return appointmentRequestDao.saveNewAppointmentRequest(appointmentRequest);
    }
    
    public 	List<Appointment> findAppointmentsByCustomerIdAndMerchantId(String userId, String merchantId){
    	return appointmentDao.findAppointmentsByCustomerIdAndMerchantId(userId, merchantId);
    }
    
    public List<Appointment> createNewAppointments(AppointmentRequest apptRequest, Map<String,String> merchants, Map<String,String> merchantPhones,  List<CartItem> cartItems){
    	
    	
    	List<Appointment> appts = new ArrayList<Appointment>();
    	for(Map.Entry<String, String> merchant : merchants.entrySet()){
    		
    		//Generate the accept and reject Ids
    		int rejectId;
    		int acceptId;
    		int completeId;
    		int Low = 10;
    		int High = 100000;
    		do{
	    		Random r = new Random();
	    		acceptId = r.nextInt(High-Low) + Low;
    		}while(  (findByAcceptId(String.valueOf(acceptId))!=null) && (findByRejectId(String.valueOf(acceptId))!=null) && (findByCompleteId(String.valueOf(acceptId))!=null) );
    		
    		do{
    			Random r = new Random();
    			rejectId = r.nextInt(High-Low) + Low;
    		}while( (findByAcceptId(String.valueOf(rejectId))!=null) && (findByRejectId(String.valueOf(rejectId))!=null) && (findByCompleteId(String.valueOf(rejectId))!=null) );
 
    		do{
    			Random r = new Random();
    			completeId = r.nextInt(High-Low) + Low;
    		}while( (findByAcceptId(String.valueOf(completeId))!=null) && (findByRejectId(String.valueOf(completeId))!=null) && (findByCompleteId(String.valueOf(completeId))!=null)  );
  
    		//create the appointment
    		System.out.println("$$$$$ merchant.getKey() " + merchant.getKey());
    		Appointment appointment = new Appointment(apptRequest, apptRequest.getUserId(), apptRequest.getCustomerName(), 
    												 merchant.getKey(), merchant.getValue(), 
    												apptRequest.getSearchTextField(), apptRequest.getLng(), apptRequest.getLat(), 
    												"new" , apptRequest.getPhone(), merchantPhones.get(merchant.getKey()), apptRequest.getTimestamp(), null, null, null, 
    												Integer.toString(acceptId), Integer.toString(rejectId), Integer.toString(completeId), 0);
    		System.out.println(appointment.getCustomerPhone());
    		System.out.println(appointment.getMerchantPhone());
    		System.out.println(appointment.getUserId());
    		appointmentDao.saveNewAppointment(appointment);
    		appts.add(appointment);
    		
    		//create the appointment items
    		for(CartItem c: cartItems){
    			if(merchant.getKey().equals(c.getMerchantId())){
    				AppointmentItem item = new AppointmentItem(appointment, c.getProductId(), c.getQuantity(), c.getUnits(), 
    															c.getPrice(), c.getProductName());
    				appointmentItemDao.saveNewAppointmentItem(item);
    			}
    		}
    		
    		
    	}
    	
    	return appts;
    	
    }
    
   public AppointmentRequest findCustomersLastAppointmentRequest(String userId){
		return appointmentRequestDao.findCustomersLastAppointmentRequest(userId);
	}	
    public List<Appointment> findAppointmentByMerchantId(String userId){
    	return appointmentDao.findAppointmentByMerchantId(userId);
    }
    
    public List<Appointment> findAppointmentsByCustomerId(String userId){
    	return appointmentDao.findAppointmentsByCustomerId(userId);
    }
    
	public List<Appointment> findNewAppointmentsByMerchantId(String userId){
    	return appointmentDao.findNewAppointmentsByMerchantId(userId);
	}
	
	public List<Appointment> findConfirmedAppointmentsByMerchantId(String userId){
    	return appointmentDao.findConfirmedAppointmentsByMerchantId(userId);
	}
	
	public List<Appointment> findPastAppointmentsByMerchantId(String userId){
    	return appointmentDao.findPastAppointmentsByMerchantId(userId);
	}
	
	public List<Appointment> findNewAppointmentsByCustomerId(String userId){
    	return appointmentDao.findNewAppointmentsByCustomerId(userId);
	}
	
	public List<Appointment> findConfirmedAppointmentsByCustomerId(String userId){
    	return appointmentDao.findConfirmedAppointmentsByCustomerId(userId);
	}
	
	public List<Appointment> findPastAppointmentsByCustomerId(String userId){
    	return appointmentDao.findPastAppointmentsByCustomerId(userId);
	}
	
    public Appointment findAppointmentByAppointmentId(String appointmentId){
    	return appointmentDao.findAppointmentByAppointmentId(appointmentId);
    }

    public Appointment findByAcceptId(String id){
    	return appointmentDao.findByAcceptId(id);
    }
    
    public Appointment findByRejectId(String id){
    	return appointmentDao.findByRejectId(id);
    }
    
    public Appointment findByCompleteId(String id){
    	return appointmentDao.findByCompleteId(id);
    }
    
    public void updateAppointmentStatus(String appointmentId, String newStatus){
    	 appointmentDao.updateAppointmentStatus(appointmentId, newStatus);
    }

    public Integer placeConfirmedAppointmentInQueue(Appointment appointment){
    	System.out.println("inside appt service place confirmed appt in QUEUE");
    	Appointment lastAppt = findQueueSizeByMerchantId(appointment.getMerchantId());
    	int newQueueNum = 0;
    	if(lastAppt==null)
    		newQueueNum = 1;
    	else
    		newQueueNum = lastAppt.getQueueNumber() + 1;
    	appointmentDao.placeConfirmedAppointmentInQueue(appointment, newQueueNum);
    	return newQueueNum;
    }
 
    public Appointment findQueueSizeByMerchantId(String userId){
        	return appointmentDao.findQueueSizeByMerchantId(userId);
    }
    
    public void removeAppointmentFromQueue(Appointment appointment){
    	appointmentDao.removeAppointmentFromQueue(appointment);
    	
    }
    
    public void  updateTimeAccepted(String appointmentId, Date date){
    	appointmentDao.updateTimeAccepted(appointmentId, date);
    	
    }
    public void  updateTimeRejected(String appointmentId, Date date){
       	appointmentDao.updateTimeRejected(appointmentId, date);
    }
    
    public void  updateTimeCompleted(String appointmentId, Date date){
       	appointmentDao.updateTimeCompleted(appointmentId, date);
    }
    
    
    public List<AppointmentItem> findAppointmentItemsByAppointmentId(String appointmentId){
    	return appointmentItemDao.findAppointmentItemsByAppointmentId(appointmentId);
    }
	
    public 	String formatDate(Date date){
    	String pattern =  "hh:mmaa MM/dd/yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
 
        // formatting
        System.out.println(format.format(date));
        return format.format(date);
      }
    
    public Date parseDate(Date date){
    	String pattern =  "hh:mmaa MM/dd/yyyy";
    	SimpleDateFormat format = new SimpleDateFormat(pattern);
    	try{
    		date = format.parse(date.toString());
    	}catch(ParseException pe){}
    		return date;
    }
    
    
   
    
 
 
 
 
}