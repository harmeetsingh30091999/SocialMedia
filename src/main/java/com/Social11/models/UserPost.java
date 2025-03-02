package com.Social11.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_post")
public class UserPost{

	
	@Id
   	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="user_id")
	private int user_id;
	
	@Column(name="post_id")
	private int post_id;
	
//	private int post_report;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

//	public int getPost_report() {
//		return post_report;
//	}
//
//	public void setPost_report(int post_report) {
//		this.post_report = post_report;
//	}

	public UserPost() {
		System.out.println("User post class called");
	}

	@Override
	public String toString() {
		return "UserPost [id=" + id + ", user_id=" + user_id + ", post_id=" + post_id + ", post_report=" 
				+ "]";
	}

	public UserPost(int id, int user_id, int post_id, int post_report) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.post_id = post_id;

	}

	
}
