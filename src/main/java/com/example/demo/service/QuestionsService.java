package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.JobSeekers;
import com.example.demo.entity.Questions;
import com.example.demo.entity.Vacancy;

public interface QuestionsService {
	void addQuestions(Questions questions);
	List<Questions> getAllQuestions();
	boolean isQuestionsExist(int questionId);
	Questions getQuestionsById(int questionId);
	boolean deleteQuestionss(int questionId);
	boolean updateQuestions(Questions questions);
	
	
	List<Questions> getQuestionsByCompanyId(int companyId);
	
	 List<Questions> findByVacancy_VacancyId(int vacancyId);
}
