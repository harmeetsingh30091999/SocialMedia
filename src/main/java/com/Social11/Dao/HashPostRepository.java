package com.Social11.Dao;


import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Social11.models.Hashtag_post;

@Repository
public interface HashPostRepository extends JpaRepository<Hashtag_post,Integer>{

	@Query(value="select DISTINCT postid from hashtag_post where hash_id =:hashid",
			nativeQuery=true)
	public Page<Integer> getAllPostWRTHashtag(Pageable pageable,Integer hashid);
	
	@Transactional
	@Modifying
	@Query(value ="delete from hashtag_post where postid =:postid", nativeQuery=true)
	public void deletehashtags(Integer postid);
	
	@Query(value= "select count(*) from hashtag_post where hash_id =:hashid", nativeQuery=true)
	public Integer TotalPostHashtag(Integer hashid);
	
	@Query(value="select distinct(postid) from hashtag_post", nativeQuery=true)
	public Page<Integer> fetchAllPost(Pageable pageable);
	
	@Query(value="SELECT postid\r\n"
			+ "FROM hashtag_post\r\n"
			+ "where postid in (select distinct(postid) from hashtag_post)\r\n"
			+ "group by postid \r\n"
			+ "ORDER BY\r\n"
			+ "  CASE \r\n"
			+ "    WHEN postid =:postid THEN 0\r\n"
			+ "    ELSE 1\r\n"
			+ "  END;", nativeQuery=true)
	public Page<Integer> fetchAllPostwithSpecificPost(Pageable pageable, Integer postid);
	
//	SELECT postid
//	FROM hashtag_post
//	where postid in (select distinct(postid) from hashtag_post)
//	ORDER BY
//	  CASE 
//	    WHEN postid = 2265 THEN 0
//	    ELSE 1
//	  END;
	
}