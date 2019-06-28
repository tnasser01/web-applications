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
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
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

import com.yanya.springmvc.listener.AppointmentListener;
 
@Entity
//@EntityListeners(AppointmentListener.class)
@Table(name="Appointment")
public class Appointment implements Serializable{
 
 	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "appointmentId", unique = true, nullable = false)
    private String appointmentId;
 	
	
 	@NotNull
 	@Column(name = "customerName")
 	protected String customerName;
 	
 	@NotNull
 	@Column(name = "timeReceived", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
 	private Date timeReceived;
 	
 	@Column(name = "timeAccepted", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
 	private Date timeAccepted;
 	
 	@Column(name = "timeRejected", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
 	private Date timeRejected;
 	
 	@Column(name = "timeCompleted", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
 	private Date timeCompleted;
 	
 	@NotNull
 	@Column(name = "userId")
    private String userId;
 	
 	@NotNull
 	@Column(name = "merchantId", nullable = false)
    private String merchantId;

 	@NotNull
 	@Column(name = "storeName", nullable = false)
    private String storeName;
 	
    @NotNull
    @Column(name = "appointmentStatus")
    private String appointmentStatus;

    @NotNull
 	@Column(name = "searchTextField", nullable = false)
    private String searchTextField;
    
    @NotNull
 	@Column(name = "lng", nullable = false)
    private String lng;
    
    @NotNull
 	@Column(name = "lat", nullable = false)
    private String lat;
    
    @NotNull
 	@Column(name = "customerPhone", nullable = false)
    private String customerPhone;
    
    @NotNull
 	@Column(name = "merchantPhone", nullable = false)
    private String merchantPhone;
    
    @Column(name = "acceptId", nullable = true, unique=true)
    private String acceptId;
    
    @Column(name = "rejectId", nullable = true, unique=true)
    private String rejectId;
    
    @Column(name = "completeId", nullable = true, unique=true)
    private String completeId;
    
    @Column(name = "queueNumber")
    private int queueNumber;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "appointment")
    private List<AppointmentItem> items;
    
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "appointmentRequestId", nullable = false)
	private AppointmentRequest appointmentRequest;
    
    public Appointment(){ }
 
    public Appointment(AppointmentRequest request, String userId, String customerName, String merchantId, String storeName, String searchTextField,
			String lng, String lat, String appointmentStatus, String customerPhone, String merchantPhone, Date timeReceived, Date timeRejected, Date timeAccepted, Date timeCompleted, String acceptId, String rejectId, String completeId, int queueNumber) {
    	this.appointmentRequest = request;
		this.userId = userId;
		this.customerName = customerName;
		this.merchantId = merchantId;
		this.storeName = storeName;
		this.searchTextField = searchTextField;
		this.lng = lng;
		this.lat = lat;
		this.appointmentStatus = appointmentStatus;
		this.customerPhone = customerPhone;
		this.merchantPhone = merchantPhone;
		this.timeReceived = timeReceived; 
		this.timeRejected = timeRejected;
		this.timeAccepted = timeAccepted;
		this.timeCompleted = timeCompleted;
		this.acceptId = acceptId;
		this.rejectId = rejectId;
		this.completeId = completeId;
		this.queueNumber = queueNumber;
	
	}
    

	public Appointment(AppointmentRequest request, String appointmentId, String userId, String customerName,
			String merchantId, String storeName, String searchTextField, String lng, String lat, String appointmentStatus, String customerPhone, String merchantPhone, Date timeReceived, Date timeRejected, Date timeAccepted, Date timeCompleted, String acceptId, String rejectId, String completeId, int queueNumber) {
		this.appointmentRequest = request;
		this.appointmentId = appointmentId;
		this.userId = userId;
		this.customerName = customerName;
		this.timeReceived = timeReceived;
		this.merchantId = merchantId;
		this.storeName = storeName;
		this.searchTextField = searchTextField;
		this.lng = lng;
		this.lat = lat;
		this.appointmentStatus = appointmentStatus;
		this.customerPhone = customerPhone;
		this.merchantPhone = merchantPhone;
		this.timeReceived = timeReceived; 
		this.timeRejected = timeRejected;
		this.timeAccepted = timeAccepted;
		this.timeCompleted = timeCompleted;
		this.acceptId = acceptId;
		this.rejectId = rejectId;
		this.completeId = completeId;
		this.queueNumber = queueNumber;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        if(appointmentId == null) return 0;
        result = prime * result + Integer.parseInt(appointmentId);
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Appointment))
            return false;
        Appointment other = (Appointment) obj;
        if (appointmentId != other.appointmentId)
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "AppointmentId=" + appointmentId;
    }

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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

	public String getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(String appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}

	public List<AppointmentItem> getItems() {
		return items;
	}

	public void setItems(List<AppointmentItem> items) {
		this.items = items;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public AppointmentRequest getAppointmentRequest() {
		return appointmentRequest;
	}

	public void setAppointmentRequest(AppointmentRequest appointmentRequest) {
		this.appointmentRequest = appointmentRequest;
	}

	public String getMerchantPhone() {
		return merchantPhone;
	}

	public void setMerchantPhone(String merchantPhone) {
		this.merchantPhone = merchantPhone;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getRejectId() {
		return rejectId;
	}

	public void setRejectId(String rejectId) {
		this.rejectId = rejectId;
	}

	public String getAcceptId() {
		return acceptId;
	}

	public void setAcceptId(String acceptId) {
		this.acceptId = acceptId;
	}

	public int getQueueNumber() {
		return queueNumber;
	}

	public void setQueueNumber(int queueNumber) {
		this.queueNumber = queueNumber;
	}

	public String getCompleteId() {
		return completeId;
	}

	public void setCompleteId(String completeId) {
		this.completeId = completeId;
	}

	public Date getTimeReceived() {
		return timeReceived;
	}

	public void setTimeReceived(Date timeReceived) {
		this.timeReceived = timeReceived;
	}

	public Date getTimeAccepted() {
		return timeAccepted;
	}

	public void setTimeAccepted(Date timeAccepted) {
		this.timeAccepted = timeAccepted;
	}

	public Date getTimeRejected() {
		return timeRejected;
	}

	public void setTimeRejected(Date timeRejected) {
		this.timeRejected = timeRejected;
	}

	public Date getTimeCompleted() {
		return timeCompleted;
	}

	public void setTimeCompleted(Date timeCompleted) {
		this.timeCompleted = timeCompleted;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



     
}