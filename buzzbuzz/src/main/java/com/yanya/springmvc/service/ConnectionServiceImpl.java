package com.yanya.springmvc.service;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanya.springmvc.dao.ConnectionDao;
import com.yanya.springmvc.dao.CustomerDao;
import com.yanya.springmvc.dao.MerchantDao;
import com.yanya.springmvc.dao.NotificationDao;
import com.yanya.springmvc.model.Connection;
import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.service.ConnectionService;
import com.yanya.springmvc.event.NotificationEventProducer;

@Service("connectionService")
@Transactional
public class ConnectionServiceImpl implements ConnectionService {
    
    @Autowired
    private ConnectionDao connectionDao;
    
    @Autowired
    private CustomerDao customerDao;
    
    @Autowired
    private MerchantDao merchantDao;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private NotificationEventProducer producer;
    
    public Connection saveConnection(Connection connection) {
    	//send notification to merchant
    	Connection conn = connectionDao.saveConnection(connection);
    	notificationService.createConnectionRequestNotification(connection);
    	return conn;
    }
    
    public void deleteConnection(Connection connection) {
    	connectionDao.deleteConnection(connection);
    }
    
    public Connection findConnectionByConnectionId(String connectionId){
    	return connectionDao.findConnectionByConnectionId(connectionId);
    }
    
    public Connection findConnectionByUsers(String user1Id, String user2Id){
    	return connectionDao.findConnectionByUsers(user1Id, user2Id);
    }
    
    public Connection findConnectionByUsers(String user1Id, String user2Id, String status){	
    	return connectionDao.findConnectionByUsers(user1Id, user2Id, status);
    }

    public List<Connection> findConnectionRequestsByRequesteeId(String requesteeId){
    	return connectionDao.findConnectionRequestsByRequesteeId(requesteeId);
    }
    
    public Connection respondToConnectionRequest(String connectionId, String status, HttpServletRequest request){
    	Connection conn = findConnectionByConnectionId(connectionId);
    	if(status.equals("accepted")){   		
    		producer.createConnectionAcceptNotification(conn, request, "increment", conn.getRequesterId(), conn.getRequesterType());
    	}else if(status.equals("rejected")){
    		deleteConnection(conn);
    		return  null;
    	}else if(status.equals("cancelled")){
    		deleteConnection(conn);   
    		return null;
    	}
    	
    	return connectionDao.respondToConnectionRequest(connectionId, status);
    }
    
    public 	List<Connection> findUserConnectionsByType(String userId, String type){
    	return connectionDao.findUserConnectionsByType(userId, type);
    }
    
    @Cacheable("connectedMerchants")
    public 	List<Merchant> findUserConnectedMerchants(String userId){
    	List<Connection> connections = connectionDao.findUserConnectionsByType(userId, "merchant");
    	List<String> connectedMerchantIds = new ArrayList<String>();    	
    	List<Merchant> merchants = new ArrayList<Merchant>();    	
    	if(connections!=null && connections.size()> 0){
	    	for(Connection c: connections){
	    		if(c.getRequesterId().equals(userId)){
	    			connectedMerchantIds.add(c.getRequesteeId());
	    		}else{
	    			connectedMerchantIds.add(c.getRequesterId());
	    		}
	    	}
    	if(connectedMerchantIds!=null && connectedMerchantIds.size() > 0){
    		merchants = merchantDao.findMerchantsByMerchantIds(connectedMerchantIds);
    	}
    	
    	}
    	return merchants;
    	
    	
    }
    
    @Cacheable("connectedFriends")
    public 	List<Customer> findUserConnectedFriends(String userId){
    	List<Connection> connections = connectionDao.findUserConnectionsByType(userId, "customer");
    	List<String> connectedCustomerIds = new ArrayList<String>();    	
    	List<Customer> friends = new ArrayList<Customer>();
    	if(connections!=null && connections.size()> 0){
	    	for(Connection c: connections){
	    		if(c.getRequesterId().equals(userId)){
	    			connectedCustomerIds.add(c.getRequesteeId());
	    		}else{
	    			connectedCustomerIds.add(c.getRequesterId());
	    		}
	    	}
	    	if(connectedCustomerIds!=null && connectedCustomerIds.size() > 0){
	    		friends = customerDao.findCustomersByCustomerIds(connectedCustomerIds);
	    	}
    	}
    	
    	return friends;
    	
    }
    
    public void readConnectionRequests(String userId, String userType,  HttpServletRequest model){
    	Integer numOfMsgRead = connectionDao.readConnectionRequests(userId, userType);
    	
    }

}
	
    
   
    
 
 
 
 
