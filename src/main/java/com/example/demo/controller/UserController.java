package com.example.demo.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/v1/user")
public class UserController {
	
	@Autowired 
	UserService userService;
	
	@PostMapping("/saveUser")
	public void saveUser(@RequestBody User user, @RequestParam String token) throws InterruptedException, ExecutionException {
		userService.saveUser(user, token);
	}

}