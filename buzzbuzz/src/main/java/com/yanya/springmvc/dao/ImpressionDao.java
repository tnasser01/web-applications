package com.yanya.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.yanya.springmvc.model.Impression;
import com.yanya.springmvc.model.SearchFilterForm;
import com.yanya.springmvc.model.ZipCode;
 
public interface ImpressionDao {
 
	@Autowired
	void saveOrUpdateImpression(Impression impression);
	@Autowired
	void saveImpression(Impression impression);
	@Autowired
	void updateImpression(Impression impression);
	@Autowired
	Impression findImpressionByPhotoIdAndUserId(String photoId, String userId);
//	@Autowired
//	Impression findImpressionByProductIdAndUserId(String productId, String userId);
	@Autowired
	List<Impression> findImpressionsByUserPhotoIdsAndUserId(List<String> userPhotoIds, String userId, String userType);

}