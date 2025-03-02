package com.Social11.Dao;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Social11.models.UserPostLike;

public interface IuserPostLikeRepo extends JpaRepository<UserPostLike,Integer>{

	@Query(value="select * from user_post_like1 where POST_ID =:postid AND USER_ID =:userid", nativeQuery = true)
	public UserPostLike isUserLikePost(@Param("postid") int postid , @Param("userid") int userid);
	
	@Transactional
	@Modifying
	@Query(value="update user_post_like1 u set report =:report where u.like_id =:likeid", nativeQuery = true)
	public int updateRecordfromtable(@Param("likeid") int likeid, @Param("report") int report);
	
//	@Query(value="select user_post_like1.post_id, user_post_like1.user_id, user_post_like1.report from user_post_like1 INNER JOIN user123 on (user_post_like1.user_id=user123.id) where user_post_like1.post_id =:postid", nativeQuery = true)
//	public List<UserPostLike> findpostlike(@Param("postid") int postid);

	@Query(value="select user_post_like1.like_id,user_post_like1.date_1,user_post_like1.post_id,user_post_like1.user_id,user_post_like1.report, user123.username, user123.user_url from user_post_like1 INNER JOIN user123 on (user_post_like1.user_id=user123.id) where user_post_like1.post_id =:postid", nativeQuery = true)
	public List<Map<String,Object>> findpostlike(@Param("postid") int postid);
	
	@Query(value="select count(*) from User_post_like1 where post_id =:postid", nativeQuery =true)
	public Integer totallikes(@Param("postid") int postid);
	
	@Query(value="select * from User_post_like1 where post_id =:postid AND user_id =:userid",nativeQuery=true)
	public UserPostLike mylike(@Param("postid") int postid,@Param("userid") int userid);
	
	
}
