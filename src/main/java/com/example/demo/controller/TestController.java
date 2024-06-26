package com.example.demo.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Company;
import com.example.demo.entity.Test;
import com.example.demo.entity.Vacancy;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("company")
public class TestController {

	 @GetMapping("/addTest")
		public ModelAndView AddTest(Model model,HttpSession session) {
			
			Test test = new Test();
			model.addAttribute("test", test);
				Company company = (Company) session.getAttribute("company");
			  int cid = company.getCompanyId();
			 RestTemplate restTemplate = new RestTemplate();
			 ResponseEntity<Object[]> responseEntity = restTemplate
						.getForEntity("http://localhost:8091/JobPathFinder/api/v1/vacIdandJobTitle/"+cid, Object[].class);
			 Object[] responseBody =  responseEntity.getBody(); 
			 List<Object> vacancyList = Arrays.asList(responseBody);
			System.out.println("vacancyList "+vacancyList.size());
			 
			 model.addAttribute("vacanciesList",vacancyList);
		
			ModelAndView view = new ModelAndView("addTest");
			return view;
		}
	 
	 @PostMapping("/saveTest")
	  public ModelAndView addingTest(@ModelAttribute("test") Test test, Model model) {
	      
	 HttpHeaders headers = new HttpHeaders();
     headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
     HttpEntity<Test> entity = new HttpEntity<Test>(test, headers);
     
     RestTemplate restTemplate = new RestTemplate();
     String url = "http://localhost:8091/JobPathFinder/api/v1/addTest";
     
     ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity,String.class);
     Object responseString = response.getBody();
     
     test = new Test();
     model.addAttribute("test", test);
     
     ModelAndView view = new ModelAndView("addTest");
     model.addAttribute("responseString", responseString);
     model.addAttribute("resp","Tests Added Successfully!!");
     return view;
 
}
	 @GetMapping("/viewAppliedTests")
	  public ModelAndView viewTests(Model model,HttpSession session)
		{
		  
		  Company company = (Company) session.getAttribute("company");
		  int cid = company.getCompanyId();

			 RestTemplate restTemplate = new RestTemplate();
			 ResponseEntity<Test[]> responseEntity = restTemplate
						.getForEntity("http://localhost:8091/JobPathFinder/api/v1/getTestt/"+cid, Test[].class);

			 Test[] responseBody =  responseEntity.getBody(); 
			 List< Test> testList = Arrays.asList(responseBody);
			 
			System.out.println("testList "+testList.size());
		    
			 model.addAttribute("testListt",testList);
			ModelAndView view = new ModelAndView("viewTest");
			return view;
		}
	 
	  @GetMapping("/editTest/{testId}")
		public ModelAndView getvacancy(@PathVariable("testId") int testId,Model model,HttpSession session) {
			
		  Company company = (Company) session.getAttribute("company");
		  int cid = company.getCompanyId();
			RestTemplate restTemplate = new RestTemplate();	
			ResponseEntity<Object[]> responseEntity = restTemplate
					.getForEntity("http://localhost:8091/JobPathFinder/api/v1/vacIdandJobTitle/"+cid, Object[].class);
		 Object[] responseBody =  responseEntity.getBody(); 
		 List<Object> vacancyList = Arrays.asList(responseBody);
		System.out.println("vacancyList "+vacancyList.size());
		 
		 model.addAttribute("vacanciesList",vacancyList);
			ResponseEntity<Test> responseEntity1 = restTemplate.getForEntity("http://localhost:8091/JobPathFinder/api/v1/getTest/" + testId,
					Test.class);
			Test tee = responseEntity1.getBody();
			System.out.println("JOBTITLE:" + tee.getTestId());
			model.addAttribute("testt", tee);
			ModelAndView view = new ModelAndView("editTest");
			return view;
		}
	
	  @PostMapping("/updatingTest")
		public String updatevacancy(@ModelAttribute("testt") Test test1, Model model) {
		
		
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<Test> entity = new HttpEntity<Test>(test1, headers);

			RestTemplate restTemplate = new RestTemplate();

			String url = "http://localhost:8091/JobPathFinder/api/v1/updateTest/"+test1.getTestId();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

			String responseString = response.getBody();
			System.out.println("responseString11" + responseString);

			return "redirect:/company/viewAppliedTests";		
		}
	  

	  @GetMapping("/deleteTest/{testId}")
		public String deletevacancy(@PathVariable("testId") int testId) {
		
			Map<String, Integer> pathVar = new HashMap<>();
			pathVar.put("testId", testId);
			
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.delete("http://localhost:8091/JobPathFinder/api/v1/deleteTest/{testId}",pathVar);
			//ModelAndView view = new ModelAndView("viewProducts");
			//return view;
			return "redirect:/company/viewAppliedTests";
			
		}
		
	 
}
