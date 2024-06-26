package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.JobApply;
import com.example.demo.entity.Test;
import com.example.demo.entity.Vacancy;
import com.example.demo.service.JobApplyService;
import com.example.demo.service.TestService;
import com.example.demo.service.VacancyService;

@RestController
@RequestMapping("/api/v1")
public class TestController {
	@Autowired
	TestService testService;

	@PostMapping(value = "/addTest")
	public ResponseEntity<Object> addVacancy(@RequestBody Test test) {
		testService.addTest(test);
		return new ResponseEntity<>("Test added Successfully", HttpStatus.CREATED);
	}

	@GetMapping(value = "/allTests")
	public ResponseEntity<Object> getAllTests() {
		List<Test> test = testService.getAllTests();
		ResponseEntity<Object> entity = new ResponseEntity<>(test, HttpStatus.OK);
		return entity;
	}

	@GetMapping(value = "/getTest/{testId}")
	public ResponseEntity<Object> getTest(@PathVariable("testId") int testId) {
		Test test;

		if (testService.isTestExist(testId)) {
			test = testService.getTestById(testId);

		} else {
			test = null;
		}
		ResponseEntity<Object> entity = new ResponseEntity<>(test, HttpStatus.OK);
		return entity;

	}

	@DeleteMapping(value = "/deleteTest/{testId}")
	public ResponseEntity<Object> deleteTest(@PathVariable("testId") int testId) {

		boolean flag;
		if (testService.isTestExist(testId)) {
			flag = testService.deleteTest(testId);
		} else {
			flag = false;
		}

		return new ResponseEntity<>(flag, HttpStatus.OK);

	}

	@PutMapping(value = "/updateTest/{testId}")
	public ResponseEntity<Object> updateVacancy(@PathVariable("testId") int testId, @RequestBody Test test) {

		boolean flag;
		if (testService.isTestExist(testId)) {
			flag = testService.updateTest(test);
		} else {
			flag = false;
		}

		return new ResponseEntity<>(flag, HttpStatus.OK);

	}

	@GetMapping(value = "/getTestt/{companyId}")
	public ResponseEntity<Object> getTestByCompanyId(@PathVariable("companyId") int companyId) {
		List<Test> tee = testService.findByCompanyId(companyId);
		ResponseEntity<Object> entity = new ResponseEntity<>(tee, HttpStatus.OK);
		return entity;
	}
	
	

	@GetMapping("/getTestByVacancyIdd/{vacancyId}")
	public ResponseEntity<Object> getTestByVacancyId(@PathVariable("vacancyId") int vacancyId){
		Test tes;
	
		tes=testService.getTestById(vacancyId);
	
	ResponseEntity<Object> entity=new ResponseEntity<>(tes,HttpStatus.OK);
	return entity;
	}
	
	
}
