package com.yanya.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yanya.springmvc.model.Impression;
import com.yanya.springmvc.model.UserPhoto;

import java.lang.Integer;

@Service
public interface ImpressionService {
	
	void saveOrUpdateImpression(Impression impression);
	void saveImpression(Impression impression,  HttpServletRequest request);
	void updateImpression(Impression impression,  HttpServletRequest request);
	Impression findImpressionByPhotoIdAndUserId(String photoId, String userId);
//	Impression findImpressionByProductIdAndUserId(String productId, String userId);	
	HashMap<String,String> findImpressionsByUserPhotoIdsAndUserId(List<UserPhoto> userPhotos, String userId, String userType);
}