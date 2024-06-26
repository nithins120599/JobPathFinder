package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.JobApply;
import com.example.demo.entity.JobSeekers;
import com.example.demo.entity.SelectedCandidates;
import com.example.demo.service.SelectedCandidatesService;

@RestController
@RequestMapping("/api/v1")
public class SelectedCandidatesController {
	
	@Autowired
	SelectedCandidatesService selCandServ;
	@PostMapping("/addCandidates")
	public ResponseEntity<Object> addSelectedCandidates(@RequestBody SelectedCandidates selectedCandidates) {
		selCandServ.addSelectedCandidates(selectedCandidates);
		return new ResponseEntity<>("SelectedCandidates Added  Succesfullyyyy", HttpStatus.CREATED);

	}
	
	@GetMapping(value = "/allSelectedCandidates")
	public ResponseEntity<Object> getAllSelectedCandidates() {
		List<SelectedCandidates> cand = selCandServ.getAllSelectedCandidates();
		ResponseEntity<Object> entity = new ResponseEntity<>(cand, HttpStatus.OK);
		return entity;
	}
	
	
	@GetMapping(value="/findByCompanyIdInSelectedCand/{companyId}")
	public ResponseEntity<Object> findByCompanyIdInSelectedCand(@PathVariable("companyId") int companyId){
		List<SelectedCandidates> jobe = selCandServ.findByVacancyCompanyCompanyId(companyId);
		ResponseEntity<Object> entity = new ResponseEntity<>(jobe,HttpStatus.OK);
		return entity;
		
	}
}
