package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Company;
import com.example.demo.entity.JobSeekers;

public interface JobSeekersRepository extends JpaRepository<JobSeekers, Integer>{
	public  JobSeekers findByEmail(String email);
}
