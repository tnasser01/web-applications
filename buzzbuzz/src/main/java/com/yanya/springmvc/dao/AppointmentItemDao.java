package com.yanya.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.yanya.springmvc.model.AppointmentItem;
//import com.yanya.springmvc.model.Cart;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.ZipCode;

import com.yanya.springmvc.model.Appointment;
 
public interface AppointmentItemDao {
 
	@Autowired
	AppointmentItem saveNewAppointmentItem(AppointmentItem appointmentItem);
	
	@Autowired
    AppointmentItem findAppointmentItemByAppointmentItemId(String apppointmentId);
	
	@Autowired
	List<AppointmentItem> findAppointmentItemsByAppointmentId(String appointmentId);


}