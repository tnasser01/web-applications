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

//import com.yanya.springmvc.model.CurrentMerchantStatus;
import com.yanya.springmvc.model.MerchantStatus;
import com.yanya.springmvc.model.MerchantStatusForm;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.ZipCode;
import com.yanya.springmvc.dao.AbstractDao;
import com.yanya.springmvc.dao.MerchantStatusDao;

 
@Repository("currentMerchantStatusDao")
public class CurrentMerchantStatusDaoImpl  {

//    @SuppressWarnings("unchecked")
//    public CurrentMerchantStatus updateCurrentStatus(CurrentMerchantStatus status) {
//    	
//    	if(findCurrentMerchantStatusByMerchantId(status.getUserId()) == null){
//    		String id = (String)save(status);
//    		System.out.println("The id returned from hibernate save is:" + id);
//    		return findCurrentMerchantStatusByMerchantId(status.getUserId());
//    	}
//
//    	String hql = "";
//    	if(status.getStatus().equals("online")){
//    		hql = "update CurrentMerchantStatus set " +
//    				"status = :stat," +
//    				"location = :loc, " +
//    				"cityLng = :lng, " +
//    				"cityLat = :lat " +
//    				"where userId = :merchId";
//			Query query = getSession().createQuery(hql);  
//	    	query.setParameter("stat", status.getStatus());
//			query.setParameter("merchId", status.getUserId());
//			query.setParameter("loc", status.getLocation());
//			query.setParameter("lng", status.getCityLng());
//			query.setParameter("lat", status.getCityLat());
//			int result = query.executeUpdate(); 	
//			return findCurrentMerchantStatusByMerchantId(status.getUserId());
//    	}else{
//    		hql = "update CurrentMerchantStatus set " +
//    				"status = :stat," +
//    				"location = :loc, " +
//    				"cityLng = :loc, " +
//    				"cityLat = :loc " +
//    				"where userId = :merchId";	
// 			Query query = getSession().createQuery(hql);  
//	    	query.setParameter("stat", status.getStatus());
//			query.setParameter("merchId", status.getUserId());
//			query.setParameter("loc", new String(" "));
//			int result = query.executeUpdate(); 	
//			return findCurrentMerchantStatusByMerchantId(status.getUserId());
//    	}   	
//    }
//	
//    @SuppressWarnings("unchecked")
//    public CurrentMerchantStatus findCurrentMerchantStatusByMerchantId(String userId) {
//    	System.out.println("The inputed userId to search for is:" + userId);
//        String hql = "FROM CurrentMerchantStatus c where c.userId = '" + userId + "'";
//    	Query query = getSession().createQuery(hql);
//    	return  (CurrentMerchantStatus)query.uniqueResult();
//    }
//    
//    @SuppressWarnings("unchecked")
//    public List<CurrentMerchantStatus> findOnlineMerchants(){
//        String hql = "FROM CurrentMerchantStatus c where c.status = 'online'";
//    	Query query = getSession().createQuery(hql);
//    	List<CurrentMerchantStatus> statuses = (List<CurrentMerchantStatus>)query.list();
//    	if(statuses==null){
//    		statuses = new ArrayList<CurrentMerchantStatus>();
//    	}
//    	return statuses;
//    
//    }
//    
//    @SuppressWarnings("unchecked")
//    public List<CurrentMerchantStatus> findVisibleMerchants(){
//        String hql = "FROM CurrentMerchantStatus c where c.status = 'online' and c.visibility = 'public'";
//    	Query query = getSession().createQuery(hql);
//    	List<CurrentMerchantStatus> statuses = (List<CurrentMerchantStatus>)query.list();
//    	if(statuses==null){
//    		statuses = new ArrayList<CurrentMerchantStatus>();
//    	}
//    	return statuses;
//    }
//    
//    @SuppressWarnings("unchecked")
//    public void updateMerchantVisibility(String userId, String newVisibility){
//        String hql = "update CurrentMerchantStatus c set c.visibility=:visibility where c.userId=:userId ";
//        Query query = getSession().createQuery(hql);
//        System.out.println("userId is: " + userId);
//        System.out.println("new visibility is: " + newVisibility);
//        query.setParameter("visibility", newVisibility);
//        query.setParameter("userId", userId);
//        query.executeUpdate();
//    }
// 
//    @SuppressWarnings("unchecked")
//   	public List<CurrentMerchantStatus> findByVisibilityAndStatus(String visibility, String status){
//        String hql = "FROM CurrentMerchantStatus c where c.visibility=:visibility and c.status=:status";
//        Query query = getSession().createQuery(hql);
//        System.out.println("visibility is: " + visibility);
//        System.out.println("new status is: " + status);
//        query.setParameter("status", status);
//        query.setParameter("visibility", visibility);
//    	List<CurrentMerchantStatus> statuses = (List<CurrentMerchantStatus>)query.list();
//    	if(statuses==null){
//    		statuses = new ArrayList<CurrentMerchantStatus>();
//    	}
//    	return statuses;
//    }
//    
//    @SuppressWarnings("unchecked")
//    public List<CurrentMerchantStatus> findAllCurrentMerchantStatuses(){
//        String hql = "FROM CurrentMerchantStatus c";
//        Query query = getSession().createQuery(hql);
//    	List<CurrentMerchantStatus> statuses = (List<CurrentMerchantStatus>)query.list();
//    	if(statuses==null){
//    		statuses = new ArrayList<CurrentMerchantStatus>();
//    	}
//    	return statuses;
//    }
    
}
