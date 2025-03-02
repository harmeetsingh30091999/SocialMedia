package com.Social11.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class UserProfile {

	@Id
	private int profile_id;
	private int totalPost;
	private int totalFriends;
	private String username;
	private String profile_url;
	private String Bio;
	private String email;
	private String firstname;
	private String lastname;
	private boolean is_private;
	private boolean is_friend;
	private boolean is_requested;
	private boolean is_sender_request;
	
	@OneToMany
	private List<PostData1> Allpost;
	public int getProfile_id() {
		return profile_id;
	}
	public void setProfile_id(int profile_id) {
		this.profile_id = profile_id;
	}
	public int getTotalPost() {
		return totalPost;
	}
	public void setTotalPost(int totalPost) {
		this.totalPost = totalPost;
	}
	public int getTotalFriends() {
		return totalFriends;
	}
	public void setTotalFriends(int totalFriends) {
		this.totalFriends = totalFriends;
	}
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
		
	public List<PostData1> getAllpost() {
		return Allpost;
	}
	public void setAllpost(List<PostData1> allpost) {
		Allpost = allpost;
	}
	
	public String getBio() {
		return Bio;
	}
	public void setBio(String bio) {
		Bio = bio;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isIs_private() {
		return is_private;
	}
	public void setIs_private(boolean is_private) {
		this.is_private = is_private;
	}
	public boolean isIs_friend() {
		return is_friend;
	}
	public void setIs_friend(boolean is_friend) {
		this.is_friend = is_friend;
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
		return "UserProfile [profile_id=" + profile_id + ", totalPost=" + totalPost + ", totalFriends=" + totalFriends
				+ ", username=" + username + ", profile_url=" + profile_url + ", Bio=" + Bio + ", email=" + email
				+ ", Allpost=" + Allpost + "]";
	}
	public UserProfile(int profile_id, int totalPost, int totalFriends, String username, String profile_url, String bio,
			String email, List<PostData1> allpost) {
		super();
		this.profile_id = profile_id;
		this.totalPost = totalPost;
		this.totalFriends = totalFriends;
		this.username = username;
		this.profile_url = profile_url;
		Bio = bio;
		this.email = email;
		Allpost = allpost;
	}
	public UserProfile() {
		System.out.println("User profile class called");
	}
	
}
