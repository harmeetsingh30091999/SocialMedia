package com.Social11.service;

import org.springframework.stereotype.Service;

@Service
public interface FCMTokenService {

	public void saveFcmToken(String fcm, Integer userid);
	
	public boolean isFCMTokenExist(String fcm, Integer userid);
}
