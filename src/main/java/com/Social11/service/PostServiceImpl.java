package com.Social11.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Social11.Dao.FriendRepository;
import com.Social11.Dao.IUserFriendReqRepo;
import com.Social11.Dao.IuserPostCommRepo;
import com.Social11.Dao.IuserPostLikeRepo;
import com.Social11.Dao.IuserPostRepos;
import com.Social11.Dao.IuserRepository;
import com.Social11.models.Post_data;
import com.Social11.models.Post_datadto;
import com.Social11.models.UserEntity;
import com.Social11.models.UserFriends;
import com.Social11.models.UserPost;
import com.Social11.models.UserPostComment;
import com.Social11.models.UserPostLike;
import com.Social11.models.Userfriend_request;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private IuserPostRepos postrepo;
	
	@Autowired
	private FriendRepository repository;
	
	@Autowired
	private IuserPostLikeRepo likerepo;
	
	@Autowired
	private IuserPostCommRepo commentrepo;
	
	@Autowired
	private IuserRepository user_profile;
	
	@Autowired
	private IUserFriendReqRepo friend_request_repo;
	
	@Override
	public List<Post_data> findfriendspost(int id, int pagenumber) {
		int pageSize = 10;
		Pageable p = PageRequest.of(pagenumber, pageSize);
		Page<Post_data> posts=postrepo.fetchAllPost2(id,p);
		if(posts != null) {
			List<Post_data> Allposts=posts.getContent();
			return Allposts;
		} else {
			new ArrayList<Post_data>();
		}
		return null;
	}
	
	@Override
	public List<Post_data> findrandompost(int pagenumber) {
		int pageSize = 10;
		Pageable p = PageRequest.of(pagenumber, pageSize);
		Page<Post_data> posts=postrepo.fetchRandomPost(p);
		if(posts != null) {
			List<Post_data> Allposts=posts.getContent();
			return Allposts;
		}
		else {
		 	new ArrayList<Post_data>();
		}
		return null;
	}

	@Override
	public List<Post_data> findMypost(int id, int pagenumber) {
		int pageSize = 10;
		Pageable p = PageRequest.of(pagenumber, pageSize);
		Page<Post_data> posts = postrepo.fetchMyPost(id,p);
		if(posts !=null) {
			List<Post_data> allposts=posts.getContent();
			return allposts;
		}
		else {
			return new ArrayList<Post_data>();
		}
	}

	@Override
	public List<Post_data> findfriendspostonly(int friend_id, int pagenumber) {
		int pageSize = 10;
		Pageable p = PageRequest.of(pagenumber, pageSize);
		Page<Post_data> posts = postrepo.findFriendspost(friend_id,p);
		if(posts !=null) {
			List<Post_data> allposts=posts.getContent();
			return allposts;
		}
		else {
			return new ArrayList<Post_data>();
		}
	}

	@Override
	public boolean checkfriendornot(int id, int friendid) {
		UserFriends friends = this.repository.fetchdata(id, friendid);
		if(friends!=null) {
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String,Object>> findlikes(int post_id) {
		List<Map<String,Object>> likes=this.likerepo.findpostlike(post_id);
		if(likes!=null) {
			return likes;
		}
		else {
			return null;
		}
	}

	@Override
	public List<Map<String,Object>> findcomment(int post_id) {
		List<Map<String,Object>> comments=this.commentrepo.findpostcomments(post_id);
		if(comments!=null) {
			return comments;
		}
		else {
			return null;
		}	
	}
	
	

	@Override
	public Integer totalComments(int post_id) {
		Integer comments=this.commentrepo.totalcomment(post_id);
		if(comments!=null)
			return comments;
		else {
			return new Integer(0);
		}
	}

	@Override
	public Integer totalLikes(int post_id) {
		Integer likes = this.likerepo.totallikes(post_id);
		if(likes!=null)
			return likes;
		else {
			return new Integer(0);
		}
	}

	@Override
	public List<Map<String, Object>> findperpostcomment(int post_id, int pagenumber) {
		int pageSize = 10;
		Pageable p = PageRequest.of(pagenumber, pageSize);
		Page<Map<String, Object>> findcomment = this.commentrepo.findperpostcomments(post_id,p);
		if(findcomment !=null) {
			List<Map<String,Object>> findallcommentsperpost=findcomment.getContent();
			return findallcommentsperpost;
		}
		else {
			return new ArrayList();
		}
	}

	@Override
	public UserEntity find_user_profile_details(int friend_id) {
		UserEntity user_profile = this.user_profile.findbyfriend_id(friend_id);
		if(user_profile!=null)
			return user_profile;
		else
			return new UserEntity();
	}

	@Override
	public int isfriendship_requested(int id, int friendid) {
		Userfriend_request request = this.friend_request_repo.isfriendship_requested(id, friendid);
			
		if(request!=null)
			if(id==request.getUser_id())
			{
//				sended a request
				return 1;
			}
			else
			{
//				received a request or denied;
				return 2;
			}
		else {
			return 0;
		}
	}

	@Override
	public boolean isUserPost(int post_id, int user_id) {
		Map<String,Object> userposts=this.postrepo.userpostexist(post_id,user_id);
		if(!userposts.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean updatepost(int post_id, String post_title, String post_data, String image_path) {
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		try {
			this.postrepo.userpostupdate(post_id, dtf.parse(dtf.format(now)), post_title, post_data, image_path);
			return true;
		}
		catch(Exception e) {
			System.out.println("update not succesfull");
			return false;
		}
	}
	
	@Override
	public boolean updatePostWithoutImage(int post_id, String post_title, String post_data) {
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		try {
			this.postrepo.userpostupdateWithoutImage(post_id, dtf.parse(dtf.format(now)), post_title, post_data);
			return true;
		}
		catch(Exception e) {
			System.out.println("update not succesfull");
			return false;
		}
	}
	

	@Override
	public boolean deletepost(int post_id) {
		try {
			this.postrepo.deletefromuserpost(post_id);
			return true;
		}
		catch(Exception e) {
			System.out.println("Something went wrong");
			return false;	
		}
	}

	@Override
	public boolean deleteuserpost(int post_id) {
		try {
			this.postrepo.deletefrompostda1(post_id);
			return true;
		}
		catch(Exception e) {
			System.out.println("Something went wrong");
			return false;	
		}	
	}

	@Override
	public boolean isUserCommented(int commentid, int user_id) {
		try {
			UserPostComment user_comment = this.commentrepo.isusercommented(commentid, user_id);
			if(user_comment!=null) {
				return true;
			}
			else return false;
		}
		catch(Exception e) {
			System.out.println("Something went wrong "+e);
			return false;
		}
	}

	@Override
	public boolean deletecomment(int comment_id) {
		try {
			this.commentrepo.deleteUserComment(comment_id);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	@Override
	public void updatecomment(int comment_id, String comment_text) {
		try {
			SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			this.commentrepo.updatecomment(comment_id, comment_text, dtf.parse(dtf.format(now)));
		}
		catch(Exception e) {
			System.out.println("Something went wrong "+e);
		}
	}

	@Override
	public List<Map<String, Object>> getallfriendrequest(int userid) {
		try {
			return this.friend_request_repo.allfriendrequested(userid);
		}
		catch(Exception e) {
			System.out.println("Exception:"+e);
			return null;
		}
	}

	@Override
	public Integer getpostReports(int post_id) {
		try {
			
		}
		catch(Exception e) {
			
		}
		return null;
	}

	@Override
	public Post_data findmyPost(int post_id) {
		try {
			Post_data post = this.postrepo.fetchuserPostforHashtag(post_id);
			return post;
		}
		catch(Exception e) {
			return new Post_data();
		}
	}
	
}