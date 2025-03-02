package com.Social11.service;

import org.springframework.stereotype.Service;


@Service
public interface ReportService {
	
	public boolean isAlreadyReported(int userid, int postid); 
	
	public Integer totalReports(int postid);

	public boolean deleteallrecords(int postid);
	
}
