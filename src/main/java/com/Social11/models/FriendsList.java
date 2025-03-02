package com.Social11.models;

import javax.persistence.Entity;
import javax.persistence.Id;


public class FriendsList {

	private int id;
	private String username;
	private String userUrl;
	private boolean ismututalFriend;
	private String firstname;
	private String lastname;
	private boolean isrequested;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserUrl() {
		return userUrl;
	}
	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}
	public boolean isIsmututalFriend() {
		return ismututalFriend;
	}
	public void setIsmututalFriend(boolean ismututalFriend) {
		this.ismututalFriend = ismututalFriend;
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
	public boolean isIsrequested() {
		return isrequested;
	}
	public void setIsrequested(boolean isrequested) {
		this.isrequested = isrequested;
	}
	
	public FriendsList(int id, String username, String userUrl, boolean ismututalFriend, String firstname,
			String lastname, boolean isrequested) {
		super();
		this.id = id;
		this.username = username;
		this.userUrl = userUrl;
		this.ismututalFriend = ismututalFriend;
		this.firstname = firstname;
		this.lastname = lastname;
		this.isrequested = isrequested;
	}

	public FriendsList() {
		System.out.println("Friendlist");
	}
	@Override
	public String toString() {
		return "FriendsList [id=" + id + ", username=" + username + ", userUrl=" + userUrl + ", ismututalFriend="
				+ ismututalFriend + ", firstname=" + firstname + ", lastname=" + lastname + ", isrequested="
				+ isrequested + "]";
	}
	
	
	
	
}
