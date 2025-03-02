package com.Social11.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.Social11.models.Post_data;
import com.Social11.models.UserEntity;
import com.Social11.models.UserPostComment;
import com.Social11.models.UserPostLike;

@Service
public interface PostService {
	
//	@Autowired
//	private IuserPostRepos postrepos;

	public List<Post_data> findfriendspost(int id, int pagenumber);
	
	public List<Post_data> findrandompost(int pagenumber);
	
	public List<Post_data> findMypost(int id, int pagenumber);
	
	public List<Post_data> findfriendspostonly(int friend_id, int pagenumber);
	
	public boolean checkfriendornot(int id,int friendid);
	
	public int isfriendship_requested(int id, int friendid);
	
	public List<Map<String,Object>> findlikes(int post_id);
	
	public List<Map<String,Object>> findcomment(int post_id);
	
	public Integer totalComments(int post_id);
	
	public Integer totalLikes(int post_id);
	
	public List<Map<String,Object>> findperpostcomment(int post_id, int pagenumber);
	
	public UserEntity find_user_profile_details(int friend_id);
	
	public boolean isUserPost(int post_id, int user_id);
	
	public boolean isUserCommented(int commentid, int user_id);
	
	public boolean deletecomment(int comment_id);
	
	public void updatecomment(int comment_id, String comment_text);
	
	public boolean updatepost(int post_id, String post_title, String post_data, String image_path);
	
	public boolean updatePostWithoutImage(int post_id, String post_title, String post_data);
	
	public boolean deletepost(int post_id);
	
	public boolean deleteuserpost(int post_id);
	
	public List<Map<String,Object>> getallfriendrequest(int userid);
	
	public Integer getpostReports(int post_id);
	
	public Post_data findmyPost(int post_id);
	
	
}
