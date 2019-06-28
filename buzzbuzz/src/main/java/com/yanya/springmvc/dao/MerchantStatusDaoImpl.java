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

import com.yanya.springmvc.model.MerchantStatus;
import com.yanya.springmvc.model.MerchantStatusForm;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.ZipCode;
import com.yanya.springmvc.dao.AbstractDao;
import com.yanya.springmvc.dao.MerchantStatusDao;

 
@Repository("merchantStatusDao")
public class MerchantStatusDaoImpl extends AbstractDao<String, MerchantStatus> implements MerchantStatusDao {

    @SuppressWarnings("unchecked")
    public MerchantStatus saveMerchantStatus(MerchantStatus status) {
    	String id = (String)save(status);
    	System.out.println("The id returned from hibernate save is:" + id);
        String hql = "FROM MerchantStatus c where c.userId = '" + id + "'";
    	Query query = getSession().createQuery(hql);
    	return  (MerchantStatus)query.uniqueResult();
    }
	
    @SuppressWarnings("unchecked")
    public MerchantStatus findMerchantStatusByMerchantId(String userId) {
    	System.out.println("The inputed userId to search for is:" + userId);
//    	String hql = "FROM MerchantStatus m where m.userId = '" + userId + "'";
    	
    	String hql = "FROM MerchantStatus f where f.userId = '" + userId + "' and f.timestamp = (select max(ff.timestamp) from MerchantStatus ff where ff.userId = f.userId)";
    	
    	Query query = getSession().createQuery(hql);
    	return  (MerchantStatus)query.uniqueResult();
    }
 
    
    
}
