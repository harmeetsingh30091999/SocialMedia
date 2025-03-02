package com.Social11.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class PostData1{

	@Id
	private int post_id;
	private int user_id;
	private String user_profile_url;
	private String username;
	private String firstname;
	private String lastname;
	private String post_title;
	private String post_data;
	private String[] post_img_url;
	private String post_video_url;
	private Date date1;
	private int totalikes;
	private int totalcomments;
	@OneToMany
	private List<UserPostLike> likes;
	@OneToMany
	private List<UserPostComment> comment;
	public int getPost_id() {
		return post_id;
	}
	public void setPost_id(int post_id) {
		this.post_id = post_id;
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
	public int getTotalikes() {
		return totalikes;
	}
	public void setTotalikes(int totalikes) {
		this.totalikes = totalikes;
	}
	public int getTotalcomments() {
		return totalcomments;
	}
	public void setTotalcomments(int totalcomments) {
		this.totalcomments = totalcomments;
	}
	public List<UserPostLike> getLikes() {
		return likes;
	}
	public void setLikes(List<UserPostLike> likes) {
		this.likes = likes;
	}
	public List<UserPostComment> getComment() {
		return comment;
	}
	public void setComment(List<UserPostComment> comment) {
		this.comment = comment;
	}
	
	public String getUser_profile_url() {
		return user_profile_url;
	}
	public void setUser_profile_url(String user_profile_url) {
		this.user_profile_url = user_profile_url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "PostData1 [post_id=" + post_id + ", user_profile_url=" + user_profile_url + ", username=" + username
				+ ", post_title=" + post_title + ", post_data=" + post_data + ", post_img_url=" + post_img_url
				+ ", post_video_url=" + post_video_url + ", date1=" + date1 + ", totalikes=" + totalikes
				+ ", totalcomments=" + totalcomments + ", likes=" + likes + ", comment=" + comment + "]";
	}
	
	public PostData1(int post_id, int user_id, String user_profile_url, String username, String post_title,
			String post_data, String[] post_img_url, String post_video_url, Date date1, int totalikes,
			int totalcomments, List<UserPostLike> likes, List<UserPostComment> comment) {
		super();
		this.post_id = post_id;
		this.user_id = user_id;
		this.user_profile_url = user_profile_url;
		this.username = username;
		this.post_title = post_title;
		this.post_data = post_data;
		this.post_img_url = post_img_url;
		this.post_video_url = post_video_url;
		this.date1 = date1;
		this.totalikes = totalikes;
		this.totalcomments = totalcomments;
		this.likes = likes;
		this.comment = comment;
	}
	
	public PostData1() {
		System.out.println("post called");
	}
	
	
}