package com.yanya.springmvc.dao;
 
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManagerFactory;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;

import com.yanya.springmvc.model.Appointment;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.ZipCode;
import com.yanya.springmvc.dao.AbstractDao;
import com.yanya.springmvc.dao.AppointmentDao;

 
@Repository("appointmentDao")
public class AppointmentDaoImpl extends AbstractDao<String, Appointment> implements AppointmentDao {

    @SuppressWarnings("unchecked")
    public Appointment saveNewAppointment(Appointment appointment) {
    	String id = (String)save(appointment);
    	System.out.println("The id returned from hibernate save is:" + id);
    	String hql = "FROM Appointment c where c.appointmentId = '" + id + "'";
    	Query query = getSession().createQuery(hql);
    	return  (Appointment)query.uniqueResult();
   
    }
	
    @SuppressWarnings("unchecked")
    public Appointment findAppointmentByAppointmentId(String appointmentId) {
    	System.out.println("The inputted appointmentId to search for is:" + appointmentId);
    	String hql = "FROM Appointment c where c.appointmentId = '" + appointmentId + "'";
    	Query query = getSession().createQuery(hql);
    	return  (Appointment)query.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
	public List<Appointment> findAppointmentsByCustomerIdAndMerchantId(String userId, String merchantId){
    	String hql = "FROM Appointment a where a.userId =:userId AND a.merchantId =:merchantId";
    	Query query = getSession().createQuery(hql);
    	query.setParameter("userId", userId);
    	query.setParameter("merchantId", merchantId);
    	List<Appointment> appointments = (List<Appointment>)query.list();
    	if(appointments==null){
    		appointments = new ArrayList<Appointment>();
    		return appointments;
    	}
    	return appointments; 
    }
    
	@SuppressWarnings("unchecked")
	public List<Appointment> findNewAppointmentsByMerchantId(String userId){
    	String hql = "FROM Appointment c where c.merchantId = '" + userId + "' and "
    										+ "c.appointmentStatus = 'new' order by a.queueNumber asc";
    	Query query = getSession().createQuery(hql);
    	List<Appointment> appointments = (List<Appointment>)query.list();
    	if(appointments==null){
    		appointments = new ArrayList<Appointment>();
    	}
    	return appointments;
 
	}
	
	@SuppressWarnings("unchecked")
	public List<Appointment> findConfirmedAppointmentsByMerchantId(String userId){
    	String hql = "FROM Appointment c where c.merchantId = '" +  userId + "' and "
    										+ "c.appointmentStatus = 'confirmed'";
    	Query query = getSession().createQuery(hql);
    	List<Appointment> appointments = (List<Appointment>)query.list();
    	if(appointments==null){
    		appointments = new ArrayList<Appointment>();
    	}
    	return appointments;
	}
	
	@SuppressWarnings("unchecked")
	public List<Appointment> findPastAppointmentsByMerchantId(String userId){
    	String hql = "FROM Appointment c where c.merchantId = '" + userId + "' and "
    										+ "c.appointmentStatus = 'completed' or "
    										+ "c.appointmentStatus = 'rejected'";
    	Query query = getSession().createQuery(hql);
    	List<Appointment> appointments = (List<Appointment>)query.list();
    	if(appointments==null){
    		appointments = new ArrayList<Appointment>();
    	}
    	return appointments;
	}
	
    @SuppressWarnings("unchecked")
    public List<Appointment> findAppointmentByMerchantId(String merchantId) {
    	System.out.println("The inputted appointmentId to search for is:" + merchantId);
    	String hql = "FROM Appointment c where c.merchantId = '" + merchantId + "'";
    	Query query = getSession().createQuery(hql);
    	List<Appointment> appointments = (List<Appointment>)query.list();
    	if(appointments==null){
    		appointments = new ArrayList<Appointment>();
    	}
    	return appointments;
    }
    
	@SuppressWarnings("unchecked")
	public List<Appointment> findNewAppointmentsByCustomerId(String userId){
    	String hql = "FROM Appointment c where c.userId = '" + userId + "' and "
    										+ "c.appointmentStatus = 'new' order by a.queueNumber asc";
    	Query query = getSession().createQuery(hql);
    	List<Appointment> appointments = (List<Appointment>)query.list();
    	if(appointments==null){
    		appointments = new ArrayList<Appointment>();
    	}
    	return appointments;
	}
	
	@SuppressWarnings("unchecked")
	public List<Appointment> findConfirmedAppointmentsByCustomerId(String userId){
    	String hql = "FROM Appointment c where c.userId = '" +  userId + "' and "
    										+ "c.appointmentStatus = 'confirmed'";
    	Query query = getSession().createQuery(hql);
    	List<Appointment> appointments = (List<Appointment>)query.list();
    	if(appointments==null){
    		appointments = new ArrayList<Appointment>();
    	}
    	return appointments;
	}
	
	@SuppressWarnings("unchecked")
	public List<Appointment> findPastAppointmentsByCustomerId(String userId){
    	String hql = "FROM Appointment c where c.userId = '" + userId + "' and "
    										+ "c.appointmentStatus = 'completed' or "
    										+ "c.appointmentStatus = 'rejected'";
    	Query query = getSession().createQuery(hql);
    	List<Appointment> appointments = (List<Appointment>)query.list();
    	if(appointments==null){
    		appointments = new ArrayList<Appointment>();
    	}
    	return appointments;
	}
	
    @SuppressWarnings("unchecked")
    public List<Appointment> findAppointmentsByCustomerId(String userId) {
    	System.out.println("The inputted appointmentId to search for is:" + userId);
    	String hql = "FROM Appointment c where c.userId = '" + userId + "'";
    	Query query = getSession().createQuery(hql);
    	List<Appointment> appointments = (List<Appointment>)query.list();
    	if(appointments==null){
    		appointments = new ArrayList<Appointment>();
    	}
    	return appointments;
    }
  
     @SuppressWarnings("unchecked")
        public Appointment findQueueSizeByMerchantId(String userId){
        	String hql = "FROM Appointment where queueNumber IN ("
        			+ "SELECT MAX(queueNumber) FROM Appointment WHERE merchantId = '" + userId + "' and queueNumber!=0 )";	
        	Query query = getSession().createQuery(hql);
        	return (Appointment)query.uniqueResult();
     }
    
    
    @SuppressWarnings("unchecked")
    public Appointment findByAcceptId(String newId){   	
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("acceptId", newId));
        return (Appointment) criteria.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    public Appointment findByRejectId(String newId){   	
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("rejectId", newId));
        return (Appointment) criteria.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    public Appointment findByCompleteId(String newId){   	
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("completeId", newId));
        return (Appointment) criteria.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    public void updateAppointmentStatus(String appointmentId, String newStatus){
    	String hql = "update Appointment a set a.appointmentStatus=:newStatus where a.appointmentId=:appointmentId ";
    	Query query = getSession().createQuery(hql);
    	query.setParameter("appointmentId", appointmentId);
    	query.setParameter("newStatus", newStatus);
    	query.executeUpdate();
    }
    
    @SuppressWarnings("unchecked")
    public void placeConfirmedAppointmentInQueue(Appointment appointment, int queueNumber){
    	String hql = "update Appointment a set a.queueNumber=:queueNumber where a.appointmentId=:appointmentId ";
    	Query query = getSession().createQuery(hql);
    	query.setParameter("appointmentId", appointment.getAppointmentId());
    	query.setParameter("queueNumber", queueNumber);
    	query.executeUpdate();
    }
    
    @SuppressWarnings("unchecked")
    public void removeAppointmentFromQueue(Appointment appointment){
    	String hql= "update Appointment a set a.queueNumber=0 where a.appointmentId=:appointmentId";
    	Query query = getSession().createQuery(hql);
    	query.setParameter("appointmentId",appointment.getAppointmentId());
    	query.executeUpdate();
    	
    	Query positionsQuery = getSession().createQuery("update Appointment a set a.queueNumber=queueNumber - 1 "
    			+ "where a.userId=:userId AND a.queueNumber!=0 AND a.queueNumber>:completedQueuePos ");
    	positionsQuery.setParameter("userId", appointment.getMerchantId());
    	positionsQuery.setParameter("completedQueuePos", appointment.getQueueNumber());
    	positionsQuery.executeUpdate();
    	
    }
    
    @SuppressWarnings("unchecked")
	public void  updateTimeAccepted(String appointmentId, Date date){
    	java.sql.Timestamp dateDB = new java.sql.Timestamp(date.getTime());
    	String hql= "update Appointment a set a.timeAccepted='" + dateDB + "' where a.appointmentId=:appointmentId";
    	Query query = getSession().createQuery(hql);
    	query.setParameter("appointmentId",appointmentId);
    	query.executeUpdate();
    }
	
    @SuppressWarnings("unchecked")
	public void  updateTimeRejected(String appointmentId, Date date){
    	java.sql.Timestamp dateDB = new java.sql.Timestamp(date.getTime());
    	String hql= "update Appointment a set a.timeRejected='" + dateDB + "' where a.appointmentId=:appointmentId";
    	Query query = getSession().createQuery(hql);
    	query.setParameter("appointmentId",appointmentId);
    	query.executeUpdate();
    }
    
	
    @SuppressWarnings("unchecked")
	public void  updateTimeCompleted(String appointmentId, Date date){
    	java.sql.Timestamp dateDB = new java.sql.Timestamp(date.getTime());
    	String hql= "update Appointment a set a.timeCompleted='" + dateDB + "' where a.appointmentId=:appointmentId";
    	Query query = getSession().createQuery(hql);
    	query.setParameter("appointmentId",appointmentId);
    	query.executeUpdate();
    }
    
}