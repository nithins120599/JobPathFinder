package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Company;
import com.example.demo.entity.Vacancy;

public interface VacancyRepository extends JpaRepository<Vacancy, Integer>{

	List<Vacancy> findByCompany_CompanyId(int companyId);
	
	@Query("SELECT v.vacancyId, v.jobTitle FROM Vacancy v WHERE v.company.companyId = :companyId")
	List<Object[]> findVacancyIdAndJobTitleByCompanyId(int companyId);

	
	
}
