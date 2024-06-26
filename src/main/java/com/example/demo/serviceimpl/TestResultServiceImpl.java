package com.example.demo.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TestResult;
import com.example.demo.entity.Vacancy;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TestResultRepository;
import com.example.demo.service.TestResultService;
@Service
public class TestResultServiceImpl implements TestResultService{
	@Autowired
	TestResultRepository testResultRepository;
	
	@Override
	public void addTestResult(TestResult testResult) {
		testResultRepository.save(testResult);

		
	}

	@Override
	public List<TestResult> getAllTestResults() {
		List<TestResult> testList = testResultRepository.findAll();
		return testList;
	}
	
	

	@Override
	public boolean isTestResultExist(int resultId) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean deleteTestResult(int resultId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateTestResult(TestResult testResult) {
		Optional<TestResult> test1 =testResultRepository.findById(testResult.getResultId());
		if(test1.isPresent()) {
			testResultRepository.save(testResult);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public TestResult getTestResultById(int resultId) {
		Optional<TestResult> testResult =testResultRepository.findById(resultId);
		TestResult testres;
		if(testResult.isPresent()) {
			testres=testResult.get();
			
		}else {
			throw new ResourceNotFoundException("TestResult", "ResultId", resultId);
		}
		return testres;
	}
	
	
}
