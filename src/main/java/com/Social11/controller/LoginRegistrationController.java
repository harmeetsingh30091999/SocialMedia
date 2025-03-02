package com.Social11.controller;

import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Social11.Dao.IuserEntityTemp;
import com.Social11.Dao.IuserRepository;
import com.Social11.helper.Jwtutil;
import com.Social11.models.Response1;
import com.Social11.models.UserEntity;
import com.Social11.models.UserEntityTemp;
import com.Social11.service.IjavaMailService;

@RestController
public class LoginRegistrationController {

	@Autowired
	IjavaMailService mailService;
	
//	@Autowired
//	UserProfileEntity userProfile;

	@Autowired
	IuserRepository Repository;
	
	@Autowired
	private PasswordEncoder bcyrtp;
	
	
	@Autowired
	private IuserEntityTemp tempentity;
	
	@Autowired
	private IuserRepository repository;
	
	
    @PostMapping("/create_user")
    public Map<String, String> createUser(@RequestBody UserEntityTemp userprofile) {
        Map<String, String> mp = new HashMap<>();
        String message;
        try {
            if (!userprofile.getEmail_address().isEmpty() && !userprofile.getPassword().isEmpty() && !userprofile.getUsername().isEmpty()) {
//                System.out.println("TestingForExist" + Repository.findByusername(userprofile.getUsername()));
                if (Repository.findByemail(userprofile.getEmail_address()) != null || Repository.findByusername(userprofile.getUsername()) != null) {
                    mp.put("message", "User with this email or username already exist");
                    return mp;
                } else {

                    Long otp = Jwtutil.generateOtp();

                    message = mailService.SendMailToEmail(userprofile.getEmail_address(), otp);
                    userprofile.setOtp(otp);
                    
                    
                    if (message != null) {
                    	userprofile.setDateandtime(Jwtutil.getCurrentDateAndTime());
                        if (tempentity.findByemail(userprofile.getEmail_address()) == null) {
                        	tempentity.save(userprofile);
                        } else {
                            tempentity.deletebyemail(userprofile.getEmail_address());
                            tempentity.save(userprofile);
                        }
                        mp.put("email", userprofile.getEmail_address());
                        mp.put("success", "true");
                        mp.put("message", "Otp Send Succesfully check your email for otp");
                        mp.put("response", "200 OK");
                        return mp;
                    } else {
                        mp.put("success", "false");
                        mp.put("message", "Opps something went wrong opt not send");
                        mp.put("response", "400 Bad Request");
                        return mp;
                    }
                }
            } else {
                mp.put("message", "fields cannot be empty");
                return mp;
            }

        } catch (Exception ex) {
//            System.out.println("Exception while creating account");
            mp.put("text", "Exception occur");
            return mp;
        }
    }

    
    @GetMapping("checkUsernameorEmail")
    public Response1 isEmailorUsernameavailable(@RequestParam(value="username",required=false)String username,@RequestParam(value="email",required=false) String email) {
    	try {
    		if(email!=null) {
    			UserEntity entity = this.repository.findEmail(email);
    			if(entity==null) {
    				Response1 res = new Response1();
    				res.setIs_success(true);
    				res.setIs_exists(false);
    				res.setMessage("User not present with this email");
    				return res;
    			}
    			else {
    				Response1 res = new Response1();
    				res.setIs_success(false);
    				res.setIs_exists(true);
    				res.setMessage("User exists with this email");
    				return res;
    			}
    		}
    		else {
    			UserEntity entity = this.repository.findUsername(username);    		
    			if(entity==null) {
    				Response1 res = new Response1();
    				res.setIs_success(true);
    				res.setIs_exists(false);
    				res.setMessage("User not present with this username");
    				return res;
    			}
    			else {
    				Response1 res = new Response1();
    				res.setIs_success(false);
    				res.setIs_exists(true);
    				res.setMessage("User exists with this username");
    				return res;
    			}
    		}
    	}
    	catch(Exception e) {
//    		System.out.println("Exception occur ");
			Response1 res = new Response1();
			res.setIs_success(false);
			res.setIs_exists(false);
			res.setMessage("Something went wrong");
    		return res;
    	}
    }

    @PostMapping("/forget_password")
    public Map<String, String> forgetPassword(@RequestParam("email") String email) {
        Map<String, String> mp = new HashMap<>();
        UserEntityTemp userEntityTemp = new UserEntityTemp();
        if (!email.isEmpty()) {
            if (Repository.findByemail(email) != null) {
                Long otp = Jwtutil.generateOtp();
                System.out.println(otp);
                userEntityTemp.setEmail_address(email);
                userEntityTemp.setOtp(otp);

                //TODO bug need to resolve
                if (tempentity.findByemail(userEntityTemp.getEmail_address()) != null) {
                    tempentity.deletebyemail(userEntityTemp.getEmail_address());
                }
                tempentity.save(userEntityTemp);

                mailService.SendMailToEmail(email, otp);
                mp.put("email", email);
                mp.put("message", "Otp Send Successfully check your email for otp");
                mp.put("success", "true");

            } else {
                mp.put("message", "Invalid email address");
                mp.put("success", "false");
            }

        } else {
            mp.put("message", "Email may not be blank");
            mp.put("success", "false");
        }

        return mp;
    }


    @PostMapping("/verify_otp")
    public Map<String, String> verifyOtpNew(@RequestParam("otp") long otp, @RequestParam("email") String email) {

        Map<String, String> mp = new HashMap<>();
        
        Long otpFromClient = Long.valueOf(otp);
        String emailFromClient = String.valueOf(email);


        System.out.println("my otp is :" + otpFromClient + " email is :" + emailFromClient);
        

        if (!emailFromClient.isEmpty()) {

            UserEntityTemp userEntityTemp = this.tempentity.findByemail(emailFromClient);

            Long otpFromDb = userEntityTemp.getOtp();

//            System.out.println("otpformCLient" + otpFromClient);
//            System.out.println("otpformdb" + otpFromDb);


            if (otpFromClient.equals(otpFromDb)) {

                System.out.println("Otp Verified");


                UserEntity userEntity;
                userEntity = Repository.findByemail((String) userEntityTemp.getEmail_address());
                if (userEntity != null) {

                    // forget user
                    mp.put("otp", "verified");
                    mp.put("email" , userEntity.getEmail_address());

                } else {

                    // new user

                    UserEntity entity = new UserEntity();
                    entity.setEmail_address(userEntityTemp.getEmail_address());
                    entity.setUsername(userEntityTemp.getUsername());
                    entity.setPassword(bcyrtp.encode(userEntityTemp.getPassword()));
                    entity.setDateandtime(userEntityTemp.getDateandtime());
                    entity.setFirstname(userEntityTemp.getFirstname());
                    Repository.save(entity);

                    mp.put("email" , entity.getEmail_address());
                    mp.put("text", "User Saved Successfully");
                }
                this.tempentity.deletebyemail(emailFromClient);
                mp.put("success", "true");

            } else {
                mp.put("text", "Incorrect otp");
                mp.put("response", "400 Bad Request");
                mp.put("success", "false");
            }
        } else {
            mp.put("success", "false");
            mp.put("message", "fields cannot be empty");
            return mp;
        }
        return mp;
    }


    @PostMapping("/reset_password")
    public Map<String, String> changePasswordNew(@RequestParam("password") String password, @RequestParam("email") String email) {
        Map<String, String> mp = new HashMap<>();

        if (!password.isEmpty() && !email.isEmpty()) {

            try {

                UserEntity userEntity = Repository.findByemail(email);
                userEntity.setPassword(bcyrtp.encode(password));
                Repository.save(userEntity);
//                System.out.println(userEntity);
//                System.out.println("User Password Changed!!");
                if(tempentity.findByemail(email) != null){
					this.tempentity.deletebyemail(email);
				}

                mp.put("message", "User password changed successfully");
                mp.put("success", "true");

            } catch (Exception e) {
                mp.put("message", "exception " + e.getLocalizedMessage());
                mp.put("success", "false");
            }

        } else {
            mp.put("message", "field cannot be empty");
            mp.put("success", "false");
        }

        return mp;
    }

}