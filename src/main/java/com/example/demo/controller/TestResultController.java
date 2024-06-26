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
import com.example.demo.entity.TestResult;
import com.example.demo.service.TestResultService;

@RestController
@RequestMapping("/api/v1")
public class TestResultController {
	@Autowired
	TestResultService testResultService;
	
	@PostMapping(value="/addTestResult")
	public ResponseEntity<Object> addTestResult(@RequestBody TestResult testRe) {
		testResultService.addTestResult(testRe);
		return new ResponseEntity<>("TestResult added Successfully",HttpStatus.CREATED);
	}
	
	@GetMapping("/allTestResults")
	public ResponseEntity<Object> getAllallTestResults(){
		    List<TestResult> test =  testResultService.getAllTestResults();
	ResponseEntity<Object> entity=new ResponseEntity<>(test,HttpStatus.OK);

	return entity;
	}
	
	
	@GetMapping("/getTestResult/{resultId}")
	public ResponseEntity<Object> getTestResult(@PathVariable("resultId") int resultId){
		TestResult tr;
	if(testResultService.isTestResultExist(resultId)) {
		tr=testResultService.getTestResultById(resultId);
			
		}else {
			tr=null;
		}
	ResponseEntity<Object> entity=new ResponseEntity<>(tr,HttpStatus.OK);
	return entity;
	}
	
	
	
	
	@PutMapping(value="/updateTestResult/{resultId}")
	public ResponseEntity<Object> updateTestResult(@PathVariable("resultId") int resultId, @RequestBody TestResult testresult)
	{
		
		boolean flag;
		if(testResultService.isTestResultExist(resultId)){
			flag = testResultService.updateTestResult(testresult); 
		}else {
			flag = false;
		}
		
		return new ResponseEntity<>(flag, HttpStatus.OK);
		}

}
