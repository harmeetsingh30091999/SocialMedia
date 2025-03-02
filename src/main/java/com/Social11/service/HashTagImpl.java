package com.Social11.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.Social11.Dao.HashPostRepository;
import com.Social11.Dao.HashtagRepository;
import com.Social11.Dao.IuserPostRepos;
import com.Social11.Dao.IuserPostdata;
import com.Social11.Dao.IuserRepository;
import com.Social11.Dao.MultiImageRepository;
import com.Social11.models.Hashtag;
import com.Social11.models.Hashtag1;
import com.Social11.models.HashtagProfile;
import com.Social11.models.Hashtag_post;
import com.Social11.models.MultiImage_post;
import com.Social11.models.PostData1;
import com.Social11.models.Post_data;
import com.Social11.models.UserEntity;
import com.Social11.models.UserPostComment;
import com.Social11.models.UserPostLike;

@Service
public class HashTagImpl implements HashTagService{

	@Autowired
	private PostService postService;
	
	@Autowired
	private HashtagRepository hashRepo;
	
	@Autowired
	private HashPostRepository hashpostRepo;
	
	@Autowired
	private MultiImageRepository multiImage;
	
	@Autowired
	private IuserPostRepos postRepo;
	
	@Autowired
	private IuserRepository userdata;
	
	@Autowired
	private IuserPostdata userpostrelation;
	
	@Value("${project.image}")
	private String path;
	
	@Value("${profile.image}")
	private String image_path;
	
	@Override
	public void createHashtagfrompost(List<String> hashtags,int postid, int userid) {
		System.out.println("CreateHastagiscalling");
		Integer hashId;
		for(int i=0; i<hashtags.size();i++) {
		    hashId=hashRepo.isHashtagPresent(hashtags.get(i));
			if(hashId==null) {
				Hashtag hashtag = new Hashtag();
				hashtag.setHashName(hashtags.get(i));
				hashtag.setHashcreater(userid);
				hashRepo.save(hashtag);
				hashId = hashtag.getId();
			}
//			System.out.println(hashId);
			Hashtag_post hashpost = new Hashtag_post();
			hashpost.setHashId(hashId);
			hashpost.setPostid(postid);
			this.hashpostRepo.save(hashpost);
		}
		return;
	}

	@Override
	public HashtagProfile fetch_hashtag_pagedetails(String hashtag, Integer pagenumber, boolean is_text) {
		if(pagenumber!=0) {
			pagenumber--;
		}
		int pageSize = 10;
		
		Pageable p = PageRequest.of(pagenumber, pageSize);
		
		Hashtag hashdetail = this.hashRepo.getHashdatafromhashtag(hashtag);
		
		if(hashdetail!=null) {
		
		HashtagProfile profile = new HashtagProfile();
		
		profile.setHashtag_id(hashdetail.getId());
		
		profile.setHashtag_name(hashtag);
		
		Map<String,Object> mp = this.userdata.getNameAndProfile(hashdetail.getHashcreater());
		
		if(mp!=null) {
			if(mp.get("firstname")!=null) {
				if(mp.get("lastname")!=null) {
					profile.setHashtag_creator(mp.get("firstname").toString()+" "+mp.get("lastname").toString());					
				}
				profile.setHashtag_creator(mp.get("firstname").toString());
			}
			else {
				profile.setHashtag_creator("N/A");
			}
			if(mp.get("userUrl")!=null) {
				profile.setCreator_profile(mp.get("userUrl").toString());
			}
			else {
				profile.setCreator_profile("N/A");
			}
		}
		
		if(hashdetail!=null && hashdetail.getId()!=null) {
			List<Integer> postids = new ArrayList<>();
			Page<Integer> post	= this.hashpostRepo.getAllPostWRTHashtag(p,hashdetail.getId());
			if(post!=null) {
				postids = post.getContent();
			    profile.setTotalpost(postids.size());
			}

			List<Post_data> allposts = new ArrayList<>();
//			System.out.println(postids);
			for(int i=0;i<postids.size();i++) {
				Post_data posts = new Post_data();
				posts = this.postRepo.fetchuserPostforHashtag(postids.get(i));
				if(posts!=null)
					allposts.add(posts);
			}
			System.out.println(allposts);
//			System.out.println("coming here");
			profile.setAllpost(this.createPostData(allposts, is_text));
			
			return profile;
			}
		}
		return new HashtagProfile();
	}
	
	@Override
	public List<PostData1> createPostData(List<Post_data> posts, boolean is_text){
		List<PostData1> posts1 = new ArrayList<>();
		boolean notConsider = false;
		try {
        for (Post_data i : posts) {
        	boolean gridSystem = true;
            PostData1 postdata = new PostData1();
            if(i!=null) {
            List<MultiImage_post> multiImages = this.multiImage.multiimages(i.getId());
            String[] img = new String[multiImages.size()];
            if(is_text) {
//            	Don't consider the image
            	gridSystem=false;
            }
            else {
//            	Only takes images in the post 
            	if(img.length>0) {
            		gridSystem=true;
        			notConsider = false;
            	}
            	else {
            		if(!is_text) {
            			notConsider=true;
            		}
            		gridSystem=false;
            	}
            }
            if(gridSystem==true) {
            	for(int k=0;k<multiImages.size();k++) {
            		img[k] = image_path+ multiImages.get(k).getImage_url();
            	}
            	postdata.setPost_img_url(img);
            }
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
                try {
                	userpostlike.setDate1((dtf.parse(date)));
                }
                catch(Exception e) {
                	System.out.println("issue: "+e);
                }
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
//			postdata.setLikes(this.postService.findlikes(i.getId()));
            postdata.setPost_data(i.getPost_data());
            postdata.setUsername(i.getUsername());
            postdata.setPost_id(i.getId());
//            if (i.getPost_img_url() != null)
//                postdata.setPost_img_url(image_path + i.getPost_img_url());
            postdata.setPost_title(i.getPost_title());
            postdata.setDate1(i.getDate1());
            postdata.setTotalikes(this.postService.totalLikes(i.getId()));
            
//		fetching comment based on post and set into Postcomment entity
            List<UserPostComment> userpostcomment = new ArrayList<>();
            List<Map<String, Object>> commentmap = this.postService.findcomment(i.getId());
            for (Map<String, Object> mp2 : commentmap) {
                UserPostComment userpostcomm = new UserPostComment();
                if (mp2 != null) {

                //	Postgres Required Changes
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
            if(!notConsider)
            	posts1.add(postdata);          
            }          	
        }   
		return posts1;
		}
		catch(Exception e) {
			System.out.println("Reason: "+e);
			return new ArrayList<>();
		}
	}

	@Override
	public void deleteHashtagPost(int post_id) {
		
	// Delete hashtagpost where post is getting deleted
		this.hashpostRepo.deletehashtags(post_id);
		
		
	}

	@Override
	public List<Hashtag1> searchHashtags(String hashname, int pagenumber) {
		// TODO Auto-generated method stub
		int pageSize = 10;
		Pageable p = PageRequest.of(pagenumber, pageSize);
		List<Hashtag1> hashtags = new ArrayList<>();
		Page<Map<String,Object>> posts = this.hashRepo.getAllTrendingHashtags(p, hashname);
//		System.out.println(posts);
		if(posts !=null) {
			List<Map<String,Object>> allposts = posts.getContent();
			for(Map<String,Object> mp: allposts) {
				Hashtag1 hastag = new Hashtag1();
				if(mp.get("hash_id")!=null) {
					hastag.setHashtag_id((Integer)mp.get("hash_id"));
					hastag.setTotalpost(this.hashpostRepo.TotalPostHashtag((Integer)mp.get("hash_id")));
				}
				if(mp.get("hash_name")!=null)
					hastag.setHashtag_name((String)mp.get("hash_name"));
				if(mp.get("hashcreater")!=null)
					hastag.setHashtag_creator((Integer)mp.get("hashcreater"));
				hastag.setCreator_profile("N/A");
				hashtags.add(hastag);
			}
			return hashtags;
		}
		
		else {
			System.out.println("coming here or not");
			return new ArrayList<Hashtag1>();
		}
	}

	@Override
	public List<PostData1> fetchAllposts(int pagenumber, boolean gridSystem) {
		int pageSize = 10;
		Pageable p = PageRequest.of(pagenumber, pageSize);
		Page<Integer> postids = this.hashpostRepo.fetchAllPost(p);
		List<Integer> postidss = new ArrayList<>();
		if(postids!=null) {
			postidss = postids.getContent();
		}
		List<Post_data> posts = new ArrayList<>();
		for(Integer postid:postidss) {
			Post_data postdata = this.postService.findmyPost(postid);
			posts.add(postdata);
		}
		List<PostData1> posts1 = this.createPostData(posts, true);
		return posts1;
	}

	@Override
	public List<PostData1> fetchSpecificPostwithotherhashtags(int pagenumber, int postid) {
		int pageSize = 10;
		Pageable p = PageRequest.of(pagenumber, pageSize);
		Page<Integer> postids = this.hashpostRepo.fetchAllPostwithSpecificPost(p,postid);
		List<Integer> postidss = new ArrayList<>();
		if(postids!=null) {
			postidss = postids.getContent();
		}
		List<Post_data> posts = new ArrayList<>();
		for(Integer postid1:postidss) {
			Post_data postdata = this.postService.findmyPost(postid1);
			posts.add(postdata);
		}
		List<PostData1> posts1 = this.createPostData(posts, true);
		return posts1;
	}

}
