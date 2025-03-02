package com.Social11.Dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Social11.models.UserPost;


public interface IuserPostdata extends JpaRepository<UserPost,Integer>{
	
	@Query(value = "select user_id from user_post INNER JOIN post_da1 on user_post.post_id = post_da1.post_id where user_post.post_id =:postid",nativeQuery=true)
	public Integer finduseridbypostid(Integer postid);

//	@Query(value= "select post_report from user_post where post_id =:post_id",nativeQuery=true)
//	public Integer gettotalReports(int post_id);
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM user_post WHERE post_id =:postid",nativeQuery=true)
	public void deleteuserpostrecords(@Param("postid") int postid);
	
}
