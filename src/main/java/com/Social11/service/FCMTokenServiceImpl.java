package com.Social11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Social11.Dao.FCMRepository;
import com.Social11.models.FCMModel;

@Service
public class FCMTokenServiceImpl implements FCMTokenService{

	
	@Autowired
	private FCMRepository FRepository;
	
	@Override
	public void saveFcmToken(String fcmtoken, Integer userid) {
		// TODO Auto-generated method stub
		FCMModel fcm = new FCMModel();
		fcm.setFcmToken(fcmtoken);
		fcm.setUserid(userid);
		try {
			this.FRepository.save(fcm);
		}
		catch(Exception e) {
			return;
		}
	}

	@Override
	public boolean isFCMTokenExist(String fcm, Integer userid) {
		// TODO Auto-generated method stub
		if(fcm!=null) {
			String Fcm = this.FRepository.isFCMPresent(userid);
			try {
				if(Fcm!=null && !(Fcm.isEmpty())) {
					this.FRepository.updateFCMToken(fcm, userid);
					return true;
				}
				else {
					this.saveFcmToken(fcm, userid);
				}
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
		return false;
	}

}
