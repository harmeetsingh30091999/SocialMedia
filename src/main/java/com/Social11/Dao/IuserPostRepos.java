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

import com.Social11.models.Post_data;
import com.Social11.models.UserPost;

public interface IuserPostRepos extends JpaRepository<Post_data,Integer> {

	@Query("SELECT u from UserPost u WHERE u.user_id= :id")
	public List<Post_data> findByid(@Param("id") int user_id);
		
	@Query(value = "SELECT * from post_da1 inner join user_post on post_da1.post_id = user_post.post_id where user_post.user_id in (SELECT friend_id from user_fri_map where user_fri_map.user_id =:user_id) OR user_post.user_id =:user_id order by (post_da1.date1) desc",
	countQuery = "SELECT count(*) from post_da1 inner join user_post on post_da1.post_id = user_post.post_id where user_post.user_id in (SELECT friend_id from user_fri_map where user_fri_map.user_id =:user_id OR user_post.user_id =:user_id)",		
	nativeQuery = true)
	public Page<Post_data> fetchAllPost2(@Param("user_id") int user_id, Pageable pePageable);
	
	@Query(value="select * from user_post", nativeQuery = true)
	public List<UserPost> fetchpost();
	
	@Query(value = "SELECT * from post_da1 inner join user_post on post_da1.post_id = user_post.post_id where user_post.user_id not in (select id from user123 where isacprivate=true) ORDER BY RANDOM(),(post_da1.date1) desc",
	countQuery = "SELECT count(*) from post_da1 inner join user_post on post_da1.post_id = user_post.post_id where user_post.user_id not in (select id from user123 where isacprivate=true) ORDER BY RANDOM()",
	nativeQuery = true)
	public Page<Post_data> fetchRandomPost(Pageable pePageable);
	
	@Query(value = "select * from post_da1 where post_id =:postid",nativeQuery=true)  
	public Post_data fetchuserPostforHashtag(@Param("postid") int postid); 
	
//	@Query(value = "SELECT * from post_da1 inner join user_post on post_da1.post_id = user_post.post_id where user_post.user_id in (SELECT friend_id from user_fri_map where user_fri_map.user_id =:user_id OR user_post.user_id =:user_id)", nativeQuery = true)
//	public List<Post_data> fetchAllPost3(@Param("user_id") int user_id);
	
	@Query(value = "SELECT * from post_da1 inner join user_post on post_da1.post_id = user_post.post_id where user_post.user_id =:user_id order by (post_da1.date1) desc" ,
	countQuery ="SELECT count(*) from post_da1 inner join user_post on post_da1.post_id = user_post.post_id where user_post.user_id =:user_id",		
	nativeQuery = true)	
	public Page<Post_data> fetchMyPost(@Param("user_id") int user_id, Pageable pePageable);

	@Query(value = "SELECT count(*) from post_da1 inner join user_post on post_da1.post_id = user_post.post_id where user_post.user_id =:user_id" ,nativeQuery = true)
	public int totalpost(@Param("user_id") int user_id);
	
	@Query(value = "SELECT * from post_da1 inner join user_post on post_da1.post_id = user_post.post_id where user_post.user_id =:friend_id",
	countQuery ="SELECT count(*) from post_da1 inner join user_post on post_da1.post_id = user_post.post_id where user_post.user_id =:friend_id",nativeQuery = true)
	public Page<Post_data> findFriendspost(@Param("friend_id") int friend_id, Pageable pageable);
	
	@Query(value ="SELECT * from user_post where post_id =:post_id and user_id =:user_id", nativeQuery = true)
	public Map<String,Object> userpostexist(@Param("post_id") int post_id,@Param("user_id") int user_id);
	
	@Transactional
	@Modifying
	@Query(value ="UPDATE post_da1 SET date1 =:date , post_data =:post_data , post_img_url =:image_path , post_title =:post_title WHERE post_id =:postid",nativeQuery= true)
	public void userpostupdate(@Param("postid") int postid, @Param("date") Date date, @Param("post_title") String post_title, @Param("post_data") String post_data,@Param("image_path") String image_path);

	@Transactional
	@Modifying
	@Query(value = "UPDATE post_da1 SET date1 =:date , post_data =:post_data , post_title =:post_title WHERE post_id =:postid", nativeQuery = true)
    public int userpostupdateWithoutImage(@Param("postid") int postid, @Param("date") Date date, @Param("post_title") String post_title, @Param("post_data") String post_data);
	
	@Transactional
	@Modifying
	@Query("delete from UserPost u where u.post_id =:post_id")
	public void deletefromuserpost(@Param("post_id") int post_id);
	
	@Transactional
	@Modifying
	@Query("delete from Post_data u where u.id =:post_id")
	public void deletefrompostda1(@Param("post_id") int post_id);

}