package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.User;
import com.example.demo.exception.InvalidTokenException;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private Firestore firebase;
	
	@Autowired
	private TokenServiceImpl tokenServiceImpl;

	@Override
	public void saveUser(User user, String token) throws InterruptedException, ExecutionException {
		
		boolean response = tokenServiceImpl.validToken(token);
		if(!response) throw new InvalidTokenException("Token no válido");
		
        Map<String, Object> docData = new HashMap<>();
        docData.put("rut", user.rut());
        docData.put("nombre", user.nombre());
        docData.put("apellido",user.apellido());
        docData.put("edad", user.edad());
        
        ApiFuture<WriteResult> responseFirebase = firebase.collection("users").document(user.rut()).set(docData);
        WriteResult result = responseFirebase.get();
        System.out.println("Document written at: " + result.getUpdateTime());
		
	}

}
