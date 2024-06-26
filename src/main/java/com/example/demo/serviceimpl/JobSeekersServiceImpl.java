package com.example.demo.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Company;
import com.example.demo.entity.JobSeekers;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.JobSeekersRepository;
import com.example.demo.service.CompanyService;
import com.example.demo.service.JobSeekersService;
@Service
public class JobSeekersServiceImpl implements JobSeekersService{
	
	
	@Autowired
	JobSeekersRepository jobSeekersRepository;
	
	@Override
	public void addJobSeekers(JobSeekers jobseekers) {
		jobSeekersRepository.save(jobseekers);
		
	}

	@Override
	public List<JobSeekers> getAllJobSeekers() {
		List<JobSeekers> jobsekerlist=jobSeekersRepository.findAll();
		return jobsekerlist;
	}
	
	


	@Override
	public boolean isJobSeekersExist(int userId) {
		Optional<JobSeekers> jobsekers = jobSeekersRepository.findById(userId);
		if(jobsekers.isPresent()) {
			return true;
		}else {
			return false;
		}
	}
	
	


	@Override
	public JobSeekers getJobSeekersById(int userId) {
		Optional<JobSeekers> jobsekers = jobSeekersRepository.findById(userId);
		JobSeekers job;
		if(jobsekers.isPresent()) {
			job=jobsekers.get();
		}else {
			throw new ResourceNotFoundException("JobSekers","userId", userId);
		}
		return job;
		
	}
	
	
	


	@Override
	public boolean deleteJobSeekers(int userId) {
		Optional<JobSeekers> jobsekers = jobSeekersRepository.findById(userId);
		if(jobsekers.isPresent()) {
			jobSeekersRepository.deleteById(userId);
			return true;
		}else {
			return false;
		}
	}
	
	

	@Override
	public boolean updateJobSeekers(JobSeekers jobSeekers) {
		Optional<JobSeekers> jobseker1 = jobSeekersRepository.findById(jobSeekers.getUserId());
		if(jobseker1.isPresent()) {
			jobSeekersRepository.save(jobSeekers);
				return true;
			
			
		}else {
			return false;
		}
		
	}

	/*
	public boolean loginvalidate(JobSeekers jobSeekers) {
		JobSeekers jobSeekers1 =jobSeekersRepository.findByEmailAndPassword(jobSeekers.getEmail(),jobSeekers.getPassword());
		System.out.println("jobSeekers1 ="+jobSeekers1);

		if(jobSeekers1 == null)
		return false;
		else
			return true;
	}
	
	*/

	@Override
	public JobSeekers loginvalidate(String email) {
		JobSeekers jobSeekers1=jobSeekersRepository.findByEmail(email);
		return jobSeekers1;
	}
	
	
	
	}

	
	
	

