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

import com.yanya.springmvc.model.Message;
import com.yanya.springmvc.model.ReadTimeForm;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.User;
import com.yanya.springmvc.model.ZipCode;
import com.yanya.springmvc.dao.AbstractDao;
import com.yanya.springmvc.dao.MessageDao;

 
@Repository("messageDao")
public class MessageDaoImpl extends AbstractDao<String, Message> implements MessageDao {

    @SuppressWarnings("unchecked")
    public Message saveNewMessage(Message message) {
    	String id = (String)save(message);
    	System.out.println("The id returned from hibernate save is:" + id);
    	String hql = "FROM Message c where c.messageId = '" + id + "'";
    	Query query = getSession().createQuery(hql);
    	return  (Message)query.uniqueResult();
   
    }
    @SuppressWarnings("unchecked")
    public void deleteMessage(Message message) {
    	delete(message);
    }
    
    @SuppressWarnings("unchecked")
    public void markMessageRead(ReadTimeForm form){
    	String hql = "UPDATE Message m set m.read_time = '" + form.getReadTime() 
    			+ "' where m.messageId = '" + form.getMessageId() + "' and "
    			+ "m.recipientId = '" + form.getUserId() + "'";
    	Query query = getSession().createQuery(hql);
    	query.executeUpdate();
    }
    
    @SuppressWarnings("unchecked")
    public List<Message> findMessagesByMerchantId(String userId) {
    	
    	String hql = "FROM Message m where m.recipientId = '" + userId +  "' order by m.recieve_time DESC";
    	Query query = getSession().createQuery(hql);
    	List<Message> messages = (List<Message>)query.list();
    	if(messages==null){
    		messages = new ArrayList<Message>();
    	}
    	return messages;  
    }
	
    @SuppressWarnings("unchecked")
    public List<Message> findMessagesByCustomerId(String userId) {
    	
    	String hql = "FROM Message m where m.recipientId = '" + userId + "' order by m.recieve_time DESC";
    	Query query = getSession().createQuery(hql);
    	List<Message> messages = (List<Message>)query.list();
    	if(messages==null){
    		messages = new ArrayList<Message>();
    	}
    	return messages;  
    }
    
    @SuppressWarnings("unchecked")
    public Message findMessageByMessageId(String messageId) {
    	
    	String hql = "FROM Message m where m.messageId = '" + messageId + "'";
    	Query query = getSession().createQuery(hql);
    	return  (Message)query.uniqueResult();
    }


}

    
    
