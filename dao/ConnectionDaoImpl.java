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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;

import com.yanya.springmvc.model.Connection;
import com.yanya.springmvc.dao.AbstractDao;
import com.yanya.springmvc.dao.ConnectionDao;

 
@Repository("connectionDao")
public class ConnectionDaoImpl extends AbstractDao<String, Connection> implements ConnectionDao {

    @SuppressWarnings("unchecked")
    public Connection saveConnection(Connection connection) {
    	String id = (String)save(connection);
    	System.out.println("The id returned from hibernate save is:" + id);
    	String hql = "FROM Connection c where c.connectionId = '" + id + "'";
    	Query query = getSession().createQuery(hql);
    	return  (Connection)query.uniqueResult();
   
    }
    
    @SuppressWarnings("unchecked")
    public void deleteConnection(Connection connection) {
    	delete(connection);  
    }
	
    @SuppressWarnings("unchecked")
    public Connection findConnectionByConnectionId(String connectionId) {
    	System.out.println("The inputted connectionId to search for is:" + connectionId);
    	String hql = "FROM Connection c where c.connectionId = '" + connectionId + "'";
    	Query query = getSession().createQuery(hql);
    	return  (Connection)query.uniqueResult();
    }
    

    @SuppressWarnings("unchecked")
    public Connection findConnectionByUsers(String user1Id, String user2Id){   	
    	String hql = "FROM Connection c where c.requesterId = '" + user1Id + "' and c.requesteeId = '" + user2Id + "'";
    	Query query = getSession().createQuery(hql);
    	Connection conn = (Connection)query.uniqueResult();
    	if(conn != null)
    		return conn;
    	else{
        	String hql2 = "FROM Connection c where c.requesterId = '" + user2Id + "' and c.requesteeId = '" + user1Id + "'";
        	Query query2 = getSession().createQuery(hql2);
        	Connection conn2 = (Connection)query2.uniqueResult();
        	if(conn2 != null)
        		return conn2;	
    	}
    	return null;
    			
    			
    }
    
    @SuppressWarnings("unchecked")
    public List<Connection> findAcceptedConnections(String userId, String userType){   	
    	String hql = "FROM Connection c where c.status = 'accepted' and ((c.requesterId =:userId and c.requesterType =:userType) or (c.requesteeId =:userId and c.requesteeType =:userType))";
    	Query query = getSession().createQuery(hql);
    	List<Connection> conn = (List<Connection>)query.list();
    	if(conn != null)
    		return conn;
    	return null;  			
    }

    @SuppressWarnings("unchecked")
    public Connection findConnectionByUsers(String user1Id, String user2Id, String status){   	
    	String hql = "FROM Connection c where c.requesterId = '" + user1Id + "' and c.requesteeId = '" + user2Id + "' and c.status = '" + status + "'";
    	Query query = getSession().createQuery(hql);
    	Connection conn = (Connection)query.uniqueResult();
    	if(conn != null)
    		return conn;
    	else{
        	String hql2 = "FROM Connection c where c.requesterId = '" + user2Id + "' and c.requesteeId = '" + user1Id + "' and c.status = '" + status + "'";
        	Query query2 = getSession().createQuery(hql2);
        	Connection conn2 = (Connection)query2.uniqueResult();
        	if(conn2 != null)
        		return conn2;	
    	}
    	return null;
    			
    			
    }
    
    @SuppressWarnings("unchecked")    
	public List<Connection> findUserConnectionsByType(String userId, String type){
    	String hql = 
    			"FROM Connection c where c.status=:accepted and ((c.requesteeId =:userId AND c.requesterType =:type) OR (c.requesterId =:userId AND c.requesteeType =:type))";
    	
    	Query query = getSession().createQuery(hql);
    	query.setString("accepted", "accepted");
    	query.setString("userId", userId);
    	query.setString("type", type);
    	return (List<Connection>)query.list();
    
    	 
    }
    

    @SuppressWarnings("unchecked")
	public List<Connection> findConnectionRequestsByRequesteeId(String requesteeId){
    	String hql = "FROM Connection c where c.requesteeId = '" + requesteeId + "' and c.status = 'requested'";
    	Query query = getSession().createQuery(hql);
    	return (List<Connection>)query.list();
    }
      
    @SuppressWarnings("unchecked")
    public Connection respondToConnectionRequest(String connectionId, String status){
    	String hql = "update Connection c set c.status=:status where c.connectionId=:connectionId ";
    	Query query = getSession().createQuery(hql);
    	query.setParameter("connectionId", connectionId);
    	query.setParameter("status", status);
    	query.executeUpdate();
    	
    	String hql2 = "from Connection c where c.connectionId=:connectionId ";
    	Query query2 = getSession().createQuery(hql2);
    	query2.setParameter("connectionId", connectionId);
    	return (Connection)query2.uniqueResult();
    }

    

    @SuppressWarnings("unchecked")
    public Integer readConnectionRequests(String userId, String userType) {
		String formattedDate = "";

    	String hql = "UPDATE Connection m set m.readTime ='" + 1 + "' where "
    			+ "m.requesteeId = '" + userId + "' and "
    			+ "m.requesteeType ='" + userType + "' and "
    			+ "m.readTime IS NULL";
    	Query query = getSession().createQuery(hql);
    	 return (Integer)query.executeUpdate();
    }
   
}