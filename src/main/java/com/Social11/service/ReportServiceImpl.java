package com.Social11.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.Social11.Dao.IuserPostRepos;
import com.Social11.Dao.IuserPostdata;
import com.Social11.Dao.MultiImageRepository;
import com.Social11.Dao.ReportRepository;
import com.Social11.models.MultiImage_post;
import com.Social11.models.Report_post;
@Service
public class ReportServiceImpl implements ReportService{

	@Autowired
	private ReportRepository Reportrepo;
	
	@Autowired
	private IuserPostdata postdata;
	
	@Autowired
	private IuserPostRepos post_da1;
	
	@Autowired
	private MultiImageRepository multiImage;
	
	@Autowired
	private FileServiceImpl fileservice;
	
	@Value("${project.image}")
	private String path;
	
	@Override
	public boolean isAlreadyReported(int userid, int postid) {
		Report_post reports = this.Reportrepo.getpost_reports(postid, userid);
		if(reports!=null) {
			return true;
		}
		else {
			Report_post newReport = new Report_post();
			newReport.setPostid(postid);
			newReport.setUserid(userid);
			Reportrepo.save(newReport);
			return false;
		}
	}

	@Override
	public Integer totalReports(int postid) {
		Integer totalReports = this.Reportrepo.totalReports(postid);
		return totalReports;
	}

	@Override
	public boolean deleteallrecords(int postid) {
		try {
			this.Reportrepo.deleteallRecordsinReport(postid);
			this.postdata.deleteuserpostrecords(postid);
			this.post_da1.deletefrompostda1(postid);
			List<MultiImage_post> multipleimages=this.multiImage.multiimages(postid);
			for(MultiImage_post m:multipleimages) {
				this.fileservice.DeleteImage(path,m.getImage_url());
			}
			this.multiImage.deltemultipleImages(postid);
		}
		catch(Exception e) {
			System.out.println(e);
			return false;
			
		}
		return true;
	}

}
