package com.example.demo.service;

import java.util.concurrent.ExecutionException;

import com.example.demo.dto.User;
import com.example.demo.dto.UserSavedResponse;

public interface UserService {
	
	public UserSavedResponse saveUser(User user, String token) throws InterruptedException, ExecutionException;

}
