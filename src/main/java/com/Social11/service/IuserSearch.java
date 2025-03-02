package com.Social11.service;

import java.util.List;

import com.Social11.models.UserEntity;

public interface IuserSearch {

	public List<UserEntity> searchbyusername(String username , int pagenumber);
	
}
