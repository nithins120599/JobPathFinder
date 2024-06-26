package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Company;
import com.example.demo.entity.JobApply;
import com.example.demo.entity.JobSeekers;
import com.example.demo.entity.Questions;
import com.example.demo.entity.SelectedCandidates;
import com.example.demo.entity.Test;
import com.example.demo.entity.Vacancy;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("jobseeker")
public class JobApplyController {
	@GetMapping("applyJob")
	public ModelAndView addQuestions(Model model, HttpSession session, @RequestParam("vacancyId") int vacancyId) {
		
		
		
		JobApply jobApply = new JobApply();
		
	    Vacancy vacancy1= new Vacancy();
	    vacancy1.setVacancyId(vacancyId);
	    
	    jobApply.setVacancy(vacancy1);
	    
	   
	    model.addAttribute("jobApply", jobApply);

	    // Assuming you need JobSeekers information for some purpose
	    JobSeekers jobSeekers1 =(JobSeekers) session.getAttribute("jobSeekers");
		
		int userId = jobSeekers1.getUserId();
		model.addAttribute("userId", userId);
		System.out.println("userId" +userId);

	    // Adding testId and vacancyId to the model
	  
	    model.addAttribute("vacancyId", vacancyId);

	    ModelAndView view = new ModelAndView("applyJob");
	    return view;
	}
	
	@PostMapping("/saveApplyJob")
	public ModelAndView saveQuestions(@ModelAttribute("jobApply") JobApply jobApply, Model model,HttpSession session) {
		
		String status = "OnProgress";
		jobApply.setStatus(status);
		
		jobApply.setFinalScore("0");
		
		
		 JobSeekers jobSeekers1 =new JobSeekers();
			int userId = ((JobSeekers)session.getAttribute("jobSeekers")).getUserId();
			model.addAttribute("userId", userId);
		jobSeekers1.setUserId(userId);
		
		System.out.println("userId here.."+userId);
		
		//jobApply.setJobseeker(jobSeekers1);
		jobApply.setJobseeker(jobSeekers1);
		
		Vacancy vacancy1= jobApply.getVacancy();
		 System.out.println("vacancy here..=" +vacancy1); 
		System.out.println("JobApply: " + jobApply);
		
		 
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity<JobApply> entity = new HttpEntity<JobApply>(jobApply, headers);
	    
	    RestTemplate restTemplate = new RestTemplate();
	    String url = "http://localhost:8091/JobPathFinder/api/v1/addJobApply";
	    
	    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	  
	    String responseString = response.getBody();
	    System.out.println("objj =" +responseString);
	    
	    // Clearing the question object after submission
	    jobApply= new JobApply();
	    model.addAttribute("jobApply",jobApply);
	    
	    ModelAndView view = new ModelAndView("applyJob");
	    model.addAttribute("responseString", responseString);

	    return view;
	}
	
	
	
	
	
	/*
	 * (M codee)
	//////////////////////
	@GetMapping("/addJobApplay/{vacancyId}")
	public ModelAndView addJobApplay(@PathVariable("vacancyId") int vacancyId,Model model, HttpSession session) {
		
		JobSekers job1 = (JobSekers) session.getAttribute("jobseeker");
		int userId=job1.getUserId();
		
		JobApplay jobapplay = new JobApplay();
		//jobapplay.getVacancy().setVacancyId(vacancyId);
		model.addAttribute("vacancyId", vacancyId);
		model.addAttribute("userId", userId);
		model.addAttribute("jobapplay", jobapplay);
		ModelAndView view = new ModelAndView("jobseeker/addJobApplay");
		return view;
	}
	
	@PostMapping("/addingJobApplay")
	public String postMethodName(@ModelAttribute("jobapplay") JobApplay jobapplay, Model model) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<JobApplay> entity = new HttpEntity<JobApplay>(jobapplay, headers);

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8091/JobPathFinder/api/v1/addJobApplay";
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		String responseString = response.getBody();
		System.out.println("responseString" + responseString);

		jobapplay = new JobApplay();

		model.addAttribute("jobapplay", jobapplay);

		model.addAttribute("responseString", responseString);
		model.addAttribute("job", "*ThankYou for Applaying");
		return  "redirect:/jobseeker/jobHome";
	}
	

	
	*/
	
	
	
	
	///////////////////////////////////////////////////////////////////////
	
	  @GetMapping("/viewJobApplyDetails")
	  public ModelAndView viewJobApplyDetails(Model model,HttpSession session){
		  
	  
		 JobSeekers jobs = (JobSeekers) session.getAttribute("jobSeekers");
		 System.out.println("Jobs==" +jobs);
		  int userId = jobs.getUserId();

			 RestTemplate restTemplate = new RestTemplate();
			 ResponseEntity<JobApply[]> responseEntity = restTemplate
						.getForEntity("http://localhost:8091/JobPathFinder/api/v1/findByUserIdInJobApply/"+userId, JobApply[].class);

			 JobApply[] responseBody =  responseEntity.getBody(); 
			 List<JobApply> jobApplyList = Arrays.asList(responseBody);
			 
			System.out.println("jobApplyList "+jobApplyList.size());  
			 model.addAttribute("jobAppliersList",jobApplyList);
			 
			 //////////////////////////////////////////////////////////////////////////////
			 ///////////////////////////
			 for(JobApply jobapply :jobApplyList) {
				 Company company = jobapply.getVacancy().getCompany();
				 String imageUrl ="http://localhost:8091/JobPathFinder/api/files/download/" +company.getLogo();
			 }
			ModelAndView view = new ModelAndView("viewJobApply");
			return view;
		}
	  
	  @GetMapping("/viewResultsinJobSeekers")
	  public ModelAndView viewResultsinJobSeekers(Model model,HttpSession session){
		  JobSeekers jobs = (JobSeekers) session.getAttribute("jobSeekers");
			 System.out.println("Jobs==" +jobs);
			  int userId = jobs.getUserId();

			 RestTemplate restTemplate = new RestTemplate();
			 ResponseEntity<JobApply[]> responseEntity = restTemplate
						.getForEntity("http://localhost:8091/JobPathFinder/api/v1/findByUserIdsInJobApply/"+userId, JobApply[].class);

			 JobApply[] responseBody =  responseEntity.getBody(); 
			 List<JobApply> jobAppList = Arrays.asList(responseBody);
			 
			System.out.println("jobAppList "+jobAppList.size());  
			 model.addAttribute("jobAppList",jobAppList);
			 
			 
			ModelAndView view = new ModelAndView("jobSeekerResults");
			return view;
		}

}
