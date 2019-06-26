package com.yanya.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yanya.springmvc.model.Message;
import com.yanya.springmvc.model.ReadTimeForm;
import com.yanya.springmvc.model.Appointment;
import com.yanya.springmvc.model.AppointmentRequest;
////import com.yanya.springmvc.model.Cart;
import com.yanya.springmvc.model.CartItem;

import java.lang.Integer;

@Service
public interface MessageService {
	
	Message sendNewAppointmentMessage(Appointment appointment, HttpServletRequest request);
	
	Message sendAppointmentRequestSubmissionConfirmation(AppointmentRequest appointmentRequest, List<Appointment> appointments, List<CartItem> cartItems, HttpServletRequest request);
	
	List<Message> findMessagesByMerchantId(String userId);
	
	List<Message> findMessagesByCustomerId(String userId);
	
	Message findMessageByMessageId(String messageId);
	
	void deleteMessage(Message message);
	
	void deleteMessage(String messageId, HttpServletRequest request);
	
	void markMessageRead(ReadTimeForm form, HttpServletRequest request);
    
	Message sendCustomerAppointmentAcceptedMessage(Appointment appointment, HttpServletRequest request);
	Message sendCustomerAppointmentRejectedMessage(Appointment appointment, HttpServletRequest request);
	Message sendCustomerAppointmentCompletedMessage(Appointment appointment, HttpServletRequest request);

	 
}