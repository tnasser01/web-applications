package com.yanya.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import com.yanya.springmvc.model.Connection;
 
public interface ConnectionDao {
 
	@Autowired
	Connection saveConnection(Connection connection);
	
	@Autowired
	void deleteConnection(Connection connection);
	
	@Autowired
    Connection findConnectionByConnectionId(String connectionId);
		
	@Autowired
	Connection findConnectionByUsers(String user1Id, String user2Id);

	@Autowired
	Connection findConnectionByUsers(String user1Id, String user2Id, String status);

	@Autowired
    public List<Connection> findAcceptedConnections(String userId, String userType);  
			
	@Autowired
	List<Connection> findUserConnectionsByType(String userId, String type);
	
	@Autowired
	List<Connection> findConnectionRequestsByRequesteeId(String requesteeId);

	@Autowired
	Connection respondToConnectionRequest(String connectionId, String status);
	
	@Autowired
	Integer readConnectionRequests(String userId, String userType);
}