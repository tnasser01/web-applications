package com.yanya.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.yanya.springmvc.model.FriendInvite;
 
public interface InviteDao {
 
	@Autowired
	FriendInvite saveNewInvite(FriendInvite invite);
	
	@Autowired
	FriendInvite findByInviteCode(String inviteCode);
	
	@Autowired
	void updateInviteStatus(String inviteId, String status);
	
}