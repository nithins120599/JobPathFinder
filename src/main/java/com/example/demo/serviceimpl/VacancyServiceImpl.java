package com.example.demo.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Company;

import com.example.demo.entity.Vacancy;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.VacancyRepository;
import com.example.demo.service.CompanyService;
import com.example.demo.service.VacancyService;
@Service
public class VacancyServiceImpl implements VacancyService{

	@Autowired
	VacancyRepository vacancyRepository;
	@Override
	public void addVacancy(Vacancy vacancy) {
		 vacancyRepository.save(vacancy);
		
	}

	@Override
	public List<Vacancy> getAllVacancies() {
		List<Vacancy> vacancyList = vacancyRepository.findAll();
		return vacancyList;
		
		
		
	}

	@Override
	public boolean isVacancyExist(int vacancyId) {
		Optional<Vacancy> vacancy =vacancyRepository.findById(vacancyId);
		if(vacancy.isPresent()) {
			return true;
		}
		return false;
		
	}
	
	

	@Override
	public Vacancy getVacancyById(int vacancyId) {
		Optional<Vacancy> vacancy =vacancyRepository.findById(vacancyId);
		Vacancy vac;
		if(vacancy.isPresent()) {
			vac=vacancy.get();
		}else {
			throw new ResourceNotFoundException("Vacancy", "VacancyId", vacancyId);
		}
		return vac;
	}
	
	

	

	@Override
	public boolean deleteVacancy(int vacancyId) {
		Optional<Vacancy> vacancy =vacancyRepository.findById(vacancyId);
		if(vacancy.isPresent()) {
			vacancyRepository.deleteById(vacancyId);
			return true;
		}else {
		return false;
	}
		
	}

	@Override
	public boolean updateVacancy(Vacancy vacancy) {
		Optional<Vacancy> vacancy1 =vacancyRepository.findById(vacancy.getVacancyId());
		if(vacancy1.isPresent()) {
			vacancyRepository.save(vacancy);
			return true;
		
	}else {
		return false;
	}

	}

	
	/*
	@Override
	public List<Vacancy> getVacanciesByCompanyId(int companyId) {
        List<Vacancy> vacancies = vacancyRepository.findByCompany_CompanyId(companyId);
        if (!vacancies.isEmpty()) {
            return vacancies;
        } else {
            throw new ResourceNotFoundException("Vacancies", "CompanyId", companyId);
        }
    } 
    ChatgptCode   */
	
	
	
	@Override
	public List<Vacancy> getVacanciesByCompanyId(int companyId) {
		List<Vacancy> vacancyList = vacancyRepository.findByCompany_CompanyId(companyId);
		return vacancyList;
	}

	@Override
	public List<Object[]> getVacancyIdAndJobTitle(int companyId) {
		List<Object[]>  vacanc =vacancyRepository.findVacancyIdAndJobTitleByCompanyId(companyId);
		
		return vacanc;
	}
}
