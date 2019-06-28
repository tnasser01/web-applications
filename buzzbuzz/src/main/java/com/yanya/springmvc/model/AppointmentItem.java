package com.yanya.springmvc.model;

import org.springframework.stereotype.Component;

import com.yanya.springmvc.model.Appointment;
import com.yanya.springmvc.model.Product;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import static javax.persistence.GenerationType.IDENTITY;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name="AppointmentItem")
public class AppointmentItem implements Serializable{ 

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "appointmentItemId", unique = true, nullable = false)
	private String appointmentItemId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "appointmentId", nullable = false)
	private Appointment appointment;
	
	@NotNull
	@Column(name = "productId", nullable = false)
	private String productId;
	
	@Column(name = "quantity",  nullable = false)
	private int quantity;
	
	@Column(name = "units",  nullable = false)
	private String units;
	
	@Column(name = "price",  nullable = false)
	private Double price;
	
	@Column(name = "productName",  nullable = false)
	private String productName;
	
	public AppointmentItem(){ }
	
	public AppointmentItem(Appointment appointment, String productId, int quantity, String units, Double price, String productName){
		this(null, appointment, productId, quantity, units, price, productName);
	}
	
	public AppointmentItem(String appointmentItemId, Appointment appointment, String productId, int quantity, String units, Double price, String productName){
		this.appointmentItemId = appointmentItemId;
		this.appointment = appointment;
		this.productId = productId;
		this.quantity = quantity;
		this.units = units;
		this.price = price;
		this.productName = productName;
	}

	public String getAppointmentItemId() {
		return appointmentItemId;
	}

	public void setAppointmentItemId(String appointmentItemId) {
		this.appointmentItemId = appointmentItemId;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public Double getPrice() {
		return price;
	}

	
	public void setPrice(Double price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}


}
