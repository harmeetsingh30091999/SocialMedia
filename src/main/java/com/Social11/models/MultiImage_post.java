package com.Social11.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="multipleimage")
public class MultiImage_post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) 
	private int id;
	private int post_id;
	private String image_url;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPost_id() {
		return post_id;
	}
	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	@Override
	public String toString() {
		return "MultiImage_post [id=" + id + ", post_id=" + post_id + ", image_url=" + image_url + "]";
	}
	public MultiImage_post(int id, int post_id, String image_url) {
		super();
		this.id = id;
		this.post_id = post_id;
		this.image_url = image_url;
	}
	public MultiImage_post() {
		System.out.println("Multiple images called");
	}
	
	
}
