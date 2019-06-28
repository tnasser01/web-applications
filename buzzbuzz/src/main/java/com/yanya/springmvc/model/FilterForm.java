package com.yanya.springmvc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.yanya.springmvc.model.Product;

import java.io.IOException;
import java.lang.reflect.Field;

public class FilterForm{
	
	private HashMap<String, String> ratings;
	private String lowPrice;
	private String highPrice;
	private String range;
	private String distance;
	private String zip;
	private HashMap<String,String> names;
 	private HashMap<String, String> strains;
 	private HashMap<String, String> types;
 	
	
	public FilterForm() {}
	

	public FilterForm(HashMap<String, String> ratings, String lowPrice, String highPrice, String range, String zip,
			HashMap<String, String> names, HashMap<String, String> strains, HashMap<String, String> types) {
	
		this.ratings = ratings;
		this.lowPrice = lowPrice;
		this.highPrice = highPrice;
		this.range = range;
		this.distance = "0.0";
		this.zip = zip;
		this.names = names;
		this.strains = strains;
		this.types = types;

	}
	
	public Boolean isProductCurrentlyWithinRange(Product p){
		//current lat and lng of product 
		System.out.println("inside productcurrentlywithinrange method");
		System.out.println("product name is: " + p.getProductName());
		System.out.println("product merchant is : " + p.getMerchant().getStoreName() + " " + p.getMerchant().getUserId());
		System.out.println("merchant status is : " + p.getMerchant().getStatus());
		System.out.println("product lng is : " + p.getMerchant().getLng());
		System.out.println("product lat is : " + p.getMerchant().getLat());
		Double searchLng = Double.valueOf(p.getMerchant().getLng());

		
		Double searchLat = Double.valueOf(p.getMerchant().getLat());

 		//find lat and long of filter zip
		Double filterLng = 0.0; 
		Double filterLat = 0.0;	
				
		try{
			GeoApiContext context = new GeoApiContext.Builder()
				    .apiKey("AIzaSyD0OEWclbmhjnoJEY0bWAa8WLnlfynXUUQ")
				    .build();

			GeocodingResult[] results =  GeocodingApi.geocode(context, zip).await();
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				filterLng = Double.valueOf(gson.toJson(results[0].geometry.location.lng));
				filterLat = Double.valueOf(gson.toJson(results[0].geometry.location.lat));

				System.out.println(filterLng);
				System.out.println(filterLat);
		
		}catch(ApiException | InterruptedException | IOException e){}
	
		Double d = distFrom(filterLat, filterLng, searchLat, searchLng);
		System.out.println("****************** Calculated distance is: " + d );
		distance = String.valueOf(d);
		System.out.println("%%%%%%%%% DISTANCE is: " + distance);
		
		Boolean isWithinRange = false;
		if(Double.valueOf(distance) < Double.valueOf(range)){
			isWithinRange = true;
		}
		
		return isWithinRange;
	}
	
	 public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
		    double earthRadius = 6369; //miles
		    double dLat = Math.toRadians(lat2-lat1);
		    double dLng = Math.toRadians(lng2-lng1);
		    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
		               Math.sin(dLng/2) * Math.sin(dLng/2);
		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		    double dist = (double) (earthRadius * c);

		    return dist;
		    }


	public HashMap<String, String> getRatings() {
		return ratings;
	}


	public void setRatings(HashMap<String, String> ratings) {
		this.ratings = ratings;
	}


	public String getLowPrice() {
		return lowPrice;
	}


	public void setLowPrice(String lowPrice) {
		this.lowPrice = lowPrice;
	}


	public String getHighPrice() {
		return highPrice;
	}


	public void setHighPrice(String highPrice) {
		this.highPrice = highPrice;
	}


	public String getDistance() {
		return distance;
	}


	public void setDistance(String distance) {
		this.distance = distance;
	}


	public HashMap<String, String> getNames() {
		return names;
	}


	public void setNames(HashMap<String, String> names) {
		this.names = names;
	}


	public HashMap<String, String> getStrains() {
		return strains;
	}


	public void setStrains(HashMap<String, String> strains) {
		this.strains = strains;
	}


	public HashMap<String, String> getTypes() {
		return types;
	}


	public void setTypes(HashMap<String, String> types) {
		this.types = types;
	}
	public String toString() {
		  StringBuilder result = new StringBuilder();
		  String newLine = System.getProperty("line.separator");

		  result.append( this.getClass().getName() );
		  result.append( " Object {" );
		  result.append(newLine);

		  //determine fields declared in this class only (no fields of superclass)
		  Field[] fields = this.getClass().getDeclaredFields();

		  //print field names paired with their values
		  for ( Field field : fields  ) {
		    result.append("  ");
		    try {
		      result.append( field.getName() );
		      result.append(": ");
		      //requires access to private field:
		      result.append( field.get(this) );
		    } catch ( IllegalAccessException ex ) {
		      System.out.println(ex);
		    }
		    result.append(newLine);
		  }
		  result.append("}");

		  return result.toString();
		}


	public String getZip() {
		return zip;
	}


	public void setZip(String zip) {
		this.zip = zip;
	}


	public String getRange() {
		return range;
	}


	public void setRange(String range) {
		this.range = range;
	}




}