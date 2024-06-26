package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Questions;
import com.example.demo.entity.Vacancy;

public interface QuestionsRepository extends JpaRepository<Questions,Integer>{

	 @Query(value = "SELECT * FROM questions WHERE vacancy_Id IN (SELECT vacancy_Id FROM vacancy WHERE company_Id = ?1)", nativeQuery = true)
	    List<Questions> findByCompanyId(int companyId);
	 
	 
	 List<Questions> findByVacancy_VacancyId(int vacancyId);
}
