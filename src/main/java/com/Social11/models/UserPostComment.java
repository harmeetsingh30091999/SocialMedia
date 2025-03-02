package com.Social11.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lenovo
 *
 */
@Entity
@Table(name="User_comment")
public class UserPostComment {

	@Id
	@Column(name="comment_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int commentid;
	private String comment_text;
	@Column(name="post_id")
	private int post_id;
	@Column(name="user_id")
	private int user_id;
	@Column(name="username")
	private String username;
	@Column(name="userprofile")
	private String userprofile;
	@Column(name="date_1")
	private Date date1;
	public int getCommentid() {
		return commentid;
	}
	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}
	public String getComment_text() {
		return comment_text;
	}
	public void setComment_text(String comment_text) {
		this.comment_text = comment_text;
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
	public String getUserprofile() {
		return userprofile;
	}
	public void setUserprofile(String userprofile) {
		this.userprofile = userprofile;
	}
	@Override
	public String toString() {
		return "UserPostComment [commentid=" + commentid + ", comment_text=" + comment_text + ", post_id=" + post_id
				+ ", user_id=" + user_id + ", username=" + username + ", userprofile=" + userprofile + ", date1="
				+ date1 + "]";
	}
	public UserPostComment(int commentid, String comment_text, int post_id, int user_id, Date date1) {
		super();
		this.commentid = commentid;
		this.comment_text = comment_text;
		this.post_id = post_id;
		this.user_id = user_id;
		this.date1 = date1;
	}
	public UserPostComment() {
		System.out.print("User post comment class called" );
	}
	
	
	
}
