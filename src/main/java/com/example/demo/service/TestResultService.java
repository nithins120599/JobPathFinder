package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.TestResult;
import com.example.demo.entity.Vacancy;

public interface TestResultService {
	void addTestResult(TestResult testResult);
	List<TestResult> getAllTestResults();

	boolean isTestResultExist(int resultId);
	TestResult getTestResultById(int resultId);
	
	boolean deleteTestResult(int resultId);
	boolean updateTestResult(TestResult testResult);
	
}
