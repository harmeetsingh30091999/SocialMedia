package com.Social11.models;

import javax.persistence.Id;

public class Friend_request {
	

	private String username;
	private String profile_url;
	private String firstname;
	private String lastname;
	private int friend_request_id;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getProfile_url() {
		return profile_url;
	}
	public void setProfile_url(String profile_url) {
		this.profile_url = profile_url;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public int getFriend_request_id() {
		return friend_request_id;
	}
	public void setFriend_request_id(int friend_request_id) {
		this.friend_request_id = friend_request_id;
	}
	@Override
	public String toString() {
		return "Friend_request [username=" + username + ", profile_url=" + profile_url + ", firstname=" + firstname
				+ ", lastname=" + lastname + "]";
	}
	
	
	public Friend_request(String username, String profile_url, String firstname, String lastname) {
		super();
		this.username = username;
		this.profile_url = profile_url;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	public Friend_request() {
		System.out.print("Friend_request"); 
	}
	
	
	
}
