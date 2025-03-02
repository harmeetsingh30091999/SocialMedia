package com.Social11.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.Social11.models.FriendsList;
@Service
public interface FriendsListService {

	
	public List<FriendsList> FriendsList(int friendid,int userid, int pagenumber);
	
	public List<FriendsList> MyFriendsList(int userid, int pagenumber);
	
}
