package com.Social11.models;

import javax.persistence.Entity;
import javax.persistence.Id;


import org.springframework.security.core.userdetails.UserDetails;

public class UserLogin {
    private String access_token;
    private CustomUserDetails1 userDetails;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

	public CustomUserDetails1 getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(CustomUserDetails1 userDetails) {
		this.userDetails = userDetails;
	}

    

    

    

}
	
	

