package com.Social11.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetails implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

//	@Autowired
//	IuserRepository userRepository;
//
//	String User;
//	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		
//		UserEntity user=userRepository.findByusername(username);
//		System.out.println(user);
//		User=user.getUsername();
//		
//		if(user!=null) {
//			return new User(user.getUsername(),user.getPassword(), new ArrayList<>()); 
//		}
//		else{
//			throw new UsernameNotFoundException("User not Found");
//		}
////		if(username.equals("harmeet_singh")) {
////			return new User("harmeet_singh","Harmeet1234",new ArrayList<>());
////		}
////		else {
////			throw new UsernameNotFoundException("User not found!!");
////		}
//	}
//	
//		public String currentLoggedInUser() {
//			return User;
//		}
//
////	@Override
////	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException,DataAccessException {
////		// TODO Auto-generated method stub
////		
////		CurrentUser user = null;
////		List authorities = new ArrayList();
////		
////		UserEntity currentuser = userRepository.findByusername(username);
////		
////		return null;
////	}
	
}
