package com.yanya.springmvc.dao;
 
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.context.annotation.ImportResource;

import com.yanya.springmvc.model.LikeMessage;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.ZipCode;
import com.yanya.springmvc.dao.AbstractDao;
import com.yanya.springmvc.dao.LikeMessageDao;

 
@Repository("likeMessageDao")
public class LikeMessageDaoImpl extends AbstractDao<String, LikeMessage> implements LikeMessageDao {

    @SuppressWarnings("unchecked")
    public LikeMessage saveNewLikeMessage(LikeMessage message) {
    	String id = (String)save(message);
    	System.out.println("The id returned from hibernate save is:" + id);
    	String hql = "FROM LikeMessage c where c.likeMessageId = '" + id + "'";
    	Query query = getSession().createQuery(hql);
    	return  (LikeMessage)query.uniqueResult();
   
    }
    
    @SuppressWarnings("unchecked")
    public LikeMessage findLikeMessageByLikeMessageId(String likeMessageId){
    	String hql = "FROM LikeMessage m where m.likeMessageId = '" + likeMessageId + "'";
    	Query query = getSession().createQuery(hql);
    	return  (LikeMessage)query.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    public List<LikeMessage> findLikeMessagesByMerchantId(String userId) {
    	
    	String hql = "FROM LikeMessage m where m.recipientId = '" + userId + "'";
    	Query query = getSession().createQuery(hql);
    	List<LikeMessage> likemsgs = (List<LikeMessage>)query.setMaxResults(100).list();
    	if(likemsgs==null){
    		likemsgs = new ArrayList<LikeMessage>();
    	}
    	return likemsgs;
    
    }
	
    @SuppressWarnings("unchecked")
    public List<LikeMessage> findLikeMessagesByCustomerId(String userId) {
    	
    	String hql = "FROM LikeMessage m where m.recipientId = '" + userId + "'";
    	Query query = getSession().createQuery(hql);
    	List<LikeMessage> likemsgs = (List<LikeMessage>)query.setMaxResults(100).list();
    	if(likemsgs==null){
    		likemsgs = new ArrayList<LikeMessage>();
    	}
    	return likemsgs;
    }
    
    @SuppressWarnings("unchecked")
    public LikeMessage findLikeMessageByImpressionId(String impressionId){
    	String hql = "FROM LikeMessage m where m.impressionId = '" + impressionId + "'";
    	Query query = getSession().createQuery(hql);
    	return  (LikeMessage)query.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    public LikeMessage findLikeMessageByProductImpressionId(String productImpressionId){
    	String hql = "FROM LikeMessage m where m.productImpressionId = '" + productImpressionId + "'";
    	Query query = getSession().createQuery(hql);
    	return  (LikeMessage)query.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    public void deleteLikeMessage(LikeMessage message) {
    	delete(message);
    }

    @SuppressWarnings("unchecked")
    public Integer readLikeMessages(String userId, String userType, Date date) {
		String formattedDate = "";
    	try{
			String input = date.toString();
	        SimpleDateFormat parser = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
	        date = parser.parse(input);
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	        formattedDate = formatter.format(date);
        }catch(ParseException p){System.out.println("could not parse the date"); }
    	
    	String hql = "UPDATE LikeMessage m set m.readTime ='" + formattedDate + "' where "
    			+ "m.recipientId = '" + userId + "' and "
    			+ "m.recipientType ='" + userType + "' and "
    			+ "m.readTime IS NULL";
    	Query query = getSession().createQuery(hql);
    	 return (Integer)query.executeUpdate();
    }

    
    
}