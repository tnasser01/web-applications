package com.yanya.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.yanya.springmvc.model.Merchant;
import com.yanya.springmvc.model.ZipCode;
 
public interface ZipDao {
 
	@Autowired
    ZipCode findLatAndLng(String zipCode);
	List<String> findZipsInRange(double latMax, double latMin, double lngMax, double lngMin);
 
}