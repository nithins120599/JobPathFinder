package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Company;
import com.example.demo.entity.Test;

public interface TestRepository extends JpaRepository<Test, Integer>{

	@Query(value = "SELECT * FROM test WHERE vacancy_Id IN (SELECT vacancy_Id FROM vacancy WHERE company_Id = :companyId)", nativeQuery = true)
    List<Test> findByCompanyId(int companyId);
	
	 @Query("SELECT t FROM Test t WHERE t.vacancy.vacancyId = :vacancyId")
	    Test findByVacancyId(int vacancyId);
	
  }
	

	
	

