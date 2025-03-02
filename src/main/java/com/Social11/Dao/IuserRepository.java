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

import com.Social11.models.UserEntity;

public interface IuserRepository extends JpaRepository<UserEntity,Integer>{

	public UserEntity findByusername(String username);
	
//	@Query(value = "SELECT * FROM user123 u WHERE u.email_address :harmeetsinghghai567@gmail.com", nativeQuery = true)
	
//	@Query("SELECT u from UserEntity u")
//	public List<UserEntity> findByemail_address();
	
	
	@Query("SELECT u from UserEntity u WHERE u.email_address= :m")
	public UserEntity findByemail(@Param("m") String email);
	
	@Query(value = "SELECT username from user123 where username =:email or email_address =:email",nativeQuery=true)
	public String findByemail1(@Param("email") String email);
	
	@Query(value = "SELECT user_url from user123 where id =:userid",nativeQuery=true)
	public String findbyimage(int userid);
	
	@Query(value = "SELECT Bio from user123 where id =:userid",nativeQuery=true)
	public String findbio(int userid);
	
	@Query(value = "select * from user123 where username like %:username% OR firstname like %:username"
			+ "%",
	countQuery = "select * from user123 where username like %:username% OR firstname like %:username%" ,nativeQuery=true)
	public Page<UserEntity> searchbyusername(String username, Pageable pageable);
	
	@Query(value= "select * from user123 where id =:friend_id",nativeQuery=true)
	public UserEntity findbyfriend_id(@Param("friend_id") int id);
	
	@Query(value= "select * from user123 where id NOT IN (select friend_id from user_fri_map where user_id =:userid) and id != :userid ORDER BY RANDOM() LIMIT 3",nativeQuery=true)
	public List<UserEntity> friendSuggestion(@Param("userid") int userid);

	@Transactional
	@Modifying
	@Query(value="update user123 set isacprivate =:accountmode where id =:userid",nativeQuery=true)
	public void accountswitch(@Param("userid") int userid,@Param("accountmode") boolean accountmode);
	
	@Query(value="select isacprivate from user123 where id =:friendid", nativeQuery=true)
	public boolean isprivateaccount(@Param("friendid") int friendid);
	
	@Query(value="select * from user123 where email_address =:email",nativeQuery=true)
	public UserEntity findEmail(@Param("email") String email);
	
	@Query(value="select * from user123 where username =:username",nativeQuery=true)
	public UserEntity findUsername(@Param("username") String username);
	
	@Query(value="select firstname,lastname,user_url from user123 where id =:userid", nativeQuery=true)
	public Map<String,Object> getNameAndProfile(@Param("userid") int userid);
	
}
