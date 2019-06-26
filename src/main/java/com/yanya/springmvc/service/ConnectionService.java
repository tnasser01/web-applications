package com.yanya.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yanya.springmvc.model.Connection;
import com.yanya.springmvc.model.Customer;
import com.yanya.springmvc.model.Merchant;


@Service
public interface ConnectionService {
	
	Connection saveConnection(Connection connection);
	Connection findConnectionByConnectionId(String connectionId);
	Connection findConnectionByUsers(String user1Id, String user2Id);
	Connection findConnectionByUsers(String user1Id, String user2Id, String status);
	List<Connection> findConnectionRequestsByRequesteeId(String requesteeId);
	Connection respondToConnectionRequest(String connectionId, String status, HttpServletRequest model);
	List<Connection> findUserConnectionsByType(String userId, String type);
  void readConnectionRequests(String userId, String userType,  HttpServletRequest model);
  List<Merchant> findUserConnectedMerchants(String userId);   
  List<Customer> findUserConnectedFriends(String userId);

}