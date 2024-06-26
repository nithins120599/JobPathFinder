package com.example.demo.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.AdminService;
@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	AdminRepository adminRepository;
	@Override
	public void addAdmin(Admin admin) {
		adminRepository.save(admin);
		
	}

	@Override
	public boolean loginvalidate(Admin admin) {
		Admin admin1 =adminRepository.findByUsernameAndPassword(admin.getUsername(),admin.getPassword());
		
		System.out.println("ADMIN1 ="+admin1);
		
		if(admin1 == null)
		return false;
		else
			return true;
	}

}
