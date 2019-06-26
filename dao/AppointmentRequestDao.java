package com.yanya.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.yanya.springmvc.model.AppointmentRequest;
//import com.yanya.springmvc.model.Cart;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.ZipCode;
 
public interface AppointmentRequestDao {
 
	@Autowired
	AppointmentRequest saveNewAppointmentRequest(AppointmentRequest appointmentRequest);
	
	@Autowired
    AppointmentRequest findAppointmentRequestByAppointmentRequestId(String apppointmentId);
	
	@Autowired
	 AppointmentRequest findCustomersLastAppointmentRequest(String userId);
}