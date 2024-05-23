package com.example.demo.service;

import com.example.demo.dto.User;
import com.example.demo.dto.UserSavedResponse;

public interface UserService {
	
	public UserSavedResponse saveUser(User user, String token);

}
