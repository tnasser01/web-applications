package com.yanya.springmvc.model;

import java.util.List;
import java.util.ArrayList;

public class SearchFilterForm {

	private String searchTerm;
    private int distance;

	public String getSearchTerm() {
		return searchTerm;
	}
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}


}