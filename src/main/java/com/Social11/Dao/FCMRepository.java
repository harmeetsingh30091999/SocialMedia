package com.Social11.Dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Social11.models.FCMModel;

@Repository
public interface FCMRepository extends JpaRepository<FCMModel,Integer>{

	@Query(value="select fcm_token from FCMModel where userid =:userid", nativeQuery=true)
	public String isFCMPresent(Integer userid);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE FCMMOdel SET fcm_token =:FCM WHERE userid =:userid", nativeQuery=true)
	public void updateFCMToken(String FCM, Integer userid);
	
	
}
