package com.Social11.helper;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Social11.Dao.IuserRepository;
import com.Social11.models.UserEntity;

@Service
public class JwtUserDetailService implements UserDetailsService {
	
	public String user_url=null;
	
	public int user_id = 0;

	@Autowired
	private IuserRepository repository;

	String User;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println(username);
		UserEntity user=repository.findByusername(username);
		System.out.println(user.getId());
		User=user.getUsername();
		if(user!=null) {
			if(user.getUserUrl()!=null)
				user_url=user.getUserUrl();
				user_id = user.getId();
				System.out.println("getting the userid:"+user_id);
				return new User(user.getUsername(),user.getPassword(), new ArrayList<>()); 
		}
		else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
//		if ("javainuse".equals(username)) {
////			return new User("javainuse", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
////					new ArrayList<>());
//		} else {
//			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
	}
	
	public String currentLoggedInUser() {
		return User;
	}
	
	public String user_url() {
		return user_url;
	}
	
	public int get_user_id() {
		return user_id;
	}
	
}
