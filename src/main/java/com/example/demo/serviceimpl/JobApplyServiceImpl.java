package com.example.demo.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.JobApply;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.JobApplyRepository;
import com.example.demo.service.JobApplyService;

import jakarta.transaction.Transactional;
@Service
public class JobApplyServiceImpl implements JobApplyService{
	@Autowired
	JobApplyRepository jobApplyRepository;
	@Override
	public void addJobApply(JobApply jobApply) {
		jobApplyRepository.save(jobApply);
	}

	@Override
	public List<JobApply> getAllJobApplies() {
		// TODO Auto-generated method stub
		 return jobApplyRepository.findAll();
		
	}

	@Override
	public boolean isJobApplyExist(int applyId) {
		jobApplyRepository.existsById((int) applyId);
		return false;
	}

	@Override
	public JobApply getJobApplyById(int applyId) {
		Optional<JobApply> jobApply = jobApplyRepository.findById((int) applyId);
        if (jobApply.isPresent()) {
            return jobApply.get();
        } else {
            throw new ResourceNotFoundException("JobApply", "applyId", applyId);
        }
	}

	@Override
	public boolean updateJobApply(JobApply jobApply) {
		if (isJobApplyExist(jobApply.getApplyId())) {
            jobApplyRepository.save(jobApply);
            return true;
        }
        return false;
	}

	@Override
	public boolean deleteJobApply(int applyId) {
		 if (isJobApplyExist(applyId)) {
	            jobApplyRepository.deleteById((int) applyId);
	            return true;
	        }
	        return false;
	}

	@Override
	public List<JobApply> findByJobseekerUserId(int userId) {
		List<JobApply> list=jobApplyRepository.findByJobseekerUserId(userId);
		return list; 
	}

	@Override
	@Transactional
	public void updateStatusAndFinalScoreByUserIdAndVacancyId(int userId, int vacancyId, String status,
			String finalScore) {
		
		jobApplyRepository.updateStatusAndFinalScoreByUserIdAndVacancyId(userId, vacancyId, status, finalScore);
		
		
	}

	@Override
	public List<JobApply> findJobApplyByCompanyId(int companyId) {
		List<JobApply> list=jobApplyRepository.findByCompanyId(companyId);
		return list; 
	}

	//////////////////////////////////////////////////////////////////
	@Override
	public boolean existsByVacancyIdAndUserId(int vacancyId, int userId) {
	    List<Integer> applyIds = jobApplyRepository.findApplyIdByVacancyIdAndUserId(vacancyId, userId);
	    if (!applyIds.isEmpty()) {
	        return true; // User has applied for this vacancy
	    } else {
	        return false; // User has not applied for this vacancy
	    }
	}

	@Override
	public List<JobApply> findByUserId(int userId) {
		List<JobApply> listt=jobApplyRepository.findByUserId(userId);
		return listt;
	}
	
}
