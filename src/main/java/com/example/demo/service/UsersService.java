package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Users;

@Service
public interface UsersService {
	Users getUsersByEmail(String username);
	boolean updateUsers(Users user);

}
