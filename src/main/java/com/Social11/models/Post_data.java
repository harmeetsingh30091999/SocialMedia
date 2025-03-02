package com.Social11.models;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="post_da1")
public class Post_data {

	@Id
	@Column(name="Post_id")
	@GeneratedValue(strategy=GenerationType.AUTO) 
	private int id;
	@Column(length=2000)
	private String post_title;
	private String post_data;
	private String[] post_img_url;
	private String post_video_url;
	private Date date1;
	private String username;
	private String user_url;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPost_title() {
		return post_title;
	}
	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}
	public String getPost_data() {
		return post_data;
	}
	public void setPost_data(String post_data) {
		this.post_data = post_data;
	}
	
	public String[] getPost_img_url() {
		return post_img_url;
	}
	public void setPost_img_url(String[] post_img_url) {
		this.post_img_url = post_img_url;
	}
	public String getPost_video_url() {
		return post_video_url;
	}
	public void setPost_video_url(String post_video_url) {
		this.post_video_url = post_video_url;
	}
	public Date getDate1() {
		return date1;
	}
	public void setDate1(Date date1) {
		this.date1 = date1;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUser_url() {
		return user_url;
	}
	public void setUser_url(String user_url) {
		this.user_url = user_url;
	}
	@Override
	public String toString() {
		return "Post_data [id=" + id + ", post_title=" + post_title + ", post_data=" + post_data + ", post_img_url="
				+ post_img_url + ", post_video_url=" + post_video_url + ", date1=" + date1 + ", username=" + username
				+ ", user_url=" + user_url + "]";
	}
	public Post_data(int id, String post_title, String post_data, String[] post_img_url, String post_video_url,
			Date date1, String username, String user_url) {
		super();
		this.id = id;
		this.post_title = post_title;
		this.post_data = post_data;
		this.post_img_url = post_img_url;
		this.post_video_url = post_video_url;
		this.date1 = date1;
		this.username = username;
		this.user_url = user_url;
	}
	public Post_data() {
		System.out.println("Post data called");
	}
	
	
	
}