package com.yanya.springmvc.service;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanya.springmvc.dao.InviteDao;
import com.yanya.springmvc.model.FriendInvite;
import com.yanya.springmvc.service.InviteService;


import java.lang.Integer;

@Service("friendInviteService")
@Transactional
public class InviteServiceImpl implements InviteService {
 
    @Autowired
    private InviteDao inviteDao;
    
    public FriendInvite saveNewInvite(FriendInvite friendInvite) {	
    	return inviteDao.saveNewInvite(friendInvite);
    }
    
    public FriendInvite generateInviteCode(FriendInvite invite){
		int inviteCode;
		int Low = 10;
		int High = 100000;
    	do{
    		Random r = new Random();
    		inviteCode = r.nextInt(High-Low) + Low;
		}while(findByInviteCode(String.valueOf(inviteCode))!=null);
    	invite.setInviteCode(String.valueOf(inviteCode));  	
    	return invite;
    }
      
    public FriendInvite findByInviteCode(String inviteCode){
    	return inviteDao.findByInviteCode(inviteCode);
    }
    
    public void updateFriendInviteStatus(String friendInviteId, String newStatus){
    	 inviteDao.updateInviteStatus(friendInviteId, newStatus);
    }


	
    
   
    
 
 
 
 
}