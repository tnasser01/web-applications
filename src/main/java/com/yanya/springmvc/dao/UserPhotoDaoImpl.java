package com.yanya.springmvc.dao;
 
import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManagerFactory;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.ImportResource;

import com.yanya.springmvc.model.UserPhoto;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.ZipCode;
import com.yanya.springmvc.dao.AbstractDao;
import com.yanya.springmvc.dao.UserPhotoDao;

 
@Repository("userPhotoDao")
public class UserPhotoDaoImpl extends AbstractDao<String, UserPhoto> implements UserPhotoDao {

    @SuppressWarnings("unchecked")
    public UserPhoto saveNewUserPhoto(UserPhoto userPhoto) {
    	System.out.println("The value of userPhoto objects userPhoto descrip in userPhotodao is " + userPhoto.getUserPhotoDescription());
    	String id = (String)save(userPhoto);
    	System.out.println("The id returned from hibernate save is:" + id);
    	String hql = "FROM UserPhoto p where p.userPhotoId = '" + id + "'";
    	Query query = getSession().createQuery(hql);
    	return  (UserPhoto)query.uniqueResult();
   
    }
	
    
    @SuppressWarnings("unchecked")
    public List<UserPhoto> findAllUserPhotos() {
        Criteria criteria = createEntityCriteria();
        criteria.setFetchMode("comments", FetchMode.JOIN);
        return (List<UserPhoto>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }
    
    @SuppressWarnings("unchecked")
    public UserPhoto findPhotoByPhotoId(String photoId){
    	Query query = getSession().createQuery("FROM UserPhoto p where p.userPhotoId = '" + photoId + "'");
    	return (UserPhoto)query.uniqueResult();
    }
    
    
    @SuppressWarnings("unchecked")
    public List<UserPhoto> findPhotosByCustomerId(String userId){
    	Query query = getSession().createQuery("Select DISTINCT UserPhoto from UserPhoto where userId = '" + userId + "' ORDER BY userPhotoId DESC");
    	List<UserPhoto> userPhotos = (List<UserPhoto>)query.list();
    	if(userPhotos==null){
    		userPhotos = new ArrayList<UserPhoto>();
    	}
    	return userPhotos;  
  	}
    
    @SuppressWarnings("unchecked")
    public void deleteUserPhoto(UserPhoto userPhoto) {
    	delete(userPhoto);
    
    }
    
    @SuppressWarnings("unchecked")
    public void updatePhotoDescription(String userPhotoId, String description, String userId){
    	Query query = getSession().createQuery("Update UserPhoto p set p.description =:description where p.userPhotoId =:userPhotoId and p.customer.userId =:userId");
    	System.out.println("user photo id is: " + userPhotoId + " and  description is: " + description);
    	query.setParameter("userPhotoId", userPhotoId);
    	query.setParameter("description", description);
    	query.setParameter("userId", userId);
    	query.executeUpdate();
    }
    
    @SuppressWarnings("unchecked")
    public List<UserPhoto> findAllVisibleUserPhotos(List<String> userIds, String userId){
    	String hql = "";
    	Query query = null;
    	if(userIds!=null || userIds.size()==0){
    		hql = "FROM UserPhoto p where p.customer.visibility='public' OR p.customer.userId =:userId ORDER BY p.userPhotoId DESC";
    		query = getSession().createQuery(hql);
    		query.setParameter("userId", userId);
    	}else{
    		hql = "FROM UserPhoto p where p.customer.visibility='public' OR p.customer.userId IN (:userIds) ORDER BY p.userPhotoId DESC";
    		query = getSession().createQuery(hql).setParameterList("userIds", userIds);
    		query.setParameter("userId", userId);
    	}
    	List<UserPhoto> userPhotos = (List<UserPhoto>)query.list();
    	if(userPhotos==null){
    		return new ArrayList<UserPhoto>();
    	}
    	
    	return userPhotos;
    	
    	
    }
    
}