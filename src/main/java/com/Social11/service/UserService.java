package com.Social11.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Social11.models.UserEntity;

@Service
public interface UserService {

	public List<UserEntity> friendSuggestion(int userid);
	
	public boolean switchprivatepublic(int userid, boolean accountmode);
	
	public boolean isaccountprivate(int friendid);
	
	public String findmyusername(String email);
	
}
