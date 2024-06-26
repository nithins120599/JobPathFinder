package com.example.demo.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Users;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UsersService;
@Service
public class UsersServiceImpl implements UsersService{
@Autowired
UserRepository userRepo;
	@Override
	public Users getUsersByEmail(String username) {
		Optional<Users> user1 = userRepo.findById(username);
		Users us;
		if(user1.isPresent()) {
			us = user1.get();
		}else {
			throw new ResourceNotFoundException("Users","email",username);
		}
		
		return us;
	}

	@Override
	public boolean updateUsers(Users user) {
		Optional<Users> user2 = userRepo.findById(user.getUsername());
		if(user2.isPresent()) {
			userRepo.save(user);
			return true;
		}else {
		return false;
		
	}

}
}
