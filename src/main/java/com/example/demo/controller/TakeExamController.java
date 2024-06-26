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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.JobApply;
import com.example.demo.entity.JobSeekers;
import com.example.demo.entity.Questions;
import com.example.demo.entity.TestResult;
import com.example.demo.entity.Vacancy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("jobseeker")
public class TakeExamController {

	////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////take exam button code from History navbar 
	////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("takeExam/{vacancyId}")
	public ModelAndView takeExam(Model model, @PathVariable("vacancyId") int vacancyId,HttpSession session) {
		Questions question = new Questions();
		System.out.println("oooo vacancyId" + vacancyId);
		Vacancy vacancy1 = new Vacancy();
		vacancy1.setVacancyId(vacancyId);

		question.setVacancy(vacancy1);

		model.addAttribute("question", question);
		System.out.println("oooo " + question);

		//Adding testId and vacancyId to the model
		//model.addAttribute("testId", testId);
		model.addAttribute("vacancyId", vacancyId);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Questions[]> responseEntity = restTemplate
				.getForEntity("http://localhost:8091/JobPathFinder/api/v1/getAllQuestions/" + vacancyId, Questions[].class);

		Questions[] responseBody = responseEntity.getBody();
		List<Questions> quesList = Arrays.asList(responseBody);
		System.out.println("quesList" + quesList);
		System.out.println("quesList" + quesList.size());
		
		 // Set questionsList attribute in session
	    session.setAttribute("questionsList", quesList);
	   
		
		model.addAttribute("quesList", quesList);
		ModelAndView view = new ModelAndView("takeExam");
		return view;
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	////////////////////after selecting options and clicking on submit exam for that the code 
	/////////////////////////////////////////////////////////////////////////////////////////////

	@PostMapping("/submitExam")
	public String submitExam(HttpServletRequest request, HttpSession session) {
///////////////////////////////////////////////////////////////////////////////
		JobSeekers jobSeeker = (JobSeekers) session.getAttribute("jobSeekers");
		int userId = jobSeeker.getUserId();
		System.err.println("User ID: " + userId);

		////////////////////////////////////////////////////////////////////////////////
		List<Questions> questionsList = (List<Questions>) session.getAttribute("questionsList");
		int totalScore = 0;

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		for (Questions quest : questionsList) {
			int questId = quest.getQuestionId();
			////////////////////////////////////////////////////////////////////////
			Vacancy vacancy = quest.getVacancy();
			/////////////////////////////////////
			System.err.println("vacancyId ===" + vacancy.getVacancyId());

			String selectedOption = request.getParameter("selectedOptions" + questId);
			String correctOption = quest.getCorrect();
			System.out.println("questId: " + questId + ", selectedOptions: " + selectedOption + ", correctOption: "
					+ correctOption);

			String score = "0";
			String result = "incorrect";

			if (selectedOption != null && selectedOption.equals(correctOption)) {
				result = "correct";
				score = quest.getScore();
				totalScore += Integer.parseInt(quest.getScore());

				// Add score to totalScore

			}

			TestResult testResult = new TestResult();
			testResult.setJobSeekers(jobSeeker);
			testResult.setQuestions(quest);
			testResult.setVacancy(vacancy);
			testResult.setSelectedOption(selectedOption);
			testResult.setResult(result);

			// Assuming you calculate score somewhere else and set it here
			testResult.setScore(score);
			////////////////////////////////////////////////////////////////////////////////////
			// Save the test result
			// setting test result object to end point
			HttpEntity<TestResult> entity = new HttpEntity<TestResult>(testResult, headers);

			String url = "http://localhost:8091/JobPathFinder/api/v1/addTestResult";
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

			String responseString = response.getBody();
			System.out.println("responseString" + responseString);

			/////////////////////////////////////////////////////////////////

			String status;
			if (totalScore >= 75) {
				status = "qualified";
			} else {
				status = "better luck next time";
			}

			System.out.println("totalscore" + totalScore);

			JobApply jobapply = new JobApply();
///
			jobapply.setStatus(String.valueOf(totalScore));

			HttpEntity<JobApply> entity1 = new HttpEntity<JobApply>(jobapply, headers);

			String url1 = "http://localhost:8091/JobPathFinder/api/v1/updateStatus/" + userId + "/" + vacancy.getVacancyId() + "/"
					+ status + "/" + totalScore;
			ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.PUT, entity, String.class);

			String responseString1 = response1.getBody();
			System.out.println("responseString" + responseString1);

		}

		return "redirect:/jobseeker/jobseekerHome";
	}

}
