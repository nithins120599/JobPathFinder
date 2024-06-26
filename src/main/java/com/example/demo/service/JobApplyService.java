package com.example.demo.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.example.demo.entity.JobApply;

public interface JobApplyService {
	
	void addJobApply(JobApply jobApply);
    List<JobApply> getAllJobApplies();
    boolean isJobApplyExist(int applyId);
    JobApply getJobApplyById(int applyId);
    boolean updateJobApply(JobApply jobApply);
    boolean deleteJobApply(int applyId);
    
    
    List<JobApply> findByJobseekerUserId(int userId);

    
    void updateStatusAndFinalScoreByUserIdAndVacancyId(@Param("userId") int userId, @Param("vacancyId") int vacancyId, @Param("status") String status, @Param("finalScore") String finalScore);
    
    
    List<JobApply> findJobApplyByCompanyId(int companyId);
    
    
    boolean existsByVacancyIdAndUserId(int vacancyId, int userId);
    
    
    List<JobApply> findByUserId(int userId);
    
}
