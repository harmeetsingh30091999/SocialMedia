package com.Social11.models;

import java.util.List;

public class HashtagProfile {

	private int hashtag_id;
	private String hashtag_name;
	private String hashtag_creator;
	private String creator_profile;
	private List<PostData1> Allpost;
	private Integer totalpost;
	public int getHashtag_id() {
		return hashtag_id;
	}
	public void setHashtag_id(int hashtag_id) {
		this.hashtag_id = hashtag_id;
	}
	public String getHashtag_name() {
		return hashtag_name;
	}
	public void setHashtag_name(String hashtag_name) {
		this.hashtag_name = hashtag_name;
	}
	public String getHashtag_creator() {
		return hashtag_creator;
	}
	public void setHashtag_creator(String hashtag_creator) {
		this.hashtag_creator = hashtag_creator;
	}
	public String getCreator_profile() {
		return creator_profile;
	}
	public void setCreator_profile(String creator_profile) {
		this.creator_profile = creator_profile;
	}
	public List<PostData1> getAllpost() {
		return Allpost;
	}
	public void setAllpost(List<PostData1> allpost) {
		Allpost = allpost;
	}
	@Override
	public String toString() {
		return "HashtagProfile [hashtag_id=" + hashtag_id + ", hashtag_name=" + hashtag_name + ", hashtag_creator="
				+ hashtag_creator + ", creator_profile=" + creator_profile + ", Allpost=" + Allpost + "]";
	}
	
	public Integer getTotalpost() {
		return totalpost;
	}
	public void setTotalpost(Integer totalpost) {
		this.totalpost = totalpost;
	}
	
	
	public HashtagProfile(int hashtag_id, String hashtag_name, String hashtag_creator, String creator_profile,
			List<PostData1> allpost, Integer totalpost) {
		super();
		this.hashtag_id = hashtag_id;
		this.hashtag_name = hashtag_name;
		this.hashtag_creator = hashtag_creator;
		this.creator_profile = creator_profile;
		Allpost = allpost;
		this.totalpost = totalpost;
	}
	public HashtagProfile() {
	
	}
	
	
	
}
