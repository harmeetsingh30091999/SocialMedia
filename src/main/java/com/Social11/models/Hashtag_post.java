package com.Social11.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hashtag_post")
public class Hashtag_post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer hashpostid;
	private Integer postid;
	private Integer hashId;
	
	
	public Integer getHashpostid() {
		return hashpostid;
	}
	public void setHashpostid(Integer hashpostid) {
		this.hashpostid = hashpostid;
	}
	public Integer getPostid() {
		return postid;
	}
	public void setPostid(Integer postid) {
		this.postid = postid;
	}
	public Integer getHashId() {
		return hashId;
	}
	public void setHashId(Integer hashId) {
		this.hashId = hashId;
	}
	
	@Override
	public String toString() {
		return "Hashtag_post [hashpostid=" + hashpostid + ", postid=" + postid + ", hashId=" + hashId + "]";
	}
	public Hashtag_post(Integer hashpostid, Integer postid, Integer hashId) {
		super();
		this.hashpostid = hashpostid;
		this.postid = postid;
		this.hashId = hashId;
	}
	public Hashtag_post() {
		System.out.println("HashTag post ");
		
	}
	
	
	
}
