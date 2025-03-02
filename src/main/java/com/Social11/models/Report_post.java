package com.Social11.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Report_post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int postid;
	private int userid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPostid() {
		return postid;
	}
	public void setPostid(int postid) {
		this.postid = postid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	@Override
	public String toString() {
		return "Report_post [id=" + id + ", postid=" + postid + ", userid=" + userid + "]";
	}
	public Report_post() {
		System.out.println("Report called");
	}
	
	
}
