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

import com.yanya.springmvc.model.AppointmentItem;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.ZipCode;

import com.yanya.springmvc.model.Appointment;

import com.yanya.springmvc.dao.AbstractDao;
import com.yanya.springmvc.dao.AppointmentItemDao;

 
@Repository("appointmentItemDao")
public class AppointmentItemDaoImpl extends AbstractDao<String, AppointmentItem> implements AppointmentItemDao {

    @SuppressWarnings("unchecked")
    public AppointmentItem saveNewAppointmentItem(AppointmentItem appointmentItem) {
    	String id = (String)save(appointmentItem);
    	System.out.println("The id returned from hibernate save is:" + id);
    	String hql = "FROM AppointmentItem c where c.appointmentItemId = '" + id + "'";
    	Query query = getSession().createQuery(hql);
    	return  (AppointmentItem)query.uniqueResult();
   
    }
	
    @SuppressWarnings("unchecked")
    public AppointmentItem findAppointmentItemByAppointmentItemId(String appointmentItemId) {
    	System.out.println("The inputted appointmentItemId to search for is:" + appointmentItemId);
    	String hql = "FROM AppointmentItem c where c.appointmentItemId = '" + appointmentItemId + "'";
    	Query query = getSession().createQuery(hql);
    	return  (AppointmentItem)query.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    public List<AppointmentItem> findAppointmentItemsByAppointmentId(String appointmentId){
    	System.out.println("The inputted appointmentId to search for is:" + appointmentId);
    	String hql = "FROM AppointmentItem c where c.appointmentId = '" + appointmentId + "'";
    	Query query = getSession().createQuery(hql);
    	List<AppointmentItem> appointmentItems = (List<AppointmentItem>)query.list();
    	if(appointmentItems==null){
    		appointmentItems = new ArrayList<AppointmentItem>();
    	}
    	return appointmentItems;
    }


    
    
}