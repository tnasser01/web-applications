package com.yanya.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.yanya.springmvc.model.Appointment;
import com.yanya.springmvc.model.AppointmentRequest;
import com.yanya.springmvc.model.Connection;
import com.yanya.springmvc.model.Notification;
import java.lang.Integer;

@Service
public interface NotificationService {
	
	//SEND APPOINTMENT REQUEST TO MERCHANT
	Notification createNewAppointmentNotification(Appointment appointment);

	//SEND APPOINTMENT REQUEST CONFIRMATON TO CUSTOMER
	Notification createAppointmentRequestSubmissionConfirmationNotification(AppointmentRequest appointmentRequest,  List<Appointment> appointments );

	//SEND CONNECTION REQUEST TO MERCHANT 
	Notification createConnectionRequestNotification(Connection conn );

	//SEND APPOINTMENT CONFIRMATION TO CUSTOMER
	Notification createAppointmentConfirmationNotification(Appointment appointment);
}