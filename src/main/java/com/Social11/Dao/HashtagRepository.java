package com.Social11.Dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Social11.models.Hashtag;

@Repository
public interface HashtagRepository  extends JpaRepository<Hashtag,Integer>{
	

	@Query(value="select hash_id from hash_tag where hash_name =:hashtag",nativeQuery=true)
	public Integer isHashtagPresent(String hashtag);
	
	@Query(value="select * from hash_tag where hash_name =:hashtag",nativeQuery=true)
	public Hashtag getHashdatafromhashtag(String hashtag);
	
	@Query(value="select p.hash_id, q.hash_name, q.hashcreater \r\n"
			+ "from (\r\n"
			+ "select b.hash_id, count(*) as count_of_post\r\n"
			+ "from hash_tag a, hashtag_post b \r\n"
			+ "where a.hash_id = b.hash_id and a.hash_name like %:search% \r\n"
			+ "group by b.hash_id \r\n"
			+ "order by count_of_post desc) p, hash_tag q\r\n"
			+ "where p.hash_id = q.hash_id",
			
	countQuery="select count(p.hash_id, q.hash_name, q.hashcreater) \r\n"
			+ "from (\r\n"
			+ "select b.hash_id, count(*) as count_of_post\r\n"
			+ "from hash_tag a, hashtag_post b \r\n"
			+ "a.hash_id = b.hash_id and a.hash_name like %:search% \r\n"
			+ "group by b.hash_id \r\n"
			+ "order by count_of_post desc) p, hash_tag q\r\n"
			+ "where p.hash_id = q.hash_id",	
			
	nativeQuery=true)
	public Page<Map<String,Object>> getAllTrendingHashtags(Pageable pageable, String search); 
	
	
}
