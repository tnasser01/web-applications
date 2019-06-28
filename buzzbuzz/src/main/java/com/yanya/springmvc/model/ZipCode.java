package com.yanya.springmvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ZipCode" )
public class ZipCode{
	
	@Id
	@Column(name = "zip")
	String zip;
	@Column(name = "lat")
	double lat;
	@Column(name = "lng")
	double lng;
	
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public boolean equals(ZipCode other){
		boolean result = false;
	    if (other instanceof ZipCode) {
	        ZipCode that = (ZipCode) other;
	        result = (this.getZip().equals(that.getZip()));
	    }
	    return result;
	}
	
    @Override
    public int hashCode() {
        return (41 * ( (41 + Integer.parseInt(zip)) ));
    }
	
	
	
	
}