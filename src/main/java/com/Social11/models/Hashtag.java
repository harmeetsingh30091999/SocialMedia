package com.Social11.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hash_tag")
public class Hashtag {

	@Id
	@Column(name="hash_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String hashName;
	private Integer hashcreater;
	
	
	public Integer getHashcreater() {
		return hashcreater;
	}
	public void setHashcreater(Integer hashcreater) {
		this.hashcreater = hashcreater;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHashName() {
		return hashName;
	}
	public void setHashName(String hashName) {
		this.hashName = hashName;
	}
	public Hashtag(Integer id, String hashName, Integer hashcreater) {
		super();
		this.id = id;
		this.hashName = hashName;
		this.hashcreater = hashcreater;
	}
	@Override
	public String toString() {
		return "Hashtag [id=" + id + ", hashName=" + hashName + ", hashcreater=" + hashcreater + "]";
	}
	public Hashtag() {
		
	}
	
}