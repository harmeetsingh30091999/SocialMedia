package com.Social11.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Social11.Dao.FriendRepository;
import com.Social11.models.FriendsList;
import com.Social11.models.Post_data;

@Service
public class FriendsListServiceImpl implements FriendsListService{

	@Autowired
	private FriendRepository friendrepo; 
	
	@Value("${profile.image}")
	private String image_path;
	
	@Override
	public List<FriendsList> FriendsList(int friendid, int userid, int page) {
		int pageSize = 3;
		Pageable p = PageRequest.of(page, pageSize);
        List<FriendsList> friendsList = new ArrayList<>();
		try {
			Set<Integer> set= new HashSet<>();
			Page<Map<String,Object>> mutualFriendspage = this.friendrepo.mutualfriendsList(friendid, userid,p);
			Page<Map<String,Object>> unknownListpage = this.friendrepo.friendsList(friendid, userid,p);
			Page<Map<String,Object>> friendshipRequestedpage = this.friendrepo.friendRequested(userid,p);
			List<Map<String,Object>> mutualFriends = new ArrayList<>();
			List<Map<String,Object>> unknownList = new ArrayList<>();
			List<Map<String,Object>> friendshipRequested = new ArrayList<>();
			if(mutualFriendspage!=null) {
				mutualFriends = mutualFriendspage.getContent();
			}
			else {
				new ArrayList<>();
			}
			if(unknownListpage!=null) {
			    unknownList = unknownListpage.getContent();
			}
			else {
				new ArrayList<>();
			}
			if(friendshipRequestedpage!=null) {
			   friendshipRequested = friendshipRequestedpage.getContent();
			}
			else {
				new ArrayList<>();
			}
						
			if(!mutualFriends.isEmpty()) {
				for(Map<String,Object> mp:mutualFriends) {
					FriendsList friends = new FriendsList();				
					friends.setId((Integer)mp.get("friend_id"));	
					friends.setUsername((String)mp.get("username"));
					friends.setUserUrl(image_path+(String)mp.get("user_url"));
					friends.setFirstname((String)mp.get("firstname"));
					friends.setLastname((String)mp.get("lastname"));
					friends.setIsmututalFriend(true);
					friendsList.add(friends);
				}
			}
			
			for(Map<String,Object> request:friendshipRequested) {
				set.add((Integer)request.get("friend_id"));
			}
			
			if(!unknownList.isEmpty()) {
				for(Map<String,Object> mp:unknownList) {
					FriendsList friends = new FriendsList();				
					if(set.contains((Integer)mp.get("friend_id"))) {
						friends.setIsrequested(true);	
					}
					friends.setId((Integer)mp.get("friend_id"));	
					friends.setUsername((String)mp.get("username"));
					friends.setUserUrl(image_path+(String)mp.get("user_url"));
					friends.setFirstname((String)mp.get("firstname"));
					friends.setLastname((String)mp.get("lastname"));
					friendsList.add(friends);
				}
			}
			return friendsList;
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return new ArrayList<>();
	}

	@Override
	public List<FriendsList> MyFriendsList(int userid, int pagenumber) {
		int pageSize = 3;
		Pageable p = PageRequest.of(pagenumber, pageSize);
        List<FriendsList> friendsList = new ArrayList<>();
		try {
			Page<Map<String,Object>> Allposts = this.friendrepo.myfriendsList(userid,p);
			if(Allposts!=null) {
			List<Map<String,Object>> friends1=Allposts.getContent();
			for(Map<String,Object> mp:friends1) {
				FriendsList friends = new FriendsList();
				friends.setId((Integer)mp.get("friend_id"));	
				friends.setUsername((String)mp.get("username"));
				friends.setUserUrl(image_path+(String)mp.get("user_url"));
				friends.setFirstname((String)mp.get("firstname"));
				friends.setLastname((String)mp.get("lastname"));
				friendsList.add(friends);
			}
			return friendsList;
			}
			else {
				return new ArrayList<>();
			}
		}
		catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}

}
