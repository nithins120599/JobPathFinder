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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Company;

import com.example.demo.service.CompanyService;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/v1")
public class CompanyController {
	@Autowired
	CompanyService companyService;
	
	
	@PostMapping(value="/addCompany")
	public ResponseEntity<Object> addProduct(@RequestBody Company company) {
		companyService.addCompany(company);
		return new ResponseEntity<>("Company added Successfully",HttpStatus.CREATED);
	}
	
	
	@GetMapping("/allCompanies")
	public ResponseEntity<Object> getAllcompanies(){
		    List<Company> company =  companyService.getAllCompanies();
	ResponseEntity<Object> entity=new ResponseEntity<>(company,HttpStatus.OK);

	return entity;
	}

	
	@GetMapping("/getCompany/{companyId}")
	public ResponseEntity<Object> getCompany(@PathVariable("companyId") int companyId){
		Company company;
	if(companyService.isCompanyExist(companyId)) {
		company=companyService.getCompanyById(companyId);
			
		}else {
			company=null;
		}
	ResponseEntity<Object> entity=new ResponseEntity<>(company,HttpStatus.OK);
	return entity;
	}
	
	
	@DeleteMapping(value="/deleteCompany/{companyId}")
	public ResponseEntity<Object> deleteCompany(@PathVariable("companyId") int companyId){
		
		boolean flag;
		if(companyService.isCompanyExist(companyId)) {
			flag = companyService.deleteCompany(companyId);
		}else {
			flag = false;
		}
		
		return new ResponseEntity<>(flag,HttpStatus.OK);
		
	}
	
	
	@PutMapping(value="/updateCompany/{companyId}")
	public ResponseEntity<Object> updateCompany(@PathVariable("companyId") int companyId, @RequestBody Company company)
	{
		
		boolean flag;
		if(companyService.isCompanyExist(companyId)){
			flag = companyService.updateCompany(company); 
		}else {
			flag = false;
		}
		
		return new ResponseEntity<>(flag, HttpStatus.OK);
		}
	
	
/*
	@PostMapping("/companyLogin")
	public ResponseEntity<Object> companyLogin(@RequestBody Company company, HttpSession session) {
		 Company company1= companyService.loginvalidate(company);
	    
		 int cid=company1.getCompanyId();
		    System.err.println("ccccccccc"+cid);
		    if (company1!=null) {
		        // Set companyId in the session
		        session.setAttribute("companyId", company1.getCompanyId());
		        // System.out.println("compo"+session.getAttribute("companyId"));
		    }
		    return new ResponseEntity<>(company1, HttpStatus.OK);
		}
	*/

	@GetMapping("companyLogin/{email}")
	public ResponseEntity<Object> companyLogin(@PathVariable("email") String  email) {
		 Company company1= companyService.loginvalidate(email);
	     
		    return new ResponseEntity<>(company1, HttpStatus.OK);
		}
	
	
	}



