package com.yanya.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import com.yanya.springmvc.model.Appointment;
//import com.yanya.springmvc.model.Cart;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.ZipCode;
 
public interface AppointmentDao {
 
	@Autowired
	Appointment saveNewAppointment(Appointment appointment);
	
	@Autowired
    Appointment findAppointmentByAppointmentId(String apppointmentId);
	
	@Autowired
	List<Appointment> findAppointmentsByCustomerIdAndMerchantId(String userId, String merchantId);
	@Autowired
    List<Appointment> findAppointmentByMerchantId(String userId);
	
	@Autowired	
	List<Appointment> findNewAppointmentsByMerchantId(String userId);

	@Autowired	
	List<Appointment> findConfirmedAppointmentsByMerchantId(String userId);

	@Autowired	
	List<Appointment> findPastAppointmentsByMerchantId(String userId);
 	
	@Autowired
    List<Appointment> findAppointmentsByCustomerId(String userId);
	
	@Autowired	
	List<Appointment> findNewAppointmentsByCustomerId(String userId);

	@Autowired	
	List<Appointment> findConfirmedAppointmentsByCustomerId(String userId);

	@Autowired	
	List<Appointment> findPastAppointmentsByCustomerId(String userId);
 	
	@Autowired
	Appointment findQueueSizeByMerchantId(String userId);
	
	@Autowired
	Appointment findByAcceptId(String id);
	
	@Autowired
	Appointment findByRejectId(String id);
	
	@Autowired
	Appointment findByCompleteId(String id);
	
	@Autowired
	void updateAppointmentStatus(String appointmentId, String status);
	
	@Autowired
	void placeConfirmedAppointmentInQueue(Appointment appointment, int queueNumber);
	
	@Autowired
	void removeAppointmentFromQueue(Appointment appointment);
	
	@Autowired
	void  updateTimeAccepted(String appointmentId, Date date);
	
	@Autowired
	void  updateTimeRejected(String appointmentId, Date date);

	@Autowired
	void updateTimeCompleted(String appointmentId, Date date);
}