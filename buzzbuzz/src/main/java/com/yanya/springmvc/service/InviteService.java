package com.yanya.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.yanya.springmvc.model.FriendInvite;

@Service
public interface InviteService {
	
	FriendInvite saveNewInvite(FriendInvite friendInvite);
    FriendInvite generateInviteCode(FriendInvite invite);
    FriendInvite findByInviteCode(String inviteCode);
    void updateFriendInviteStatus(String friendInviteId, String newStatus);	
}