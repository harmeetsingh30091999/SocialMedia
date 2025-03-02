package com.Social11.Dao;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Social11.models.Post_data;
import com.Social11.models.Userfriend_request;


@Repository
public interface IUserFriendReqRepo  extends JpaRepository<Userfriend_request,Integer>{

	
	@Transactional
	@Modifying
	@Query("DELETE from Userfriend_request u WHERE u.friend_id =:friendid AND u.user_id =:id")
	public void deletefriendrequest(@Param("id") int user_id, @Param("friendid") int friend_id);
	
	@Query(value = "select username,user_url,firstname,lastname,id from user123 where id IN (select user_id from user_fri_req where friend_id =:userid)",
	countQuery = "select count(*) from user123 where id IN (select user_id from user_fri_req where friend_id =:userid)" ,nativeQuery=true)
	public Page<Map<String,Object>> totalrequest(@Param("userid")int userid, Pageable pageable);

//	here friend_id is the person who get a friend request from a user
	@Query(value = "select * from user_fri_req where (user_id =:id and friend_id =:friendid) OR (user_id =:friendid and friend_id =:id)",nativeQuery=true)
	public Userfriend_request isfriendship_requested(@Param("id")int id, @Param("friendid")int friendid);

	@Transactional
	@Modifying
	@Query(value = "DELETE from UserFriends u where u.friendid =:friendid and u.userid =:userid")
	public void unfriendexistingfriend(@Param("userid") int userid, @Param("friendid") int friendid);
	
	@Query(value = "select friend_id,user_id from user_fri_req where user_id =:userid or friend_id =:userid",nativeQuery=true)
	public List<Map<String,Object>> allfriendrequested(@Param("userid") int userid);
	
	@Transactional
	@Modifying	
	@Query(value = "DELETE from user_fri_req where friend_id =:friendid and user_id =:userid",nativeQuery=true)
	public void unsentfriendrequest(@Param("friendid") int friendid, @Param("userid") int userid);
	
}
