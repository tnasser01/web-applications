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

import com.yanya.springmvc.model.Notification;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.ZipCode;
import com.yanya.springmvc.dao.AbstractDao;
import com.yanya.springmvc.dao.NotificationDao;

 
@Repository("notificationDao")
public class NotificationDaoImpl extends AbstractDao<String, Notification> implements NotificationDao {

    @SuppressWarnings("unchecked")
    public Notification saveNewNotification(Notification notification) {
    	String id = (String)save(notification);
    	System.out.println("The id returned from hibernate save is:" + id);
    	String hql = "FROM Notification c where c.notificationId = '" + id + "'";
    	Query query = getSession().createQuery(hql);
    	return  (Notification)query.uniqueResult();
   
    }

    
    
}