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
import com.example.demo.entity.Questions;
import com.example.demo.entity.Test;
import com.example.demo.entity.Vacancy;
import com.example.demo.service.JobSeekersService;
import com.example.demo.service.QuestionsService;

@RestController
@RequestMapping("/api/v1")
public class QuestionsController {
	@Autowired
	QuestionsService quesService;

	@PostMapping("/addQuestions")
	public ResponseEntity<Object> addQuestions(@RequestBody Questions questions) {
		quesService.addQuestions(questions);
		return new ResponseEntity<>("Questions added Successfully",HttpStatus.CREATED);
	}
	

	
	@GetMapping(value ="/allQuestionss")
	public ResponseEntity<Object> getAllQuestions() {
		List<Questions> questions = quesService.getAllQuestions();
		ResponseEntity<Object> entity = new ResponseEntity<>(questions, HttpStatus.OK);
		return entity;
	}
	
	
	@GetMapping(value = "/getQuestions/{questionId}")
	public ResponseEntity<Object> getTest(@PathVariable("questionId") int questionId) {
		Questions question;

		if (quesService.isQuestionsExist(questionId)) {
			question = quesService.getQuestionsById(questionId);

		} else {
			question= null;
		}
		ResponseEntity<Object> entity = new ResponseEntity<>(question, HttpStatus.OK);
		return entity;

	}
	
	@DeleteMapping(value = "/deleteQuestion/{questionId}")
	public ResponseEntity<Object> deleteQuestion(@PathVariable("questionId") int questionId) {

		boolean flag;
		if (quesService.isQuestionsExist(questionId)) {
			flag = quesService.deleteQuestionss(questionId);
		} else {
			flag = false;
		}

		return new ResponseEntity<>(flag, HttpStatus.OK);

	}
	
	
	@PutMapping(value = "/updateQuestions/{questionId}")
	public ResponseEntity<Object> updateQuestions(@PathVariable("questionId") int questionId, @RequestBody Questions questions) {

		boolean flag;
		if (quesService.isQuestionsExist(questionId)) {
			flag = quesService.updateQuestions(questions);
		} else {
			flag = false;
		}

		return new ResponseEntity<>(flag, HttpStatus.OK);

	}


	@GetMapping(value="/getQuestions/company/{companyId}")
	public ResponseEntity<Object> getQuestionsByCompanyId(@PathVariable("companyId") int companyId){
		List<Questions> ques = quesService.getQuestionsByCompanyId(companyId);
		ResponseEntity<Object> entity = new ResponseEntity<>(ques,HttpStatus.OK);
		return entity;
	}
	
	
	
	@GetMapping(value="/getAllQuestions/{vacancyId}")
	public ResponseEntity<Object> getAllQuestionsByVacancyId(@PathVariable("vacancyId") int vacancyId){
		List<Questions> ques = quesService.findByVacancy_VacancyId(vacancyId);
		ResponseEntity<Object> entity = new ResponseEntity<>(ques,HttpStatus.OK);
		return entity;
	}
	
	
}


