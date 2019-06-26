package com.yanya.springmvc.model;

public enum RoleEnum {  
//    ROLE_ADMIN("role_admin"), 
    ROLE_CUSTOMER("role_customer"),
    ROLE_MERCHANT("role_merchant");
 
    String name; 
 
    RoleEnum(String name) { 
        this.name = name; 
    } 
 
    public String getName() { 
        return name; 
    } 
 
}