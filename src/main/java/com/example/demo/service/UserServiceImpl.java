package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.aspect.TrackingAnotation;
import com.example.demo.dto.User;
import com.example.demo.dto.UserSavedResponse;
import com.example.demo.dto.ValidTokenResponse;
import com.example.demo.exception.InvalidTokenException;
import com.example.demo.feignclient.TokenFeignClient;
import com.example.demo.feignclient.TokenFeignClient3;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

import static com.example.demo.utils.Constant.EMAIL_SYSTEM;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private Firestore firebase;
	
	@Autowired
	private TokenFeignClient tokenFeignClient;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	@TrackingAnotation
	public UserSavedResponse saveUser(User user, String token) {
		
		ValidTokenResponse validTokenResponse = tokenFeignClient.validToken(token);
		if(!validTokenResponse.isValid()) throw new InvalidTokenException(validTokenResponse.errorMessage());
		
        Map<String, Object> docData = new HashMap<>();
        docData.put("rut", user.rut());
        docData.put("name", user.name());
        docData.put("lastName",user.lastName());
        docData.put("age", user.age());
        docData.put("active", true);
        
        ApiFuture<WriteResult> responseFirebase = firebase.collection("users").document(user.rut()).set(docData);
        WriteResult writeResultFirebase;
		try {
			writeResultFirebase = responseFirebase.get();
			
			emailService.sendSimpleMessage(EMAIL_SYSTEM, "Guardado User Firebase-SpringBoot", "Se creo el siguiente registro en Firebase : " + docData.toString());
			
	        return UserSavedResponse.builder()
	        		.message("User stored in Firebase successfully.")
	        		.dateToOperation(writeResultFirebase.getUpdateTime().toString())
	        		.build();
		} catch (InterruptedException | ExecutionException e) {
	        return UserSavedResponse.builder()
	        		.message("There was a problem storing the user in firebase: ".concat(e.getMessage()))
	        		.dateToOperation("")
	        		.build();
		}
	}

}
