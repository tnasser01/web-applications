 package com.yanya.springmvc.dao;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yanya.springmvc.model.User;
 
public interface UserDao {

	@Autowired
	User saveUser(User user);
	@Autowired
  User findUserByUsername(String username);
	@Autowired
	User findUserByUserId(String userId);
  @Autowired
  void updatePassword(String userId, String password);
}