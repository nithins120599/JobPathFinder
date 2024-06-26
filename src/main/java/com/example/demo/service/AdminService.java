package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;

@Service
public interface AdminService {
	void addAdmin(Admin admin);
	
	public boolean loginvalidate(Admin admin);
}
