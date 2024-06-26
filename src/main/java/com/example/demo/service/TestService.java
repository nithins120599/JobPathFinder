package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Company;
import com.example.demo.entity.Test;
import com.example.demo.entity.Vacancy;

public interface TestService {
	void addTest(Test test);
	List<Test> getAllTests();

	boolean isTestExist(int testId);
	Test getTestById(int testId);
	
	boolean deleteTest(int testId);
	boolean updateTest(Test test);
	
	 List<Test> findByCompanyId(int companyId);
	 
	 	Test findByVacancyId(int vacancyId);

	
}
