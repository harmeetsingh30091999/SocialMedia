package com.Social11.Dao;


import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Social11.models.UserFriends;

@Repository
public interface FriendRepository extends JpaRepository<UserFriends,Integer>{
//	@Transactional
//	@Modifying
//	@Query("UPDATE UserFriends u SET u.request_report=:k WHERE u.from=:m and u.to=:n")
//	public void updateFriendRequest(@Param("m") int from, @Param("n") int to, @Param("k") int report)
	
	@Query(value="select * from user_fri_map where user_id =:userid and friend_id =:friendid", nativeQuery = true)
	public UserFriends fetchdata(@Param("userid") int userid,@Param("friendid") int friendid);
	
	@Query(value="select count(*) from user_fri_map where user_id =:userid", nativeQuery = true)
	public Integer totalfriends(@Param("userid") int userid);
	
	@Query(value="select friend_id,username,user_url,firstname,lastname,bio from user123 as u inner join user_fri_map as v on u.id = v.friend_id where v.user_id =:friend_id and (v.friend_id not in (select friend_id from user123 as u inner join user_fri_map as v on u.id = v.user_id where v.user_id =:userid)) and (v.friend_id !=:userid)",
	countQuery="select friend_id,username,user_url,firstname,lastname,bio from user123 as u inner join user_fri_map as v on u.id = v.friend_id where v.user_id =:friend_id and (v.friend_id not in (select friend_id from user123 as u inner join user_fri_map as v on u.id = v.user_id where v.user_id =:userid)) and (v.friend_id !=:userid)",
	nativeQuery = true)
	Page<Map<String,Object>> friendsList(@Param("friend_id") int friend_id, @Param("userid") int userid,Pageable pePageable);

	@Query(value="select friend_id,username,user_url,firstname,lastname,bio from user123 as u inner join user_fri_map as v on u.id = v.friend_id where v.user_id =:userid and v.friend_id in (select friend_id from user123 as u inner join user_fri_map as v on u.id = v.user_id where v.user_id =:friend_id and v.friend_id !=:userid)", 
	countQuery="select friend_id,username,user_url,firstname,lastname,bio from user123 as u inner join user_fri_map as v on u.id = v.friend_id where v.user_id =:userid and v.friend_id in (select friend_id from user123 as u inner join user_fri_map as v on u.id = v.user_id where v.user_id =:friend_id and v.friend_id !=:userid",		
	nativeQuery = true)
	Page<Map<String,Object>> mutualfriendsList(@Param("friend_id") int friend_id, @Param("userid") int userid,Pageable pePageable);

	@Query(value="select friend_id from user123 as u inner join user_fri_req as v on u.id = v.friend_id where v.user_id=:userid",
	countQuery="select friend_id from user123 as u inner join user_fri_req as v on u.id = v.friend_id where v.user_id=:userid",
	nativeQuery = true)
	Page<Map<String,Object>> friendRequested(@Param("userid") int userid,Pageable pePageable);

	@Query(value="select friend_id,username,user_url,firstname,lastname,bio from user123 as u inner join user_fri_map as v on u.id = v.friend_id where v.user_id =:user_id",
	countQuery="select friend_id,username,user_url,firstname,lastname,bio from user123 as u inner join user_fri_map as v on u.id = v.friend_id where v.user_id =:user_id", 
	nativeQuery=true)
	Page<Map<String,Object>> myfriendsList(@Param("user_id") int userid,Pageable pePageable);
	
}