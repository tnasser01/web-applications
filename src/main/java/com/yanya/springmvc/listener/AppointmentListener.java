package com.yanya.springmvc.listener;

import javax.persistence.PostPersist;

import org.springframework.beans.factory.annotation.Autowired;

import com.yanya.springmvc.service.NotificationService;

public class AppointmentListener{
	
	@Autowired
	NotificationService notificationService;
	
	@PostPersist
	public void appointmentPostPersist(){
		//Send notification to merchant
		System.out.println("post persist is working");
		//notificationService.createNewAppointmentNotification();
		
		
	}
}
