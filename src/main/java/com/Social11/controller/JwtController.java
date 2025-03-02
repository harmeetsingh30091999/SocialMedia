package com.Social11.controller;

import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Social11.Dao.JwtBlackListRepository;
import com.Social11.helper.JwtTokenUtil;
import com.Social11.helper.JwtUserDetailService;
import com.Social11.models.CustomUserDetails1;
import com.Social11.models.JwtBlackList;
import com.Social11.models.JwtRequest;
import com.Social11.models.UserLogin;
import com.Social11.security.CustomUserDetails;
import com.Social11.service.UserService;

@RestController
public class JwtController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailService userDetailsService;
	
	@Autowired
	private JwtBlackListRepository jwtRepository;

	@Autowired
	private UserService userservice;
	
	
	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		String name="";
		String image_path ="https://jaiyto.com/images/";
		String isUsernameorEmail = authenticationRequest.getUsername();
		name=isUsernameorEmail.substring(isUsernameorEmail.length()-4, isUsernameorEmail.length());
		String username=null;
		if(name.equals(".com")) {
//			Email address 
				username = userservice.findmyusername(isUsernameorEmail);
//				System.out.println(username);
			if(username!=null) {
				authenticate(username, authenticationRequest.getPassword());
				System.out.println("Coming here111");
			}
		}
		else {		
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
			System.out.println("Coming here");
		}
		
		if(username==null) {
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		CustomUserDetails1 details = new CustomUserDetails1();
		if(userDetailsService.user_url()!=null)
			details.setProfile_url(image_path+userDetailsService.user_url());
			details.setUsername(userDetails.getUsername());
			details.setPassword(userDetails.getPassword());
			details.setAccountNonExpired(true);
			details.setAccountNonLocked(true);
			details.setEnabled(true);
			details.setCredentialsNonExpired(true);
			details.setUser_id(userDetailsService.get_user_id());
		
			final String token = jwtTokenUtil.generateToken(userDetails);
			UserLogin userLogin = new UserLogin();
			userLogin.setAccess_token(token);
			userLogin.setUserDetails(details);
			return ResponseEntity.ok(userLogin);
		}
		else {
			final UserDetails userDetails = userDetailsService
					.loadUserByUsername(username);
			CustomUserDetails1 details = new CustomUserDetails1();
			if(userDetailsService.user_url()!=null)
				details.setProfile_url(image_path+userDetailsService.user_url());
				details.setUsername(userDetails.getUsername());
				details.setPassword(userDetails.getPassword());
				details.setAccountNonExpired(true);
				details.setAccountNonLocked(true);
				details.setEnabled(true);
				details.setCredentialsNonExpired(true);
				details.setUser_id(userDetailsService.get_user_id());
			
			final String token = jwtTokenUtil.generateToken(userDetails);
			UserLogin userLogin = new UserLogin();
			userLogin.setAccess_token(token);
			userLogin.setUserDetails(details);			
			return ResponseEntity.ok(userLogin);
		}
	
	}
	
	@PostMapping("/logout2")
	public Map<String,String> logoutuserwithtoken(@RequestBody Map<String,String> json){
		String token = json.get("jwt");
		System.out.println(token);
	    JwtBlackList jwtBlacklist = new JwtBlackList();
	    jwtBlacklist.setToken(token);
	    jwtRepository.save(jwtBlacklist);
//	    jwtRepository.blacklisttoken(1010, token);
	    Map<String,String> mp = new HashMap<>();
	    mp.put("response", "success");
	    mp.put("msg", "user logout succesfully");
	    return mp;
	}
	
	private void authenticate(String username, String password) throws Exception {
//		System.out.println("working or not");
		try {
			System.out.println(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED");
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid Username or Password");
		}
	}

}
