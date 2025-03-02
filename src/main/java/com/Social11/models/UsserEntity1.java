package com.Social11.models;

public class UsserEntity1 {

	private int id;
	private String email_address;
    private String username;
	private String country;
	private String dateandtime;
	private String firstname;
	private String lastname;
	private String userUrl;
	private String Bio;
	private boolean is_requested;
	private boolean is_sender_request;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail_address() {
		return email_address;
	}
	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDateandtime() {
		return dateandtime;
	}
	public void setDateandtime(String dateandtime) {
		this.dateandtime = dateandtime;
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
	public String getUserUrl() {
		return userUrl;
	}
	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}
	public String getBio() {
		return Bio;
	}
	public void setBio(String bio) {
		Bio = bio;
	}
	public boolean isIs_requested() {
		return is_requested;
	}
	public void setIs_requested(boolean is_requested) {
		this.is_requested = is_requested;
	}
	
	public boolean isIs_sender_request() {
		return is_sender_request;
	}
	public void setIs_sender_request(boolean is_sender_request) {
		this.is_sender_request = is_sender_request;
	}
	
	@Override
	public String toString() {
		return "UsserEntity1 [id=" + id + ", email_address=" + email_address + ", username=" + username + ", country="
				+ country + ", dateandtime=" + dateandtime + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", userUrl=" + userUrl + ", Bio=" + Bio + ", is_requested=" + is_requested + ", is_sender_request="
				+ is_sender_request + "]";
	}
	
	public UsserEntity1() {
		
	}
	
	
}
