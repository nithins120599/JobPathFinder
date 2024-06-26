package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Company;
import com.example.demo.entity.Vacancy;

public interface VacancyService {
	void addVacancy(Vacancy vacancy);
	List<Vacancy> getAllVacancies();

	boolean isVacancyExist(int vacancyId);
	Vacancy getVacancyById(int vacancyId);
	
	boolean deleteVacancy(int vacancyId);
	boolean updateVacancy(Vacancy vacancy);
	
	List<Vacancy> getVacanciesByCompanyId(int companyId);
	
	 List<Object[]> getVacancyIdAndJobTitle(int companyId);
}
