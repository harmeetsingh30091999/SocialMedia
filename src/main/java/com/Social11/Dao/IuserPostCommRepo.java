package com.Social11.Dao;

import java.util.Date;
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

import com.Social11.models.UserPostComment;

@Repository
public interface IuserPostCommRepo extends JpaRepository<UserPostComment,Integer>{
	
	@Query(value="select user_comment.comment_id,user_comment.comment_text,user_comment.date_1,user_comment.post_id,user_comment.user_id , user123.user_url, user123.username from user_comment INNER JOIN user123 on (user_comment.user_id = user123.id) where post_id = :postid", nativeQuery = true)
	public List<Map<String,Object>> findpostcomments(@Param("postid") int postid);
	
	@Query(value="select count(*) from User_comment where post_id =:postid", nativeQuery =true)
	public Integer totalcomment(@Param("postid") int postid);
	
	@Query(value="select user_comment.comment_id,user_comment.comment_text,user_comment.date_1,user_comment.post_id,user_comment.user_id , user123.user_url, user123.username from user_comment INNER JOIN user123 on (user_comment.user_id = user123.id) where post_id = :postid", 
	countQuery = "select count(*) from user_comment INNER JOIN user123 on (user_comment.user_id = user123.id) where post_id = :postid",
	nativeQuery = true)
	public Page<Map<String,Object>> findperpostcomments(@Param("postid") int postid,Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("Delete from UserPostComment u where u.commentid =:comment_id")
	public void deleteUserComment(@Param("comment_id") int comment_id);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE user_comment set comment_text =:comment_text, date_1 =:date where comment_id =:comment_id",nativeQuery = true)
	public void updatecomment(@Param("comment_id") int comment_id,@Param("comment_text") String comment_text, @Param("date") Date date);
	
	@Query(value ="select * from user_comment where user_id =:user_id and comment_id =:comment_id", nativeQuery = true)
	public UserPostComment isusercommented(@Param("comment_id") int comment_id, @Param("user_id") int user_id);
	
}
