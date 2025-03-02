package com.Social11.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Social11.Dao.IuserRepository;
import com.Social11.models.UserEntity;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private IuserRepository repository;
	
	@Override
	public List<UserEntity> friendSuggestion(int userid) {
		try {
			List<UserEntity> suggestion = this.repository.friendSuggestion(userid);
			return suggestion;
		}
		catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public boolean switchprivatepublic(int userid,boolean accountmode) {
		try {
			this.repository.accountswitch(userid, accountmode);
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isaccountprivate(int friendid) {
		try {
			return this.repository.isprivateaccount(friendid);
		}
		catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public String findmyusername(String email) {
		try {
			return this.repository.findByemail1(email);
		}
		catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}

}
