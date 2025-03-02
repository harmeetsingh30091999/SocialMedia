package com.Social11.Dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Social11.models.Report_post;

@Repository
public interface ReportRepository extends JpaRepository<Report_post,Integer>{

	@Query(value="select * from Report_post where postid =:postid and userid =:userid",nativeQuery=true)
	public Report_post getpost_reports(@Param("postid") int postid, @Param("userid") int userid);
	
	@Query(value="select count(*) from Report_post where postid =:postid",nativeQuery=true)
	public Integer totalReports(@Param("postid") int postid);
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM Report_post where postid =:postid",nativeQuery=true)
	public void deleteallRecordsinReport(@Param("postid") int postid);
	
}
