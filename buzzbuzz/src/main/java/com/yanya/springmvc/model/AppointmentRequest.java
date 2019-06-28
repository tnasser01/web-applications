package com.yanya.springmvc.model;
 
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;
import static javax.persistence.GenerationType.IDENTITY;

 
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.yanya.springmvc.model.Appointment;
 
@Entity
@Table(name="AppointmentRequest")
public class AppointmentRequest implements Serializable{
 
 	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "appointmentRequestId", unique = true, nullable = false)
    private String appointmentRequestId;
 	
// 	@NotNull
// 	@Column(name = "cartId", nullable = false)
//    private String cartId;
 	
 	@NotNull
 	@Column(name = "timestamp", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
 	private Date timestamp;
 	
 	@NotNull
 	@Column(name = "customerName", nullable = false)
    private String customerName;
 	
 	@NotNull
 	@Column(name = "userId", nullable = false)
    private String userId;
 	
 	@NotNull
 	@Column(name = "phone", nullable = false)
    private String phone;
 	
    @NotNull
 	@Column(name = "searchTextField", nullable = false)
    private String searchTextField;
    
    @NotNull
 	@Column(name = "lng", nullable = false)
    private String lng;
    
    @NotNull
 	@Column(name = "lat", nullable = false)
    private String lat;
 
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "appointmentRequest")
    private List<Appointment> appointments;
    
    public AppointmentRequest(){ }

	public AppointmentRequest(Date timestamp, String customerName, String userId, String phone, String searchTextField,
			String lng, String lat) {	
		this.timestamp = timestamp;
		this.customerName = customerName;
		this.userId = userId;
		this.phone = phone;
		this.searchTextField = searchTextField;
		this.lng = lng;
		this.lat = lat;
		
	}

	public AppointmentRequest(String appointmentRequestId, Date timestamp, String customerName, String userId,
			String phone, String searchTextField, String lng, String lat) {
			this.appointmentRequestId = appointmentRequestId;
			this.timestamp = timestamp;
			this.customerName = customerName;
			this.userId = userId;
			this.phone = phone;
			this.searchTextField = searchTextField;
			this.lng = lng;
			this.lat = lat;
			
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        if(appointmentRequestId == null) return 0;
        result = prime * result + Integer.parseInt(appointmentRequestId);
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof AppointmentRequest))
            return false;
        AppointmentRequest other = (AppointmentRequest) obj;
        if (appointmentRequestId != other.appointmentRequestId)
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "AppointmentRequestId=" + appointmentRequestId;
    }

	public String getAppointmentRequestId() {
		return appointmentRequestId;
	}

	public void setAppointmentRequestId(String appointmentRequestId) {
		this.appointmentRequestId = appointmentRequestId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getSearchTextField() {
		return searchTextField;
	}

	public void setSearchTextField(String searchTextField) {
		this.searchTextField = searchTextField;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setItems(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}







     
}