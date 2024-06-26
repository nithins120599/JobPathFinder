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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Company;
import com.example.demo.entity.JobApply;
import com.example.demo.entity.JobSeekers;
import com.example.demo.entity.Questions;
import com.example.demo.entity.Test;
import com.example.demo.entity.TestResult;
import com.example.demo.entity.Vacancy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("company")
public class QuestionsController {

	@GetMapping("addQuestions")
	public ModelAndView addQuestions(Model model, HttpSession session, @RequestParam("testId") int testId,
			@RequestParam("vacancyId") int vacancyId) {
		Questions question = new Questions();
		Test test1 = new Test();
		test1.setTestId(testId);
		question.setTest(test1);

		Vacancy vacancy1 = new Vacancy();
		vacancy1.setVacancyId(vacancyId);

		question.setVacancy(vacancy1);

		model.addAttribute("question", question);

		// Assuming you need company information for some purpose
		Company company = (Company) session.getAttribute("company");
		int companyId = company.getCompanyId();
		model.addAttribute("companyId", companyId);

		// Adding testId and vacancyId to the model
		model.addAttribute("testId", testId);
		model.addAttribute("vacancyId", vacancyId);

		ModelAndView view = new ModelAndView("addQuestions");
		return view;
	}

	@PostMapping("/saveQuestions")
	public ModelAndView saveQuestions(@ModelAttribute("question") Questions question, Model model) {

		System.out.println("Question: " + question);
		Test test1 = question.getTest();
		System.out.println("Test: " + test1);

		Vacancy vacancy1 = question.getVacancy();
		System.out.println("vacancy =" + vacancy1);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Questions> entity = new HttpEntity<Questions>(question, headers);

		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8091/JobPathFinder/api/v1/addQuestions";

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		String responseString = response.getBody();
		System.out.println("objj =" + responseString);
		// Clearing the question object after submission
		question = new Questions();
		model.addAttribute("question", question);

		ModelAndView view = new ModelAndView("addQuestions");
		model.addAttribute("responseString", responseString);
	     model.addAttribute("resp","Questions Added Successfully!!");

		return view;
	}

	@GetMapping("/viewQuestions")
	public ModelAndView viewTests(Model model, HttpSession session) {

		Company company = (Company) session.getAttribute("company");
		int cid = company.getCompanyId();

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Questions[]> responseEntity = restTemplate
				.getForEntity("http://localhost:8091/JobPathFinder/api/v1/getQuestions/company/" + cid, Questions[].class);

		Questions[] responseBody = responseEntity.getBody();
		List<Questions> quesList = Arrays.asList(responseBody);

		System.out.println("quesList" + quesList.size());

		model.addAttribute("quesListt", quesList);
		ModelAndView view = new ModelAndView("viewQuestions");
		return view;
	}
	
	
	 @GetMapping("/editQuestions/{questionId}")
		public ModelAndView getvacancy(@PathVariable("questionId") int questionId,Model model,HttpSession session) {
			Company company = (Company) session.getAttribute("company");
			int cid = company.getCompanyId();
			System.out.println("cid ?? ==" +cid);
			
			RestTemplate restTemplate = new RestTemplate();	
			ResponseEntity<Questions> responseEntity = restTemplate.getForEntity("http://localhost:8091/JobPathFinder/api/v1/getQuestions/" + questionId,
					Questions.class);
			Questions quee = responseEntity.getBody();
			System.out.println("Questions is  :" + quee.getQuestion());
			model.addAttribute("questionn", quee);
			ModelAndView view = new ModelAndView("editQuestionss");
			return view;
		}
	
	 @PostMapping("/updateQuestions")
		public String updateQuestions(@ModelAttribute("questionn") Questions q, Model model) {
			
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<Questions> entity = new HttpEntity<Questions>(q, headers);

			RestTemplate restTemplate = new RestTemplate();

			String url = "http://localhost:8091/JobPathFinder/api/v1/updateQuestions/"+q.getQuestionId();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

			String responseString = response.getBody();
			System.out.println("responseStringQ" + responseString);

			return "redirect:/company/viewQuestions";		
		}
	 
	 @GetMapping("/deleteQuestions/{questionId}")
		public String deletevacancy(@PathVariable("questionId") int questionId) {
		
			Map<String, Integer> pathVar = new HashMap<>();
			pathVar.put("questionId", questionId);
			
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.delete("http://localhost:8091/JobPathFinder/api/v1/deleteQuestion/{questionId}",pathVar);
			//ModelAndView view = new ModelAndView("viewProducts");
			//return view;
			return "redirect:/company/viewQuestions";
			
		}
		
	
}


