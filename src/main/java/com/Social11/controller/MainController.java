package com.Social11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainController {

	
	@GetMapping("admin")
	public String adminpage() {
		return "This page has restriced and can only be accessible by admin";
	}	

	@GetMapping("/policies")
	public String policies() {
		return "index.html";
	}
	
	
	
}



//bearerToken = bearerToken.substring(7);
//Map<String,Object> claims = this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
//int id=(int)claims.get("Id");
//System.out.println(id+" id and "+postid+" postid");
//try {
//	if(this.postService.isUserPost(postid, id)) {
//		String image_path = null;
//		if(file!=null) {
//			String filename="";
//			String name = file.getOriginalFilename();
//			String randomId = UUID.randomUUID().toString();
//			filename = randomId.concat(name.substring(name.lastIndexOf(".")));
//			this.fileservice.uploadimage(path,filename,file);
//			image_path=filename;
//		}
//		this.postService.updatepost(postid,Post_title,post_data,image_path);
//		Response response = new Response();
//		response.setIs_success(true);
//		return response;
//	}
//	else {
//		Response response = new Response();
//		response.setIs_success(false);
//		response.setError("user post not updated");
//		return response;
//	}
//}
//catch(Exception e) {
//	System.out.println("Something went wrong");
//	return null;
//}
