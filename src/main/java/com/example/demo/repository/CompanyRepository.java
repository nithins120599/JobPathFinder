package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer>{
	//public  Company findByEmailAndPassword(String email, String password);
	public  Company findByEmail(String email);
	
}
