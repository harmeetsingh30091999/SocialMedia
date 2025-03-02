package com.Social11.controller;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Social11.Dao.FriendRepository;
import com.Social11.Dao.IUserFriendReqRepo;
import com.Social11.Dao.IuserPostCommRepo;
import com.Social11.Dao.IuserPostLikeRepo;
import com.Social11.Dao.IuserPostRepos;
import com.Social11.Dao.IuserPostdata;
import com.Social11.Dao.IuserRepository;
import com.Social11.Dao.MultiImageRepository;
import com.Social11.helper.JwtTokenUtil;
import com.Social11.models.Friend_request;
import com.Social11.models.FriendsList;
import com.Social11.models.Hashtag;
import com.Social11.models.Hashtag1;
import com.Social11.models.HashtagProfile;
import com.Social11.models.MultiImage_post;
import com.Social11.models.PostData1;
import com.Social11.models.Post_data;
import com.Social11.models.Response;
import com.Social11.models.SuggestionandPost;
import com.Social11.models.UserEntity;
import com.Social11.models.UserFriends;
import com.Social11.models.UserPost;
import com.Social11.models.UserPostComment;
import com.Social11.models.UserPostLike;
import com.Social11.models.UserProfile;
import com.Social11.models.Userfriend_request;
import com.Social11.models.UsserEntity1;
import com.Social11.service.FCMTokenService;
import com.Social11.service.FileService;
import com.Social11.service.FileServiceImpl;
import com.Social11.service.FriendsListService;
import com.Social11.service.HashTagService;
import com.Social11.service.IuserSearch;
import com.Social11.service.PostService;
import com.Social11.service.ReportService;
import com.Social11.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private IuserPostRepos userpost;
	
	@Autowired
	private IuserPostdata userpostrelation;

	@Autowired
	private MultiImageRepository multiImage;
	
	@Autowired
	private IuserRepository userdata;
	
	@Autowired
	private FriendRepository friendaccept;
	
	@Autowired
	private IUserFriendReqRepo userfriendrequest;
	
	@Autowired
	private IuserPostLikeRepo postlike;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private IuserRepository repository;
	
	@Autowired
	private ReportService reportservice;
	
	@Autowired
	private FileService fileservice;
	
	@Autowired
	private FileServiceImpl fileserv;
	
	@Autowired
	private IuserPostCommRepo commentRepo;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private IuserSearch iusersearch;
	
	@Autowired
	private FriendsListService friendlist;
		
	@Autowired
	private HashTagService hashtagService;
	
	@Autowired
	private FCMTokenService fcmService;
	
	@Value("${project.image}")
	private String path;
	
	@Value("${profile.image}")
	private String image_path;
	
	
	@GetMapping("/user")
    public UserProfile getProfileInfo(@RequestHeader("Authorization") String bearerToken, @RequestParam(value = "user_id", required = false) Integer friend_id , @RequestParam(name = "is_text",required=false) boolean is_text ,@RequestParam(name = "page") Integer page) throws ParseException {

        if (page != 0) {
            page--;
        }
        
        bearerToken = bearerToken.substring(7);
        Map<String, Object> claims = this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
        String username = (String) claims.get("Username");
        String email = (String) claims.get("Email");
        int id = (int) claims.get("Id");
        boolean isaccountprivate = false;
        boolean isfriendrequestsent = false;

        if (friend_id != null && friend_id != id) {
//        	System.out.println("Friend id is called");
            boolean isfriend = false;
            UserProfile profile = new UserProfile();
            try {
                isfriend = this.postService.checkfriendornot(id, friend_id);
                isaccountprivate = this.userservice.isaccountprivate(friend_id);
            	profile.setIs_private(isaccountprivate);
                UserEntity entity = this.postService.find_user_profile_details(friend_id);
//                System.out.println(entity);
                if (entity != null) {
                	profile.setFirstname(entity.getFirstname());
                	profile.setLastname(entity.getLastname());
                    profile.setProfile_id(entity.getId());
                    profile.setBio(entity.getBio());
                    profile.setUsername(entity.getUsername());
                    profile.setTotalFriends(friendaccept.totalfriends(entity.getId()));
                    profile.setTotalPost(userpost.totalpost(entity.getId()));
                    if(entity.getUserUrl()!=null) profile.setProfile_url(image_path + entity.getUserUrl());
                    else profile.setProfile_url(null);
//                    System.out.println("its is workin000");
                    if (isfriend || (!isaccountprivate)) {
                    	
                    	if(!isaccountprivate) {
//                    		public account case 
                    		isfriendrequestsent = true;
                    	}
//						  Friend
//                        String bio = repository.findbio(friend_id);
//                        Integer totalfriends = friendaccept.totalfriends(friend_id);
                        if(isfriend)
                        	profile.setIs_friend(true);
                        else {
                        	profile.setIs_friend(false);
                        }
                        if (this.postService.findMypost(friend_id, page) != null) {
                            List<Post_data> posts = this.postService.findMypost(friend_id, page);
//                            System.out.println("its is workin"+ posts);
                            profile.setTotalPost(this.userpost.totalpost(friend_id));
                            
                            List<PostData1> posts1 = new ArrayList<>();
                            for (Post_data i : posts) {
                            	System.out.println("its is workin11");
                            	boolean gridSystem = true;
                                PostData1 postdata = new PostData1();;
                                List<MultiImage_post> multiImages = this.multiImage.multiimages(i.getId());
                                String[] img = new String[multiImages.size()];
                                for(int k=0;k<multiImages.size();k++) {
                                	img[k] = image_path+ multiImages.get(k).getImage_url();
                                }
                                if(is_text) {
//                                	Don't consider the image
                                	if(img.length>0) {
                                		gridSystem=false;
                                	}
                                	else {
                                		gridSystem=true;
                                	}
                                }
                                else {
//                                	Only takes images in the post 
                                	if(img.length>0) {
                                		gridSystem=true;
                                	}
                                	else {
                                		gridSystem=false;
                                	}
                                }

                                postdata.setPost_img_url(img);
                                List<UserPostLike> userpostlike1 = new ArrayList<>();
                                List<Map<String, Object>> likemap = this.postService.findlikes(i.getId());
                                for (Map<String, Object> mp1 : likemap) {
                                    UserPostLike userpostlike = new UserPostLike();

                                    int likeid = (int) mp1.get("like_id");
                                    int post = (int) mp1.get("post_id");
                                    int report = (int) mp1.get("report");
                                    int userid = (int) mp1.get("user_id");

                                    userpostlike.setLikeid(likeid);
                                    SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String date = mp1.get("date_1").toString();
                                    userpostlike.setDate1((dtf.parse(date)));
                                    userpostlike.setPost_id(post);
                                    userpostlike.setReport(report);
                                    userpostlike.setUser_id(userid);
                                    userpostlike.setUsername((String) mp1.get("username"));
                                    if (mp1.get("user_url") != null)
                                        userpostlike.setUser_url(image_path + (String) mp1.get("user_url"));
                                    userpostlike1.add(userpostlike);
                                }
                                Integer userid1 = userpostrelation.finduseridbypostid(i.getId());
                                String profile_url = userdata.findbyimage(userid1);
                                if (profile_url != null)
                                    postdata.setUser_profile_url(image_path + profile_url);

                                postdata.setLikes(userpostlike1);
//								postdata.setLikes(this.postService.findlikes(i.getId()));
                                postdata.setPost_data(i.getPost_data());
                                postdata.setUsername(i.getUsername());
                                postdata.setPost_id(i.getId());
//                                if (i.getPost_img_url() != null)
//                                    postdata.setPost_img_url(image_path + i.getPost_img_url());
                                postdata.setPost_title(i.getPost_title());
                                postdata.setDate1(i.getDate1());
                                postdata.setTotalikes(this.postService.totalLikes(i.getId()));
                                
//							fetching comment based on post and set into Postcomment entity
                                List<UserPostComment> userpostcomment = new ArrayList<>();
                                List<Map<String, Object>> commentmap = this.postService.findcomment(i.getId());
                                for (Map<String, Object> mp2 : commentmap) {
                                    UserPostComment userpostcomm = new UserPostComment();
                                    if (mp2 != null) {
                                        System.out.println(mp2.get("post_id"));
                                        System.out.println(mp2.get("COMMENT_ID"));
//								int commid = ((BigDecimal)mp2.get("comment_id")).intValue();
//								int post = ((BigDecimal)mp2.get("post_id")).intValue();
//								int userid = ((BigDecimal)mp2.get("user_id")).intValue();

//								Postgres Required Changes
                                        int commid = (int) mp2.get("comment_id");
                                        int post = (int) mp2.get("post_id");
                                        int userid = (int) mp2.get("user_id");

                                        userpostcomm.setComment_text((String) mp2.get("comment_text"));
                                        userpostcomm.setDate1((Date) mp2.get("Date_1"));
                                        userpostcomm.setCommentid(commid);
                                        userpostcomm.setPost_id(post);
                                        userpostcomm.setUser_id(userid);
                                        userpostcomm.setUsername((String) mp2.get("username"));
                                        String url = (String) mp2.get("user_url");
                                        if(url!=null) userpostcomm.setUserprofile(image_path+url);
                                        else userpostcomm.setUserprofile(null);
                                        userpostcomment.add(userpostcomm);
                                    }
                                }
                                postdata.setComment(userpostcomment);
                                postdata.setTotalcomments(this.postService.totalComments(i.getId()));
                                if(gridSystem) {
                                    posts1.add(postdata);                    	
                                }
//                                System.out.println(posts1);
                            }   
                            profile.setAllpost(posts1);
                            try {
                            if(isfriendrequestsent) {
                            	int i = this.postService.isfriendship_requested(id, friend_id);
                            	if (i == 0) {
                                    profile.setIs_requested(false);
                                } else if (i == 1) {
                                    profile.setIs_requested(true);
                                    profile.setIs_sender_request(true);
                                } else {
                                    profile.setIs_requested(true);
                                    profile.setIs_sender_request(false);
                                }	
                            }
                            }
                            catch(Exception e) {
                            	return profile;
                            }
                            return profile;
                        }
                    } 
                    
                    else {
//						notFriend
                        if (id == entity.getId()) {
                            return new UserProfile();
                        }
                        profile.setIs_friend(false);

//					Friend_ship request checking
                        int i = this.postService.isfriendship_requested(id, friend_id);
                        if (i == 0) {
                            profile.setIs_requested(false);
                        } else if (i == 1) {
                            profile.setIs_requested(true);
                            profile.setIs_sender_request(true);
                        } else {
                            profile.setIs_requested(true);
                            profile.setIs_sender_request(false);
                        }
//					End of Friendship request
                        return profile;
                    }
                }
//			user is not present in this id
                return null;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } else {
//        	System.out.println("My own profile");
            String bio = repository.findbio(id);
            Integer totalfriends = friendaccept.totalfriends(id);
            UserProfile profile = new UserProfile();
            if (this.postService.findMypost(id, page) != null) {
            	
                List<Post_data> posts = this.postService.findMypost(id, page);
                profile.setTotalPost(this.userpost.totalpost(id));

                List<PostData1> posts1 = new ArrayList<>();
                for (Post_data i : posts) {
                	boolean gridSystem = true;
                    PostData1 postdata = new PostData1();
                    List<MultiImage_post> multiImages = this.multiImage.multiimages(i.getId());
                   
                    String[] img = new String[multiImages.size()];
                    for(int k=0;k<multiImages.size();k++) {
                    	img[k] = image_path+ multiImages.get(k).getImage_url();
                    }
                    if(is_text) {
//                    	Don't consider the image
                    	if(img.length>0) {
                    		gridSystem=false;
                    	}
                    	else {
                    		gridSystem=true;
                    	}
                    }
                    else {
//                    	Only takes images in the post 
                    	if(img.length>0) {
                    		gridSystem=true;
                    	}
                    	else {
                    		gridSystem=false;
                    	}
                    }
                    postdata.setPost_img_url(img);
                    List<UserPostLike> userpostlike1 = new ArrayList<>();
                    List<Map<String, Object>> likemap = this.postService.findlikes(i.getId());

                    for (Map<String, Object> mp1 : likemap) {
                        UserPostLike userpostlike = new UserPostLike();

                        int likeid = (int) mp1.get("like_id");
                        int post = (int) mp1.get("post_id");
                        int report = (int) mp1.get("report");
                        int userid = (int) mp1.get("user_id");

                        userpostlike.setLikeid(likeid);
                        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String date = String.valueOf(mp1.get("date_1"));
                        userpostlike.setDate1((dtf.parse(date)));
                        userpostlike.setPost_id(post);
                        userpostlike.setReport(report);
                        userpostlike.setUser_id(userid);
                        userpostlike.setUsername((String) mp1.get("username"));
                        if (mp1.get("user_url") != null)
                            userpostlike.setUser_url(image_path + (String) mp1.get("user_url"));
                        	userpostlike1.add(userpostlike);
                    }
                    Integer userid1 = userpostrelation.finduseridbypostid(i.getId());
                    String profile_url = userdata.findbyimage(userid1);
                    if (profile_url != null)
                        postdata.setUser_profile_url(image_path + profile_url);
                    
                    postdata.setLikes(userpostlike1);
//					postdata.setLikes(this.postService.findlikes(i.getId()));
                    postdata.setPost_data(i.getPost_data());
//                    System.out.println(i.getUsername());
                    postdata.setUsername(i.getUsername());
                    postdata.setPost_id(i.getId());
//                    if (i.getPost_img_url() != null)
//                        postdata.setPost_img_url(image_path + i.getPost_img_url());
                    postdata.setPost_title(i.getPost_title());
                    postdata.setDate1(i.getDate1());
                    postdata.setTotalikes(this.postService.totalLikes(i.getId()));

//				fetching comment based on post and set into Postcomment entity
                    List<UserPostComment> userpostcomment = new ArrayList<>();
                    List<Map<String, Object>> commentmap = this.postService.findcomment(i.getId());
                    for (Map<String, Object> mp2 : commentmap) {
                        UserPostComment userpostcomm = new UserPostComment();
                        if (mp2 != null) {
//                            System.out.println(mp2.get("post_id"));
//                            System.out.println(mp2.get("COMMENT_ID"));


                            int commid = (int) mp2.get("comment_id");
                            int post = (int) mp2.get("post_id");
                            int userid = (int) mp2.get("user_id");

                            userpostcomm.setComment_text((String) mp2.get("comment_text"));
                            userpostcomm.setDate1((Date) mp2.get("Date_1"));
                            userpostcomm.setCommentid(commid);
                            userpostcomm.setPost_id(post);
                            userpostcomm.setUser_id(userid);
                            userpostcomm.setUsername((String) mp2.get("username"));
                            userpostcomm.setUserprofile((String) mp2.get("user_url"));
                            userpostcomment.add(userpostcomm);
                        }
                    }
                    postdata.setComment(userpostcomment);
                    postdata.setTotalcomments(this.postService.totalComments(i.getId()));
                    if(gridSystem) {
                        posts1.add(postdata);                    	
                    }
//                    System.out.println(posts1);
                }
                profile.setAllpost(posts1);
            }
            if (bio != null)
                profile.setBio(bio);
            else {
                profile.setBio(new String());
            }
            profile.setTotalFriends(totalfriends);
            profile.setProfile_id(id);
            profile.setUsername(username);
            profile.setEmail(email);
            isaccountprivate = this.userservice.isaccountprivate(id);
            profile.setIs_private(isaccountprivate);
            UserEntity entity = this.repository.findByusername(username);
            profile.setFirstname(entity.getFirstname());
            profile.setLastname(entity.getLastname());
            if(entity.getUserUrl()!=null) profile.setProfile_url(image_path + entity.getUserUrl());
            else profile.setProfile_url(null);
            return profile;
        }
	}	
		
	@GetMapping("/user/get_all_comment")
    public Map<String,List<UserPostComment>> CommentPerPost(@RequestHeader("Authorization") String bearerToken, @RequestParam(name = "post_id") Integer postid, @RequestParam(name = "page") Integer page) {
        if (page != 0) {
            page--;
        }
        
        List<UserPostComment> userpostcomment = new ArrayList<>();
        List<Map<String, Object>> commentmap = this.postService.findperpostcomment(postid, page);
        for (Map<String, Object> mp2 : commentmap) {
            UserPostComment userpostcomm = new UserPostComment();
            if (mp2 != null) {
                System.out.println(mp2.get("post_id"));
                System.out.println(mp2.get("COMMENT_ID"));
                
    			int commid = ((int)mp2.get("comment_id"));
    			int post = ((int)mp2.get("post_id"));
    			int userid = ((int)mp2.get("user_id"));
    			
                userpostcomm.setComment_text((String) mp2.get("comment_text"));
                userpostcomm.setDate1((Date) mp2.get("Date_1"));
                userpostcomm.setCommentid(commid);
                userpostcomm.setPost_id(post);
                userpostcomm.setUser_id(userid);
                userpostcomm.setUsername((String) mp2.get("username"));
                userpostcomm.setUserprofile(image_path + (String) mp2.get("user_url"));
                userpostcomment.add(userpostcomm);
            }
        }
        Map<String,List<UserPostComment>> mp = new HashMap<>();
        mp.put("result", userpostcomment);
        return mp;
    }
		
	@PostMapping("/user/update_profile")
    public Response profileUpdate(@RequestHeader("Authorization") String bearerToken, @RequestParam(value = "firstname", required = false) String firstname, @RequestParam(value = "bio", required = false) String bio,
    		@RequestParam(value = "lastname", required = false) String lastname, @RequestParam(value = "country", required = false) String country, @RequestParam(value = "image", required = false) MultipartFile file) {
        
		bearerToken = bearerToken.substring(7);
        Map<String, Object> claims = this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
        Response response = new Response();
        int id = (int) claims.get("Id");
        try {
            UserEntity entity1 = this.repository.findById(id).get();
            System.out.println(entity1);

            if (firstname != null)
                entity1.setFirstname(firstname);
            if (lastname != null)
                entity1.setLastname(lastname);
            if (country != null)
                entity1.setCountry(country);
            if(bio != null)
                entity1.setBio(bio);

//			handling File in MultiPartFile
            String filename = null;
            try {
                if (file != null) {
                    String name = file.getOriginalFilename();
                    String randomId = UUID.randomUUID().toString();
                    filename = randomId.concat(name.substring(name.lastIndexOf(".")));
                    boolean fileAlreadyExist = this.fileservice.uploadimage(path, filename, file);
                    if (fileAlreadyExist) {
                        response.setMessage("File with same name already exist");
                        response.setError("something went wrong!!");
                        response.setIs_success(false);
                        return response;
                    }
                    entity1.setUserUrl(filename);
                }
            } catch (Exception e) {
                System.out.println("File not uploaded");
                response.setError("Image is not uploaded due to some error");
                response.setMessage("something went wrong");
                response.setIs_success(false);
                return response;
            }
//            System.out.println(entity1);
//			end
            this.repository.save(entity1);
        } catch (Exception e) {
            response.setError("Error Updating Data");
            response.setMessage("something went wrong");
            response.setIs_success(false);
            return response;
        }
        
        response.setError("");
        response.setMessage("User Profile Updated Successfully");
        response.setIs_success(true);
        return response;
    }
	
	@GetMapping(value="images/{image_url}", produces=MediaType.IMAGE_JPEG_VALUE)
	public void loadimage(@PathVariable("image_url") String url,HttpServletResponse servletResponse) throws Exception {
		InputStream resource = this.fileservice.getresource(path, url);
		servletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, servletResponse.getOutputStream());
	}
	
	@PostMapping("user/home/post")
	public Map<String,String> userpost(@RequestParam(value="post_title",required=false) String Post_title,@RequestParam(value="post_data",required=false) String post_data , @RequestHeader("Authorization") String bearerToken,@RequestParam(value="image", required = false) MultipartFile[] files) throws ParseException {
		bearerToken = bearerToken.substring(7);
		Map<String,Object> claims=this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
		int id=(int)claims.get("Id");
		String username = (String)claims.get("Username");
		Post_data user_post = new Post_data();
		if(Post_title!=null)
			user_post.setPost_title(Post_title);
		if(post_data!=null)
			user_post.setPost_data(post_data);
		
		user_post.setUsername(username);
		boolean isHashtagPresent = false;	
		List<String> hashtag = new ArrayList<>();
		if(post_data!=null) {				
			String[] splitStr = post_data.split("\\s+");
			for(int i=0;i<splitStr.length;i++) {
				char firstCharacter = splitStr[i].charAt(0);
				if(Character.compare(firstCharacter,'#')==0) {
//					System.out.println("It is hashtag "+splitStr[i]);
					hashtag.add(splitStr[i]);
					isHashtagPresent = true;
				}
			}
//			System.out.println(hashtag.toString());
		}
		else {
			hashtag.clear();
		}
		
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		user_post.setDate1(dtf.parse(dtf.format(now)));
		try {
			int userid = id;	
			List<String> filenames = new ArrayList<>();
			if(files!=null) {
//				System.out.println("Coming inside");
				for(MultipartFile file:files) {
					String filename="";
					String name = file.getOriginalFilename();
					String randomId = UUID.randomUUID().toString();
					filename = randomId.concat(name.substring(name.lastIndexOf(".")));
					this.fileservice.uploadimage(path, filename, file);
					filenames.add(filename);
				}
			}
			userpost.save(user_post);

//			Post created in Post_data model class
			int user_post_id = user_post.getId();
			
//			User_post_id is used to store multiple image in table MultiPosttable
			for(String filename: filenames) {
				MultiImage_post multimage = new MultiImage_post();
				multimage.setImage_url(filename);
				multimage.setPost_id(user_post_id);
				this.multiImage.save(multimage);
			}
//			End of User_post_id is used to store multiple image in table multiposttable
			
			UserPost post = new UserPost();
			post.setUser_id(userid);
			post.setPost_id(user_post_id);
			this.userpostrelation.save(post);
		
		
			if(isHashtagPresent) {
				hashtagService.createHashtagfrompost(hashtag, user_post_id ,id);
			}
		
			Map<String,String> mp = new HashMap<>();
			mp.put("text", "Post Succesfully Created");
			mp.put("response", "200 OK");
			return mp;
		}
		catch(Exception e) {
//			System.out.println(e+" Invalid post");
			Map<String,String> mp = new HashMap<>();
			mp.put("text", "Oops something went wrong");
			return mp;
		}
	}
	
	@GetMapping("user/hashtag_Account")
	public HashtagProfile hashtagPage(@RequestHeader("Authorization") String bearerToken,@RequestParam(value="hashtag",required=true) String hashtag, @RequestParam(value="page",required = true) Integer page,@RequestParam(name = "is_text",required=false) boolean is_text) {
		try {
			HashtagProfile profile = this.hashtagService.fetch_hashtag_pagedetails(hashtag, page, is_text);
			if(profile.getAllpost()!=null && !profile.getAllpost().isEmpty())
				return profile;
			else {
				profile.setAllpost(new ArrayList<>());
				profile.setCreator_profile("N/A");
				profile.setHashtag_creator("N/A");
				profile.setHashtag_name("N/A");
				profile.setTotalpost(0);
				return profile;
			}
		}
		catch(Exception e) {
			System.out.println(e);
			return null;
		}
	
	}
	
	@GetMapping("user/search_user")
	public Map<String,List<UserEntity>> searchUser(@RequestHeader("Authorization") String bearerToken,@RequestParam(name = "page") Integer page,@RequestParam(name = "query") String search){
		if (page != 0) {
            page--;
        }
		try {
			List<UserEntity> user = iusersearch.searchbyusername(search, page);
			Map<String,List<UserEntity>> mp = new HashMap<>();
			mp.put("result", user);
			return mp;
		}
		catch(Exception e) {
			return new HashMap<>();
		}
	}
	
	@GetMapping("user/search_hashtag")
	public Map<String,List<Hashtag1>> searchHashtag(@RequestHeader("Authorization") String bearerToken, @RequestParam(name = "page") Integer page,@RequestParam(name = "query") String searchhash){		
		if(page!=0) {
			page--;
		}
		try {
			List<Hashtag1> list = this.hashtagService.searchHashtags(searchhash, page);
			Map<String,List<Hashtag1>> mp = new HashMap<>();
			mp.put("result", list);
			return mp;
		}
		catch(Exception e) {
			return new HashMap<>();
		}
	}
	
	@GetMapping("user/all_posts_hashtag")
	public List<PostData1> allpostshashtag(@RequestHeader("Authorization") String bearerToken,@RequestParam(name = "page") Integer page) {
		if(page!=0) {
			page--;
		}
		try {
			List<PostData1> allPosts =	this.hashtagService.fetchAllposts(page, true);
			return allPosts;
		}
		catch(Exception e) {
			return new ArrayList<>();
		}
	}
	
	@GetMapping("user/hashTag_feeds")
	public List<PostData1> specificfeedswithotherPosts(@RequestHeader("Authorization") String bearerToken,@RequestParam(name = "postid") Integer postid,@RequestParam(name = "page") Integer page){
		if(page!=0) {
			page--;
		}
		try {
			List<PostData1> allPosts =	this.hashtagService.fetchSpecificPostwithotherhashtags(postid, page);
			return allPosts;
		}
		catch(Exception e) {
			return new ArrayList<>();
		}
	}
	
//	post update and delete 
	@PostMapping("user/home/post_update")
	public Response userpostupdate(@RequestHeader("Authorization") String bearerToken,@RequestParam(value="post_id",required=true) int postid, @RequestParam(value="post_title",required=false) String Post_title,@RequestParam(value="post_data",required=false) String post_data ,@RequestParam(value="image", required = false) MultipartFile file)throws ParseException {
		bearerToken = bearerToken.substring(7);
		Map<String,Object> claims = this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
		int id=(int)claims.get("Id");
//		System.out.println(id+" id and "+postid+" postid");
		try {
			if(this.postService.isUserPost(postid, id)) {
                    this.postService.updatePostWithoutImage(postid,Post_title,post_data);
                    Response response = new Response();
                    response.setIs_success(true);
                    return response;
			}
			else {
				Response response = new Response();
				response.setIs_success(false);
				response.setError("user post not updated");
				return response;
			}
		}
		catch(Exception e) {
//			System.out.println("Something went wrong");
			return null;
		}
	}
	
	@PostMapping("user/add_comment/delete")
	public Response deleteComment(@RequestHeader("Authorization") String bearerToken,@RequestParam(value="comment_id",required=true) int commentid) throws ParseException
	{
		Response response = new Response();
		bearerToken = bearerToken.substring(7);
		Map<String,Object> claims=this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
		int user_id = (int)claims.get("Id");
		try {
			if(this.postService.isUserCommented(commentid, user_id)) {
				this.postService.deletecomment(commentid);
				response.setIs_success(true);
				response.setMessage("comment deleted succesfully");
			}
			else {
				response.setIs_success(false);
				response.setError("Something went wrong");
				response.setMessage("comment not deleted");
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return response;
	}

	@PostMapping("user/add_comment/update")
	public Response updateComment(@RequestHeader("Authorization") String bearerToken,@RequestParam(value="comment_id",required=true) int commentid,@RequestParam(value="comment_text",required=false) String comment_text) throws ParseException
	{
		Response response = new Response();
		bearerToken = bearerToken.substring(7);
		Map<String,Object> claims=this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
		int user_id = (int)claims.get("Id");
		try {
			if(this.postService.isUserCommented(commentid, user_id)) {
//				update
				this.postService.updatecomment(commentid , comment_text);
				response.setIs_success(true);
				response.setMessage("comment Updated succesfully");
			}
			else {
				response.setIs_success(false);
				response.setError("Something went wrong");
				response.setMessage("comment not Updated");
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return response;
	}

	
	@PostMapping("user/home/delete_post")
	public Response userpostupdate(@RequestHeader("Authorization") String bearerToken, @RequestParam(value="post_id",required=true) int postid) {
		bearerToken = bearerToken.substring(7);
		Map<String,Object> claims = this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
		int id=(int)claims.get("Id");
//		System.out.println(id+" id and "+postid+" postid");
		try {
			if(this.postService.isUserPost(postid, id)) {
				List<MultiImage_post> multipleimages=this.multiImage.multiimages(postid);
				for(MultiImage_post m:multipleimages) {
					this.fileserv.DeleteImage(path,m.getImage_url());
				}
				boolean success = this.postService.deletepost(postid);
				boolean success1 = this.postService.deleteuserpost(postid);
				this.multiImage.deltemultipleImages(postid);
				this.hashtagService.deleteHashtagPost(postid);
				if(success && success1) {
					Response response = new Response();
					response.setIs_success(true);
					response.setTotalpost(this.userpost.totalpost(id));
					response.setMessage("user post deleted succesfully");
					return response;
				}
				else {
					Response response = new Response();
					response.setIs_success(false);
					response.setMessage("Some exception occur catch block");
					response.setError("404 error");
					return response;
				}
			}
			else {
				Response response = new Response();
				response.setIs_success(false);
				response.setError("user post cannot be deleted");
				response.setMessage("Only post creater will delete this post");
				return response;
			}
		}
		catch(Exception e) {
			Response response = new Response();
			response.setIs_success(false);
			response.setError("user not be deleted");
			response.setMessage("exception occurs");
			return response;
		}
	}
	
//	fetch the friends post
	@GetMapping("user/home")
	public Map<String,List<PostData1>> getallpost(@RequestHeader("Authorization") String bearerToken ,@RequestParam(name="page")Integer page , HttpSession session) throws ParseException {
		if(page!=0) {
			page--;
		}
		bearerToken = bearerToken.substring(7);
		Map<String,Object> claims = this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
		int id = (int)claims.get("Id");
		List<Post_data> posts = this.postService.findfriendspost(id,page);
		List<PostData1> posts1 = new ArrayList<>();
//		In case there is no friends to show the posts! we generate random users post 
		if(posts.isEmpty()) {
//			System.out.println("posts is null");
			posts= this.postService.findrandompost(page);
		}
//		System.out.println(posts+"This is all posts");
//		empty post
		for(Post_data i : posts) {
			PostData1 postdata = new PostData1();
			List<MultiImage_post> multiImages = this.multiImage.multiimages(i.getId());
            String[] img = new String[multiImages.size()];
            for(int k=0;k<multiImages.size();k++) {
            	img[k] = image_path+ multiImages.get(k).getImage_url();
            }
            postdata.setPost_img_url(img);
			List<UserPostLike> userpostlike1 = new ArrayList<>();
			List<Map<String,Object>> likemap = this.postService.findlikes(i.getId());
			for(Map<String,Object> mp1: likemap) {
				UserPostLike userpostlike = new UserPostLike();
				
				int likeid = (int) mp1.get("like_id");
				int post = 	 (int) mp1.get("post_id");
				int report = (int) mp1.get("report");
				int userid = (int) mp1.get("user_id");
				
				userpostlike.setLikeid(likeid);
				SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date =  mp1.get("date_1").toString();
				userpostlike.setDate1((dtf.parse(date)));
				userpostlike.setPost_id(post);
				userpostlike.setReport(report);
				userpostlike.setUser_id(userid);
				userpostlike.setUsername((String) mp1.get("username"));
				if(mp1.get("user_url")!=null)
					userpostlike.setUser_url(image_path+(String) mp1.get("user_url"));
				userpostlike1.add(userpostlike);
			}
//			This userid1 is a home page users id which we are taking to find the users profile photo
			Integer userid1 = userpostrelation.finduseridbypostid(i.getId());
			String profile_url = userdata.findbyimage(userid1);
			if(profile_url!=null)
				postdata.setUser_profile_url(image_path+profile_url);
			
			postdata.setUsername(i.getUsername());
			postdata.setLikes(userpostlike1);			
			postdata.setPost_data(i.getPost_data());
			postdata.setPost_id(i.getId());
//			if(i.getPost_img_url()!=null)
//				postdata.setPost_img_url(image_path+i.getPost_img_url());
			postdata.setPost_title(i.getPost_title());
			postdata.setDate1(i.getDate1());
			postdata.setTotalikes(this.postService.totalLikes(i.getId()));
			
//			fetching comment based on post and set into Postcomment entity
			List<UserPostComment> userpostcomment = new ArrayList<>();
			List<Map<String,Object>> commentmap = this.postService.findcomment(i.getId());
			for(Map<String,Object> mp2: commentmap) {
				UserPostComment userpostcomm = new UserPostComment();
				if(mp2!=null) {

				int commid = (int)mp2.get("comment_id");
				int post = (int)mp2.get("post_id");
				int userid = (int)mp2.get("user_id");
				
				userpostcomm.setComment_text((String)mp2.get("comment_text"));
				userpostcomm.setDate1((Date)mp2.get("Date_1"));
				userpostcomm.setCommentid(commid);
				userpostcomm.setPost_id(post);
				userpostcomm.setUser_id(userid);
				userpostcomm.setUsername((String)mp2.get("username"));
				userpostcomm.setUserprofile((String)mp2.get("user_url"));
				userpostcomment.add(userpostcomm);
				}
			}
			postdata.setComment(userpostcomment);
			postdata.setTotalcomments(this.postService.totalComments(i.getId()));
			postdata.setUser_id(userid1);
			posts1.add(postdata);
			}
		Map<String,List<PostData1>> mp = new HashMap<>();
		mp.put("result", posts1);
		return mp;
	}
	
	@GetMapping("user/friend_suggestions")
	public Map<String,List<UsserEntity1>> getfriendssuggestion(@RequestHeader("Authorization") String bearerToken) {
		
		bearerToken = bearerToken.substring(7);
		Map<String,Object> claims=this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
		int id = (int)claims.get("Id");
		
		boolean isfriendrequestrelations = false;
		Set<Integer> userset = new HashSet<>();
		Set<Integer> friendset = new HashSet<>();
		   
	
        List<Map<String,Object>> map = this.postService.getallfriendrequest(id);
        if(map!=null) {
        	for(Map<String,Object> mp: map) {
        		friendset.add((Integer)mp.get("friend_id"));
        		userset.add((Integer)mp.get("user_id"));
        	}
        	isfriendrequestrelations = true;
        }
	
        List<UsserEntity1> userdetails1 = new ArrayList<>();
		
//		Friend suggestion code 
		
		List<UserEntity> userdetails = this.userservice.friendSuggestion(id);
		
		if(userdetails!=null) {
			
			for(UserEntity entity:userdetails) {
				UsserEntity1 userentity1 = new UsserEntity1();
				userentity1.setBio(entity.getBio());
				userentity1.setId(entity.getId());
				userentity1.setCountry(entity.getCountry());
				userentity1.setDateandtime(entity.getDateandtime());
				userentity1.setEmail_address(entity.getEmail_address());
				userentity1.setFirstname(entity.getFirstname());
				userentity1.setLastname(entity.getLastname());
				userentity1.setIs_requested(false);
				userentity1.setIs_sender_request(false);
				userentity1.setUsername(entity.getUsername());
				if(entity.getUserUrl()!=null)
					entity.setUserUrl(image_path+entity.getUserUrl());
				if(entity.getPassword()!=null) {
					entity.setPassword(null);
				}
				userentity1.setUserUrl(entity.getUserUrl());
				if(isfriendrequestrelations) {
					if(friendset.contains(entity.getId())) {
						userentity1.setIs_requested(true);
						userentity1.setIs_sender_request(true);
//						is_requested = true
//						is_sender_request = true (request is send from our side)
					}
					
					if(userset.contains(entity.getId())) {
						userentity1.setIs_requested(true);
						userentity1.setIs_sender_request(false);
//						is_requested = true
//						is_sender_request = false (request is send by friend)
					}
				}
				userdetails1.add(userentity1);
			}
		}
		
//		End of Friend suggestion code
		Map<String,List<UsserEntity1>> mp = new HashMap<>();
		mp.put("result",userdetails1);
		return mp;
	}
	
	@PostMapping("user/home/{id}")
	public UserProfile getfriendspost(@RequestHeader("Authorization") String bearerToken,@PathVariable("id") int friend_id,@RequestParam(name="page")Integer page){
		if(page!=0) {
			page--;
		}
//		String image_path = "http://localhost:8887/images/";
		String image_path = "https://jaiyto.com/images/";
		bearerToken = bearerToken.substring(7);
		Map<String,Object> claims= this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
		int id = (int)claims.get("Id");
		boolean isfriend = false;
		UserProfile profile = new UserProfile();
		try {
			isfriend = this.postService.checkfriendornot(id, friend_id);
			UserEntity entity = this.postService.find_user_profile_details(friend_id);
			if(entity!=null) {
				profile.setProfile_id(entity.getId());
				profile.setBio(entity.getBio());
				profile.setProfile_url(entity.getUserUrl());
				profile.setUsername(entity.getUsername());
				profile.setTotalFriends(friendaccept.totalfriends(entity.getId()));
				profile.setTotalPost(userpost.totalpost(entity.getId()));
//				System.out.println("coming here");
				if(isfriend) {
//					Friend
//					String bio = repository.findbio(friend_id);
//					Integer totalfriends = friendaccept.totalfriends(friend_id);
					profile.setIs_friend(true);
					if(this.postService.findMypost(friend_id,page) != null) {
						List<Post_data> posts = this.postService.findMypost(friend_id, page);
						
//						System.out.println(posts+" something is missing");
						profile.setTotalPost(this.userpost.totalpost(friend_id));
						
						List<PostData1> posts1 = new ArrayList<>();
						for(Post_data i : posts) {

							PostData1 postdata = new PostData1();
							List<UserPostLike> userpostlike1 = new ArrayList<>();
							List<Map<String,Object>> likemap = this.postService.findlikes(i.getId());
							
							for(Map<String,Object> mp1: likemap) {
								UserPostLike userpostlike = new UserPostLike();
								int likeid = (int)mp1.get("like_id");
								int post = 	 (int)mp1.get("post_id");
								int report = (int)mp1.get("report");
								int userid = (int)mp1.get("user_id");
								
//								Postgres Required Changes
								
//								int likeid = (int) mp1.get("like_id");
//								int post = 	 (int) mp1.get("post_id");
//								int report = (int) mp1.get("report");
//								int userid = (int) mp1.get("user_id");
								
								userpostlike.setLikeid(likeid);
								SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								String date = (String)mp1.get("date_1");
								userpostlike.setDate1((dtf.parse(date)));
								userpostlike.setPost_id(post);
								userpostlike.setReport(report);
								userpostlike.setUser_id(userid);
								userpostlike.setUsername((String) mp1.get("username"));
								if(mp1.get("user_url")!=null)
									userpostlike.setUser_url(image_path+(String) mp1.get("user_url"));
								userpostlike1.add(userpostlike);
							}
							Integer userid1 = userpostrelation.finduseridbypostid(i.getId());
							String profile_url=userdata.findbyimage(userid1);
							if(profile_url!=null)
								postdata.setUser_profile_url(image_path+profile_url);
							
							postdata.setLikes(userpostlike1);
//							postdata.setLikes(this.postService.findlikes(i.getId()));
							postdata.setPost_data(i.getPost_data());
							System.out.println(i.getUsername());
							postdata.setUsername(i.getUsername());
							postdata.setPost_id(i.getId());
//							if(i.getPost_img_url()!=null)
//								postdata.setPost_img_url(image_path+i.getPost_img_url());
							postdata.setPost_title(i.getPost_title());
							postdata.setDate1(i.getDate1());
							postdata.setTotalikes(this.postService.totalLikes(i.getId()));
							
//							fetching comment based on post and set into Postcomment entity
							List<UserPostComment> userpostcomment = new ArrayList<>();
							List<Map<String,Object>> commentmap = this.postService.findcomment(i.getId());
							for(Map<String,Object> mp2: commentmap) {
								UserPostComment userpostcomm = new UserPostComment();
								if(mp2!=null) {
								System.out.println(mp2.get("post_id"));
								System.out.println(mp2.get("COMMENT_ID"));
								int commid = (int)mp2.get("comment_id");
								int post = (int)mp2.get("post_id");
								int userid = (int)mp2.get("user_id");
								
//								Postgres Required Changes
//								int commid = (int)mp2.get("comment_id");
//								int post = (int)mp2.get("post_id");
//								int userid = (int)mp2.get("user_id");
								
								userpostcomm.setComment_text((String)mp2.get("comment_text"));
								userpostcomm.setDate1((Date)mp2.get("Date_1"));
								userpostcomm.setCommentid(commid);
								userpostcomm.setPost_id(post);
								userpostcomm.setUser_id(userid);
								userpostcomm.setUsername((String)mp2.get("username"));
								userpostcomm.setUserprofile((String)mp2.get("user_url"));
								userpostcomment.add(userpostcomm);
								}
							}
							postdata.setComment(userpostcomment);
							postdata.setTotalcomments(this.postService.totalComments(i.getId()));
							posts1.add(postdata);
							System.out.println(posts1);
						}
						profile.setAllpost(posts1);
						return profile;
					}
				}
				else {
//					notFriend
					if(id==entity.getId()) {
						return new UserProfile();
					}
					System.out.println("comes here");
					profile.setIs_friend(false);
				
//					Friend_ship request checking 
				
					if(this.postService.isfriendship_requested(id, friend_id)==0) {
						profile.setIs_requested(false);
					}
				else if(this.postService.isfriendship_requested(id, friend_id)==1) {
					profile.setIs_requested(true);
					profile.setIs_sender_request(true);
				}
				else {
					profile.setIs_requested(true);
					profile.setIs_sender_request(false);
				}
//				End of Friendship request
				return profile;
				}
			}
//			user is not present in this id
			return null;
		}
		catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	
//	Users friends 
	@GetMapping("user/friendlist")
	public Map<String,List<FriendsList>> usersFriend(@RequestParam(value="friend_id", required=false) Integer friendid , @RequestHeader("Authorization") String bearerToken,@RequestParam(name="page")Integer page) {
		if(page!=0) {
			page--;
		}
		bearerToken = bearerToken.substring(7);
		Response response = new Response();
		Map<String,Object> claims=this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
		int userid = (int)claims.get("Id");
		try {
			if(friendid!=null) {
//				System.out.println("friends of friends will run");
				List<FriendsList> friends = friendlist.FriendsList(friendid,userid,page);
				Map<String,List<FriendsList>> mp = new HashMap<>();
				mp.put("Friends", friends);
				return mp;
			}
//				System.out.println("my friends will run");
				List<FriendsList> friends = this.friendlist.MyFriendsList(userid,page);
				Map<String,List<FriendsList>> mp = new HashMap<>();
				mp.put("Friends", friends);
				return mp;
		}
		catch(Exception e) {
//			System.out.println("eror");
			return null;
		}
	}
	
	
//	User can send Friend request to many users
	@PostMapping("user/request")
	public Response friendRequest(@RequestParam("friend_user_id") int to,@RequestHeader("Authorization") String bearerToken) {
		bearerToken = bearerToken.substring(7);
		Response response = new Response();
		Map<String,Object> claims=this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
		int id = (int)claims.get("Id");
		int user_id = id;
		int friend_id = to;
		try {
	//  Dateandtime  		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		
    //	check condition if user id is not present
		Userfriend_request friend_request = new Userfriend_request(user_id,friend_id,dtf.format(now));
	//  end dateandtime
			userfriendrequest.save(friend_request);
		}
		catch(Exception e) {
			response.setIs_success(false);
			response.setMessage("Error While Sending Request");
			response.setError("Error "+e);
		}
		response.setIs_success(true);
		response.setMessage("Friend Request Send Succesfull");
		return response;
	}

	@PostMapping("user/unsentrequest")
	public Response unfriendRequest(@RequestParam("friend_id") int to,@RequestHeader("Authorization") String bearerToken) {
		bearerToken = bearerToken.substring(7);
		Response response = new Response();
		Map<String,Object> claims=this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
		int id = (int)claims.get("Id");
		int user_id = id;
		int friend_id = to;
		try {
			this.userfriendrequest.unsentfriendrequest(friend_id,user_id);
			response.setError("false");
			response.setIs_success(true);
			response.setMessage("Cancel Friend Request Success");
			return response;
		}
		catch(Exception e) {
			response.setError("True");
			response.setIs_success(false);
			response.setMessage("error: "+e);
			return response;
		}
	}
	
	@GetMapping("user/get_all_friend_request")
 	public Map<String,List<Friend_request>> get_all_friend_request(@RequestHeader("Authorization") String bearerToken,@RequestParam(name="page")Integer page){
		if(page!=0) {
			page--;
		}
		bearerToken = bearerToken.substring(7);
		Map<String,Object> claims=this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
		int id = (int)claims.get("Id");
		Map<String,List<Friend_request>> allrequest = new HashMap<>();
		List<Map<String,Object>> allfriendrequest = new ArrayList<Map<String, Object>>();
		try{
			List<Friend_request> allfriends = new ArrayList<>();
			
//			pagination code
			int pageSize = 2;
			Pageable p = PageRequest.of(page, pageSize);
			Page<Map<String,Object>> totalrequest = userfriendrequest.totalrequest(id,p);
			if(totalrequest !=null) {
				 allfriendrequest = totalrequest.getContent();
			}

//			List<Map<String,Object>> allfriendrequest = userfriendrequest.totalrequest(id);
			for(Map<String,Object> mp: allfriendrequest) {
				Friend_request request = new Friend_request();
				request.setFirstname((String)mp.get("firstname"));
				request.setLastname((String)mp.get("lastname"));
				request.setUsername((String)mp.get("username"));
//				System.out.println("issues");
				int friend_id= (int) mp.get("id");
//				System.out.println("no issues");
				request.setFriend_request_id(friend_id);
				String profile_url = (String)mp.get("user_url");
				if(profile_url!=null)
					request.setProfile_url(image_path+profile_url);
				allfriends.add(request);
			}
			allrequest.put("result", allfriends);
			return allrequest;
		}
		catch(Exception e) {
			System.out.print("Something went wrong");
			return null;
		}
	}
	
//	Accept or reject a users friend request
	@PostMapping("user/accept")
	@ResponseBody
	public Response friendAccept(@RequestParam("friend_user_id") int from,@RequestHeader("Authorization") String bearerToken) {
		bearerToken = bearerToken.substring(7);
		Map<String,Object> claims=this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
		int id = (int)claims.get("Id");
		int user_id=id;
//		System.out.println(user_id);
		Response response = new Response();
		try {
//		first entry from user to friend id
//      Dateandtime  		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		UserFriends userfriends = new UserFriends(user_id,from,dtf.format(now));
		friendaccept.save(userfriends);
		
//		second entry from friends id to user
		UserFriends userfriends1 = new UserFriends(from,user_id,dtf.format(now));
		friendaccept.save(userfriends1);
		
//      third entry for deleting record from userfrien_request table
		userfriendrequest.deletefriendrequest(from, user_id);
		}
		catch(Exception e) {
			response.setIs_success(false);
			response.setMessage("Frienship request failed");
			response.setError("error "+e);
		}
		response.setIs_success(true);
		response.setMessage("Frienship request succesfull");
		return response;
	}
	
//	Accept or reject a users friend request
	@PostMapping("user/reject")
	public Response friendDenied(@RequestParam("friend_user_id") int friendId,@RequestHeader("Authorization") String bearerToken) {
		bearerToken = bearerToken.substring(7);
		Map<String,Object> claims=this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
		int id = (int)claims.get("Id");
		int user_id = id;
		Response response = new Response();
		try {
			userfriendrequest.deletefriendrequest(friendId, user_id);
			response.setIs_success(true);
			response.setMessage("Friendship Denied Success");
		}
		catch(Exception e) {
			response.setIs_success(true);
			response.setMessage("Error while Denying Friendship");
			response.setError("Error :"+e);
			return response;
		}
		return response;
	}
	
	@PostMapping("user/report")
	public String useraccountandpostreport(@RequestHeader("Authorization") String bearerToken,@RequestParam(value="Post_id") Integer post_report) {
		bearerToken = bearerToken.substring(7);
        Map<String, Object> claims = this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
        int user_id = (int) claims.get("Id");
        boolean isReportedFromThisAccount=this.reportservice.isAlreadyReported(user_id, post_report);
        if(!isReportedFromThisAccount) {
        	Integer totalreports = this.reportservice.totalReports(post_report);
        	System.out.println(totalreports);
        	if(totalreports>=3) {
//        		System.out.println("coming inside");
        		this.reportservice.deleteallrecords(post_report);
        	}
        	return "Post Reported";
        }
        else {
        	return "Already Reported";
        }
	}
	
	@PostMapping("user/add_like")
    public Map<String,Object> userpostlike(@RequestHeader("Authorization") String bearerToken, @RequestBody UserPostLike post_like) {
        bearerToken = bearerToken.substring(7);
        Response response = new Response();
        Map<String, Object> claims = this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
        int user_id = (int) claims.get("Id");
        try {
            post_like.setUser_id(user_id);
    		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    		Date now = new Date();
    		post_like.setDate1(dtf.parse(dtf.format(now)));
            UserPostLike postlike = this.postlike.isUserLikePost(post_like.getPost_id(), user_id);
            System.out.println("post LIke id "+postlike);
            if (postlike == null) {
                this.postlike.save(post_like);
            } else {
//            	System.out.println(post_like);
//            	System.out.println("updated like created");
//            	System.out.println(postlike.getLikeid());
//            	System.out.println(post_like.getReport());
                this.postlike.updateRecordfromtable(postlike.getLikeid(), post_like.getReport());
            }
            int total_likes = this.postlike.totallikes(post_like.getPost_id());
            UserPostLike mylike = this.postlike.mylike(post_like.getPost_id(),post_like.getUser_id());
            System.out.println(mylike);
            if(post_like!=null) {
            	mylike.setReport(post_like.getReport());
            }
            Map<String,Object> mp = new HashMap<>();
            mp.put("is_success", true);
            mp.put("total_likes", total_likes);
            mp.put("user_like", mylike);
            response.setIs_success(true);
            response.setMessage("like added successfully");
            return mp;
        } catch (Exception e) {
        	Map<String,Object> mp = new HashMap<>();
        	mp.put("is_sucess",false);
        	mp.put("message","Something went wrong !!");
            System.out.println(e);
            return mp;
        }
    }

    @PostMapping("user/add_comment")
    public Map<String,Object> postComment(@RequestBody UserPostComment post_comment, @RequestHeader("Authorization") String bearerToken) throws ParseException {
        bearerToken = bearerToken.substring(7);
        Map<String, Object> claims = this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
        int user_id = (int) claims.get("Id");
        post_comment.setUser_id(user_id);
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date now = new Date();
		post_comment.setDate1(dtf.parse(dtf.format(now)));
        try {
            this.commentRepo.save(post_comment);
            int total_comments = this.commentRepo.totalcomment(post_comment.getPost_id());
            Map<String,Object> mp = new HashMap<>();
            mp.put("is_success", true);
            mp.put("total_comments", total_comments);
            return mp;    
        } 
        catch (Exception e) {
        	Map<String,Object> mp = new HashMap<>();
        	mp.put("is_success", false);
        	mp.put("message", "Something went wrong");
            return mp;
        }
        
    }	

    @PostMapping("user/account_switch")
    public Response accountswitch(@RequestHeader("Authorization") String bearerToken,@RequestParam(value="is_private",required=true) boolean is_private) {
    	bearerToken = bearerToken.substring(7);
		Map<String,Object> claims=this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
		int id = (int)claims.get("Id");
		Response response = new Response();
		try {
			 this.userservice.switchprivatepublic(id, is_private);
			 response.setIs_success(true);
			 response.setMessage("Account Switched Succesfully");
		}
		catch(Exception e) {
			response.setError("something went wrong");
			response.setMessage("Error:"+e);
			response.setIs_success(false);
			return response;
		}
		return response;
    }

    @PostMapping("user/unfriend")
    public Response unfriend(@RequestParam("friend_user_id") int friend_id,@RequestHeader("Authorization") String bearerToken) {
    	bearerToken = bearerToken.substring(7);
		Map<String,Object> claims=this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
		int user_id = (int)claims.get("Id");
		Response response = new Response();
		try {
			userfriendrequest.unfriendexistingfriend(friend_id, user_id);
			userfriendrequest.unfriendexistingfriend(user_id, friend_id);
			response.setIs_success(true);
			response.setMessage("Friendship Denied Success");
		}
		catch(Exception e) {
			response.setIs_success(true);
			response.setMessage("Error while Denying Friendship");
			response.setError("Error :"+e);
			return response;
		}
		return response;
    }
    
    @PostMapping("FCM_Messaging")
    public void storeFCMToken(@RequestHeader("Authorization") String bearerToken,@RequestParam("FCMToken") String fcm) {
    	bearerToken = bearerToken.substring(7);
		Map<String,Object> claims=this.jwtTokenUtil.getAllClaimsFromToken(bearerToken);
		int user_id = (int)claims.get("Id");
		Response response = new Response();
		try {
			boolean isalreadyexist = this.fcmService.isFCMTokenExist(fcm, user_id);
			if(isalreadyexist) {
				System.out.println("User FCM Token already Exist and updated");
			
			}
			else {
				System.out.println("User Fcm token created");
			}
		}
		catch(Exception e) {
			
		}
    }

}