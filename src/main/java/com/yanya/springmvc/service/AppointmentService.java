package com.yanya.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yanya.springmvc.model.Appointment;
import com.yanya.springmvc.model.AppointmentItem;
import com.yanya.springmvc.model.AppointmentRequest;
////import com.yanya.springmvc.model.Cart;
import com.yanya.springmvc.model.CartItem;

import java.lang.Integer;

@Service
public interface AppointmentService {
	
	AppointmentRequest saveNewAppointmentRequest(AppointmentRequest appointmentRequest);
	List<Appointment> createNewAppointments(AppointmentRequest apptRequest, Map<String, String> merchants, Map<String, String> merchantPhones, List<CartItem> cartItems);
	List<Appointment> findAppointmentByMerchantId(String userId);
	List<Appointment> findAppointmentsByCustomerId(String userId);
	List<Appointment> findAppointmentsByCustomerIdAndMerchantId(String userId, String merchantId);
	List<Appointment> findNewAppointmentsByMerchantId(String userId);
	List<Appointment> findNewAppointmentsByCustomerId(String userId);
	List<Appointment> findConfirmedAppointmentsByMerchantId(String userId);
	List<Appointment> findPastAppointmentsByMerchantId(String userId);
	List<Appointment> findConfirmedAppointmentsByCustomerId(String userId);
	List<Appointment> findPastAppointmentsByCustomerId(String userId);
	AppointmentRequest findCustomersLastAppointmentRequest(String userId);
	Appointment findByRejectId(String id);
	Appointment findByAcceptId(String id);
	Appointment findByCompleteId(String id);
	Appointment findAppointmentByAppointmentId(String id);
	void updateAppointmentStatus(String appointmentId, String newStatus);
	Appointment findQueueSizeByMerchantId(String userId);
	Integer placeConfirmedAppointmentInQueue(Appointment appointment);
	void removeAppointmentFromQueue(Appointment appointment);
	List<AppointmentItem> findAppointmentItemsByAppointmentId(String appointmentId);
	String formatDate(Date date); 
    void  updateTimeAccepted(String appointmentId, Date date);
    void  updateTimeRejected(String appointmentId, Date date);
    void  updateTimeCompleted(String appointmentId, Date date);
    public Date parseDate(Date date);
    
}