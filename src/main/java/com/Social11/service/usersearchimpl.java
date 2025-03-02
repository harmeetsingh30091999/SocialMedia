package com.Social11.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Social11.Dao.IuserRepository;
import com.Social11.models.UserEntity;

@Service
public class usersearchimpl implements IuserSearch {

	@Autowired
	private IuserRepository repository;
	
	@Value("${profile.image}")
	private String image_path;
	
	@Override
	public List<UserEntity> searchbyusername(String username, int pagenumber) {
		int pageSize = 10;
		Pageable p = PageRequest.of(pagenumber, pageSize);
		Page<UserEntity> posts = repository.searchbyusername(username, p);
		if(posts !=null) {
			List<UserEntity> allposts=posts.getContent();
			for(int i=0;i<allposts.size();i++) {
				if(allposts.get(i).getUserUrl()!=null)
					allposts.get(i).setUserUrl(image_path+allposts.get(i).getUserUrl());
				
				allposts.get(i).setPassword("N/A");
			}
			return allposts;
		}
		else {
			return new ArrayList<UserEntity>();
		}
	}

}
