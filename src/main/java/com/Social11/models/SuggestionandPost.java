package com.Social11.models;

import java.util.List;

import javax.persistence.OneToMany;

public class SuggestionandPost {
	
	@OneToMany
	private List<PostData1> posts;
	
	@OneToMany
	private List<UserEntity> users;
	
	public List<PostData1> getPosts() {
		return posts;
	}
	public void setPosts(List<PostData1> posts) {
		this.posts = posts;
	}
	public List<UserEntity> getUsers() {
		return users;
	}
	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}
	@Override
	public String toString() {
		return "SuggestionandPost [posts=" + posts + ", users=" + users + "]";
	}
	public SuggestionandPost(List<PostData1> posts, List<UserEntity> users) {
		super();
		this.posts = posts;
		this.users = users;
	}
	
	public SuggestionandPost() {
		System.out.println("Suggestion and post class called");
	}
	
	
}
