package com.yanya.springmvc.dao;
 
import java.util.List;
import java.util.ArrayList;

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
import org.springframework.context.annotation.ImportResource;

import com.yanya.springmvc.model.AppointmentRequest;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.ZipCode;
import com.yanya.springmvc.dao.AbstractDao;
import com.yanya.springmvc.dao.AppointmentRequestDao;

 
@Repository("appointmentRequestDao")
public class AppointmentRequestDaoImpl extends AbstractDao<String, AppointmentRequest> implements AppointmentRequestDao {

    @SuppressWarnings("unchecked")
    public AppointmentRequest saveNewAppointmentRequest(AppointmentRequest appointmentRequest) {
    	String id = (String)save(appointmentRequest);
    	System.out.println("The id returned from hibernate save is:" + id);
    	String hql = "FROM AppointmentRequest c where c.appointmentRequestId = '" + id + "'";
    	Query query = getSession().createQuery(hql);
    	return  (AppointmentRequest)query.uniqueResult();
   
    }
	
    @SuppressWarnings("unchecked")
    public AppointmentRequest findAppointmentRequestByAppointmentRequestId(String appointmentRequestId) {
    	System.out.println("The inputted appointmentRequestId to search for is:" + appointmentRequestId);
    	String hql = "FROM AppointmentRequest c where c.appointmentRequestId = '" + appointmentRequestId + "'";
    	Query query = getSession().createQuery(hql);
    	return  (AppointmentRequest)query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public AppointmentRequest findCustomersLastAppointmentRequest(String userId){
    	System.out.println("The inputted userId to search for is:" + userId);
    	String hql = "From AppointmentRequest ar"
    	+ "  where ar.timestamp = (select max(timestamp) from AppointmentRequest rr where rr.userId =  rr.userId)";
    	Query query = getSession().createQuery(hql);
    	return (AppointmentRequest)query.setMaxResults(1).uniqueResult();
    }

    
    
}