package com.yanya.springmvc.service;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanya.springmvc.dao.ImpressionDao;
import com.yanya.springmvc.event.NotificationEventProducer;
import com.yanya.springmvc.model.Impression;
import com.yanya.springmvc.service.ImpressionService;

import com.yanya.springmvc.model.ProductImpression;
import com.yanya.springmvc.model.UserPhoto;

@Service("impressionService")
@Transactional
public class ImpressionServiceImpl implements ImpressionService {
 
    @Autowired
    private ImpressionDao impressionDao;
    
    @Autowired
    private NotificationEventProducer producer;
    
    public void saveOrUpdateImpression(Impression impression) {
    	 impressionDao.saveOrUpdateImpression(impression);
    }
    
    public void saveImpression(Impression impression, HttpServletRequest request) {
   	 impressionDao.saveImpression(impression);
   	 System.out.println("@@@@@@@impression object saved");
 	 producer.createLikeNotification(impression, request, "increment", impression.getUserPhoto().getCustomer().getUserId(), "customer");

    }
    public void updateImpression(Impression impression, HttpServletRequest request) {
   	 impressionDao.updateImpression(impression);
   	 
   	 if(impression.getImpressionType().equals("like")){
   		 producer.createLikeNotification(impression, request, "increment", impression.getUserPhoto().getCustomer().getUserId(), "customer");	 
   	 }else if(impression.getImpressionType().equals("unlike")){	 
   		 producer.createLikeNotification(impression, request, "decrement", impression.getUserPhoto().getCustomer().getUserId(), "customer");	 
   	 }
   	 
    }
 
    public Impression findImpressionByPhotoIdAndUserId(String photoId, String userId){
    	return impressionDao.findImpressionByPhotoIdAndUserId(photoId, userId);
    }
    

    
   @Cacheable("impressions")
    public HashMap<String, String> findImpressionsByUserPhotoIdsAndUserId(List<UserPhoto> userPhotos, String userId, String userType){
		List<String> userPhotoIds = new ArrayList<String>();
    	if(userPhotos!=null){
    		for(UserPhoto p: userPhotos){
    			userPhotoIds.add(p.getUserPhotoId());
    		}
    	}
    	
		List<Impression> impressions = impressionDao.findImpressionsByUserPhotoIdsAndUserId(userPhotoIds, userId, userType);
		
		if(impressions==null){
			return new HashMap<String, String>();
		}
		
		HashMap<String, String> impressionsMap = new HashMap<String, String>();

		for(Impression i: impressions){
				if(i.getImpressionType().equals("like")){
					impressionsMap.put(i.getUserPhoto().getUserPhotoId(), "like");
				}else{
					impressionsMap.put(i.getUserPhoto().getUserPhotoId(), "unlike");
				}
		}
		
		
		return impressionsMap;
    }
}