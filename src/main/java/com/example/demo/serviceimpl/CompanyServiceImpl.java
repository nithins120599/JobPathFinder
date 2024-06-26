package com.example.demo.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Company;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CompanyRepository;

import com.example.demo.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService{

	
	@Autowired
	CompanyRepository companyRepository;
	
	
	@Override
	public void addCompany(Company company) {
		companyRepository.save(company);
		
		
	}

	@Override
	public List<Company> getAllCompanies() {
		List<Company> companyList = companyRepository.findAll();
		return companyList;
	}
	
	

	@Override
	public boolean isCompanyExist(int companyId) {
		Optional<Company> company = companyRepository.findById(companyId);
		if(company.isPresent()) {
			return true;
		}else {
			return false;
		}
	}
	
	
	

	@Override
	public Company getCompanyById(int companyId) {
		// TODO Auto-generated method stub
		Optional<Company> company = companyRepository.findById(companyId);
		Company comp;
		if(company.isPresent()) {
			comp=company.get();
		}else {
			throw new ResourceNotFoundException("Company","companyId", companyId);
		}
		return comp;
			}



	@Override
	public boolean deleteCompany(int companyId) {
		Optional<Company> company =  companyRepository.findById(companyId);
		if(company.isPresent()) {
			 companyRepository.deleteById(companyId);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean updateCompany(Company company) {
		Optional<Company> company1 = companyRepository.findById(company.getCompanyId());
		
		if(company1.isPresent()) {
			companyRepository.save(company);
			return true;
			
			
		}else {
			return false;
		}
		
		}

	/*
	@Override
	public Company loginvalidate(Company company) {
		Company company1=companyRepository.findByEmailAndPassword(company.getEmail(),company.getPassword());
		System.out.println("whatisthere in company="+company1);
		return company1;
	}
	*/

	@Override
	public Company loginvalidate(String email) {
		Company company1=companyRepository.findByEmail(email);
		return company1;
	}
}
	
	


