package com.yanya.springmvc.model;


public class MerchantControlMenuForm{
	
	
	private String merchantControlMenuChoice;
	
	public MerchantControlMenuForm(){}
	public MerchantControlMenuForm(String choice){
		this.merchantControlMenuChoice = choice;
	}

	public String getMerchantControlMenuChoice() {
		return merchantControlMenuChoice;
	}

	public void setMerchantControlMenuChoice(String merchantControlMenuChoice) {
		this.merchantControlMenuChoice = merchantControlMenuChoice;
	}
	
	
}