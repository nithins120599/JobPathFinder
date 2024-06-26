package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Company;
import com.example.demo.entity.JobApply;
import com.example.demo.entity.Questions;
import com.example.demo.entity.Vacancy;
import com.example.demo.service.JobApplyService;

@RestController
@RequestMapping("/api/v1")
public class JobApplyController {
	@Autowired
	JobApplyService jobApplyService;
	
	@PostMapping(value="/addJobApply")
	public ResponseEntity<Object> addJobApply(@RequestBody JobApply jobApply) {
		jobApplyService.addJobApply(jobApply);
		return new ResponseEntity<>("jobApply added Successfully",HttpStatus.CREATED);
	}
	
	
	@GetMapping(value="/findByUserIdInJobApply/{userId}")
	public ResponseEntity<Object> findByUserIdInJobApply(@PathVariable("userId") int userId){
		List<JobApply> vacancy = jobApplyService.findByJobseekerUserId(userId);
		ResponseEntity<Object> entity = new ResponseEntity<>(vacancy,HttpStatus.OK);
		
		return entity;
		
	}
	
	
	@PutMapping("/updateStatus/{userId}/{vacancyId}/{status}/{finalScore}")
	public  ResponseEntity<String> updateStatusByUserId(@PathVariable("userId") int userId,@PathVariable("vacancyId") int vacancyId,@PathVariable("status") String status,@PathVariable("finalScore") String finalScore)
	{
		jobApplyService.updateStatusAndFinalScoreByUserIdAndVacancyId(userId, vacancyId, status, finalScore);
		
		return ResponseEntity.ok("Status updated Succesfully" );
	}
	
	@GetMapping(value="/findByCompanyIdInJobApply/{companyId}")
	public ResponseEntity<Object> findByCompanyIdInJobApply(@PathVariable("companyId") int companyId){
		List<JobApply> job = jobApplyService.findJobApplyByCompanyId(companyId);
		ResponseEntity<Object> entity = new ResponseEntity<>(job,HttpStatus.OK);
		return entity;
		
	}
	
	@GetMapping("/getJobApply/{applyId}")
	public ResponseEntity<Object> getJobApply(@PathVariable("applyId") int applyId){
		JobApply jobApply;
	
		jobApply=jobApplyService.getJobApplyById(applyId);
	
	ResponseEntity<Object> entity=new ResponseEntity<>(jobApply,HttpStatus.OK);
	return entity;
	}
	
	
	@GetMapping("/allJobAppliers")
	public ResponseEntity<Object> getAllJobAppliers(){
		    List<JobApply> jobb =  jobApplyService.getAllJobApplies();
	ResponseEntity<Object> entity=new ResponseEntity<>(jobb,HttpStatus.OK);

	return entity;
	}
	
	
	@GetMapping("/check/{vacancyId}/{userId}")
    public ResponseEntity<Boolean> checkJobApplication(@PathVariable int vacancyId, @PathVariable int userId) {
        boolean exists = jobApplyService.existsByVacancyIdAndUserId(vacancyId, userId);
        return ResponseEntity.ok(exists);
    }
	
	@GetMapping(value="/findByUserIdsInJobApply/{userId}")
	public ResponseEntity<Object> findByUserIdsInJobApply1(@PathVariable("userId") int userId){
		List<JobApply> jobb = jobApplyService.findByUserId(userId);
		ResponseEntity<Object> entity = new ResponseEntity<>(jobb,HttpStatus.OK);
		return entity;
		
	}
}
