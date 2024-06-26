package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Company;

import com.example.demo.entity.Vacancy;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.CompanyService;
import com.example.demo.service.VacancyService;

@RestController
@RequestMapping("/api/v1")
public class VacancyController {
	@Autowired
	VacancyService vacancyService;
	
	
	@PostMapping(value="/addVacancy")
	public ResponseEntity<Object> addVacancy(@RequestBody Vacancy vacancy) {
		vacancyService.addVacancy(vacancy);
		return new ResponseEntity<>("Vacancy added Successfully",HttpStatus.CREATED);
	}
	
	
	@GetMapping(value="/allVacancies")
	public ResponseEntity<Object> getAllVacancies(){
		List<Vacancy> vacancy = vacancyService.getAllVacancies();
		ResponseEntity<Object> entity = new ResponseEntity<>(vacancy,HttpStatus.OK);
		return entity;
	}
	
	@GetMapping(value="/getVacancy/{vacancyId}")
	public ResponseEntity<Object> getVacancy(@PathVariable("vacancyId") int vacancyId){
		Vacancy vacancy;
		
		if(vacancyService.isVacancyExist(vacancyId)) {
			vacancy=vacancyService.getVacancyById(vacancyId);
			
		}else {
			vacancy = null;
		}
		ResponseEntity<Object> entity = new ResponseEntity<>(vacancy,HttpStatus.OK);
		return entity;
		
	}
	
	
	@DeleteMapping(value="/deleteVacancy/{vacancyId}")
	public ResponseEntity<Object> deleteVacancy(@PathVariable("vacancyId") int vacancyId){
		
		boolean flag;
		if(vacancyService.isVacancyExist(vacancyId)) {
			flag = vacancyService.deleteVacancy(vacancyId);
		}else {
			flag = false;
		}
		
		return new ResponseEntity<>(flag,HttpStatus.OK);
		
	}
	
	
	
	@PutMapping(value="/updateVacancy/{vacancyId}")
	public ResponseEntity<Object> updateVacancy(@PathVariable("vacancyId") int vacancyId, @RequestBody Vacancy vacancy)
	{
		
		boolean flag;
		if(vacancyService.isVacancyExist(vacancyId)) {
			flag = vacancyService.updateVacancy(vacancy);
		}else {
			flag = false;
		}
		
		return new ResponseEntity<>(flag, HttpStatus.OK);
		
	}
	
	/*@GetMapping("/vacancies/company/{companyId}")
	public ResponseEntity<List<Vacancy>> getVacanciesByCompanyId(@PathVariable int companyId) {
        try {
            List<Vacancy> vacancies = vacancyService.getVacanciesByCompanyId(companyId);
            return new ResponseEntity<>(vacancies, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        ///////////////////////////////chatgpt code
        
        */
	
	
	
	@GetMapping(value="/getVacancies/{companyId}")
	public ResponseEntity<Object> getVacanciesByCompanyId(@PathVariable("companyId") int companyId){
		List<Vacancy> vacancy = vacancyService.getVacanciesByCompanyId(companyId);
		ResponseEntity<Object> entity = new ResponseEntity<>(vacancy,HttpStatus.OK);
		return entity;
	}
	
	
	
	@GetMapping("/vacIdandJobTitle/{companyId}")
	public ResponseEntity<Object> getVacancyIdAndJobTitle(@PathVariable("companyId") int companyId){
		List<Object[]> vacancy =vacancyService.getVacancyIdAndJobTitle(companyId);
        ResponseEntity<Object> entity = new ResponseEntity<>(vacancy,HttpStatus.OK);
		return entity;
    }


}

