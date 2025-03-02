package com.Social11.Dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Social11.models.MultiImage_post;

@Repository
public interface MultiImageRepository extends JpaRepository<MultiImage_post,Integer> {

	@Query(value = "select * from multipleimage where post_id =:post_id",nativeQuery=true)
	public List<MultiImage_post> multiimages(int post_id);
	
	@Transactional
	@Modifying
	@Query(value = "delete from multipleimage where post_id =:post_id",nativeQuery=true)
	public void deltemultipleImages(int post_id);
	
	
}
