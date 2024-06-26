package com.example.demo.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.JobSeekers;
import com.example.demo.entity.Questions;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.service.QuestionsService;
@Service
public class QuestionsServiceImpl implements QuestionsService{

	@Autowired 
	QuestionsRepository  questionsRepo;
	@Override
	public void addQuestions(Questions questions) {
		questionsRepo.save(questions);
		
	}

	@Override
	public List<Questions> getAllQuestions() {
		List<Questions> questionslist=questionsRepo.findAll();
		return questionslist;
	}
	
	@Override
	public boolean isQuestionsExist(int questionId) {
		Optional<Questions>  questions = questionsRepo.findById(questionId);
		if( questions.isPresent()) {
			return true;
		}else {
			return false;
		}
	}
	

	@Override
	public Questions getQuestionsById(int questionId) {
		Optional<Questions> ques = questionsRepo.findById(questionId);
		Questions questions;
		if(ques.isPresent()) {
			questions=ques.get();
		}else {
			
			throw new ResourceNotFoundException("Questions","questionId", questionId);
	}
		return questions;
	}
	
	
		

	@Override
	public boolean deleteQuestionss(int questionId) {
		Optional<Questions> ques = questionsRepo.findById(questionId);
		if(ques.isPresent()) {
			questionsRepo.deleteById(questionId);
			return true;
		}else {
		return false;
	}
	}
	
	

	@Override
	public boolean updateQuestions(Questions questions) {
		Optional<Questions> ques1 = questionsRepo.findById(questions.getQuestionId());
		if(ques1.isPresent()) {
			questionsRepo.save(questions);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<Questions> getQuestionsByCompanyId(int companyId) {
		List<Questions> questionsList = questionsRepo.findByCompanyId(companyId);
		return questionsList;
	}

	@Override
	public List<Questions> findByVacancy_VacancyId(int vacancyId) {
		List<Questions> questionsList = questionsRepo.findByVacancy_VacancyId(vacancyId);
		return questionsList;
	}
}

	
