package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Company;
import com.example.demo.entity.JobSeekers;
import com.example.demo.service.CompanyService;
import com.example.demo.service.JobSeekersService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1")
public class JobSeekersController {
	@Autowired
	JobSeekersService jobSeekersService;

	@PostMapping("/addJobseker")
	public ResponseEntity<Object> addCompany(@RequestBody JobSeekers jobseekers) {
		jobSeekersService.addJobSeekers(jobseekers);
		return new ResponseEntity<>("JobSeker Added Welcome To Hire Me Now", HttpStatus.CREATED);

	}

	@GetMapping("/allJobseekers")
	public ResponseEntity<Object> getAllJobsekers() {
		List<JobSeekers> jobsekers = jobSeekersService.getAllJobSeekers();
		ResponseEntity<Object> entity = new ResponseEntity<>(jobsekers, HttpStatus.OK);
		return entity;
	}
	

	
	@GetMapping(value="/getJobseekers/{userId}")
	public ResponseEntity<Object> getJobseker(@PathVariable("userId") int userId){
		JobSeekers jobseeker;
		
		if(jobSeekersService.isJobSeekersExist(userId)) {
			jobseeker=jobSeekersService.getJobSeekersById(userId);
			
		}else {
			jobseeker = null;
		}
		ResponseEntity<Object> entity = new ResponseEntity<>(jobseeker,HttpStatus.OK);
		return entity;
		
	}
	
	@DeleteMapping(value="/deleteJobseeker/{userId}")
	public ResponseEntity<Object> deleteJobSeekers(@PathVariable("userId") int userId){
		
		boolean flag;
		if(jobSeekersService.isJobSeekersExist(userId)) {
			flag = jobSeekersService.deleteJobSeekers(userId);
		}else {
			flag = false;
		}
		
		return new ResponseEntity<>(flag,HttpStatus.OK);
		
	}


	@PutMapping(value="/updateJobseeker/{userId}")
	public ResponseEntity<Object> updateVacancy(@PathVariable("userId") int userId, @RequestBody JobSeekers jobseeker)
	{
		
		boolean flag;
		if(jobSeekersService.isJobSeekersExist(userId)) {
			flag = jobSeekersService.updateJobSeekers(jobseeker);
		}else {
			flag = false;
		}
		
		return new ResponseEntity<>(flag, HttpStatus.OK);
		
	}
	
	/*
	
	@PostMapping("/jobseekersLogin")
	public ResponseEntity<Object> jobseekerLogin(@RequestBody JobSeekers jobseekers, HttpSession session) {
	    boolean flag = jobSeekersService.loginvalidate(jobseekers);
	    
	    if (flag) {
	        // Assuming successful login, you can store some information in the session
	        session.setAttribute("loggedInJobAccount", jobseekers); // Store the logged-in company in the session
	    }
	    
	    return new ResponseEntity<>(flag, HttpStatus.OK);
	}
	*/
	
	@GetMapping("jobSeekersLogin/{email}")
	public ResponseEntity<Object> jobSeekersLogin(@PathVariable("email") String  email) {
		JobSeekers jobSeekers1= jobSeekersService.loginvalidate(email);
	     
		    return new ResponseEntity<>(jobSeekers1, HttpStatus.OK);
		}
	
	}
	


