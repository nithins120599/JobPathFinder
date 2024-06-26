package com.example.demo.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Test;
import com.example.demo.entity.Vacancy;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.TestRepository;
import com.example.demo.service.CompanyService;
import com.example.demo.service.TestService;

@Service
public class TestServiceImpl  implements TestService{
	@Autowired
	TestRepository testRepository;

	@Override
	public void addTest(Test test) {
		testRepository.save(test);
		
	}

	@Override
	public List<Test> getAllTests() {
		List<Test> testList = testRepository.findAll();
		return testList;
	}
	


	@Override
	public boolean isTestExist(int testId) {
		Optional<Test> test = testRepository.findById(testId);
		if(test.isPresent()) {
			return true;
	}
		return false;
	}
		
	

	@Override
	public Test getTestById(int testId) {
		Optional<Test> test =testRepository.findById(testId);
		Test t;
		if( test.isPresent()) {
			t= test.get();
		}else {
			throw new ResourceNotFoundException("Test", "TestId", testId);
		}
		return t;
	}
	
	
	
	
	

	@Override
	public boolean deleteTest(int testId) {
		Optional<Test> test =testRepository.findById(testId);
		if(test.isPresent()) {
			testRepository.deleteById(testId);
			return true;
		}
		else 
		{
		return false;
	}
		
	}
	
	

	@Override
	public boolean updateTest(Test test) {
		Optional<Test> test1 =testRepository.findById(test.getTestId());
		if(test1.isPresent()) {
			testRepository.save(test);
			return true;
		
	}else {
		return false;
	}

	}

	@Override
	public List<Test> findByCompanyId(int companyId) {
		List<Test> testList = testRepository.findByCompanyId(companyId);
		
		return testList;
	}

	@Override
	public Test findByVacancyId(int vacancyId) {
		Test test =testRepository.findByVacancyId(vacancyId);
		
		return test;
	}
	
}
	

	
	
	

