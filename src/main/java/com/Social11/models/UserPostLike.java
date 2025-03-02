package com.Social11.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="User_post_like1")
public class UserPostLike {
	@Id
	@Column(name="like_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int likeid;
	@Column(name="post_id")
	private int post_id;
	@Column(name="user_id")
	private int user_id;
	@Column(name="report")
	private int report;
	@Column(name="date_1")
	private Date date1;
	@Column(name="username")
	private String username;
	@Column(name="user_url")
	private String user_url;
	
	public int getLikeid() {
		return likeid;
	}
	public void setLikeid(int likeid) {
		this.likeid = likeid;
	}
	public int getPost_id() {
		return post_id;
	}
	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getReport() {
		return report;
	}
	public void setReport(int report) {
		this.report = report;
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
		return "UserPostLike [likeid=" + likeid + ", post_id=" + post_id + ", user_id=" + user_id + ", report=" + report
				+ ", date1=" + date1 + ", username=" + username + ", user_url=" + user_url + "]";
	}
	public UserPostLike(int likeid, int post_id, int user_id, int report, Date date1) {
		super();
		this.likeid = likeid;
		this.post_id = post_id;
		this.user_id = user_id;
		this.report = report;
		this.date1 = date1;
	}
	public UserPostLike() {
		System.out.println("User post like called");
	}
	
	
}