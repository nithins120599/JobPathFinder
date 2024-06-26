package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Company;
import com.example.demo.entity.JobSeekers;

public interface JobSeekersService {
	void addJobSeekers(JobSeekers jobseekers);
	List<JobSeekers> getAllJobSeekers();
	boolean isJobSeekersExist(int userId);
	JobSeekers getJobSeekersById(int userId);
	boolean deleteJobSeekers(int userId);
	boolean updateJobSeekers(JobSeekers JobSeekers);
	
	public JobSeekers loginvalidate(String email);
}
