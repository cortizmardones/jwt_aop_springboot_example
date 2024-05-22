package com.example.demo.service;

import java.util.concurrent.ExecutionException;

import com.example.demo.dto.User;

public interface UserService {
	
	public void saveUser(User user, String token) throws InterruptedException, ExecutionException;

}
