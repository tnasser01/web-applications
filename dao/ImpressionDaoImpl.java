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

import com.yanya.springmvc.model.Impression;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.ZipCode;
import com.yanya.springmvc.dao.AbstractDao;
import com.yanya.springmvc.dao.ImpressionDao;

 
@Repository("impressionDao")
public class ImpressionDaoImpl extends AbstractDao<String, Impression> implements ImpressionDao {

    @SuppressWarnings("unchecked")
    public void saveOrUpdateImpression(Impression impression) {
    	String hql = "FROM Impression i where i.userPhoto = '" + impression.getUserPhoto().getUserPhotoId() + "' and i.userId = '" + impression.getUserId() + "'";
    	Query query = getSession().createQuery(hql);
    	Impression imp = (Impression)query.uniqueResult();
    	if(imp!=null){
    		update(impression);
    	}else{
    		save(impression);
    	}

    }
    
    @SuppressWarnings("unchecked")
    public void saveImpression(Impression impression) {
    		save(impression);
    }
    
    @SuppressWarnings("unchecked")
    public void updateImpression(Impression impression) {
    		update(impression);
    }
    
    @SuppressWarnings("unchecked")
    public Impression findImpressionByPhotoIdAndUserId(String photoId,String userId){
    	String hql = "FROM Impression i where i.userPhoto = '" + photoId + "' and i.userId = '" + userId + "'";
    	Query query = getSession().createQuery(hql);
    	return  (Impression)query.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
	public List<Impression> findImpressionsByUserPhotoIdsAndUserId(List<String> userPhotoIds, String userId, String userType){
		String hql = "FROM Impression p where p.userPhoto.userPhotoId IN (:userPhotoIds) and p.userId =:userId and p.userType =:userType";
		Query query = getSession().createQuery(hql);
		query.setParameter("userId", userId);
		query.setParameter("userType", userType);
		query.setParameterList("userPhotoIds", userPhotoIds);
		List<Impression> impressions = (List<Impression>)query.list();
		if(impressions==null){
			return new ArrayList<Impression>();
		}
		
		return impressions;
		
	}

}