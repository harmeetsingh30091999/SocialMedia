package com.Social11.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Social11.models.JwtBlackList;

@Repository
public interface JwtBlackListRepository extends JpaRepository<JwtBlackList,Integer>{

	JwtBlackList findByTokenEquals(String token);
	
	@Query(value="insert into black_list (id,token) values(:id,:token)",nativeQuery=true)
	public String blacklisttoken(@Param("id")int id, @Param("token") String token);
	
}
