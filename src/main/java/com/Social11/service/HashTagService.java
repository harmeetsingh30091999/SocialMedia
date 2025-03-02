package com.Social11.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Social11.models.Hashtag;
import com.Social11.models.Hashtag1;
import com.Social11.models.HashtagProfile;
import com.Social11.models.PostData1;
import com.Social11.models.Post_data;

@Service
public interface HashTagService {

	public void createHashtagfrompost(List<String> hashtags ,int postid, int userid);
	
	
	public HashtagProfile fetch_hashtag_pagedetails(String hashtag, Integer page, boolean is_text);
	
	
	public List<PostData1> createPostData(List<Post_data> posts, boolean is_text);
	
	
	public void deleteHashtagPost(int post_id);
	
	
	public List<Hashtag1> searchHashtags(String hashname, int pagenumber);

	
	public List<PostData1> fetchAllposts(int pagenumber, boolean gridSystem);
	
	
	public List<PostData1> fetchSpecificPostwithotherhashtags(int postid, int pagenumber);
	
}
