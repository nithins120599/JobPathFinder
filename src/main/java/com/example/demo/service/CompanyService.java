package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Company;



public interface CompanyService {
	void addCompany(Company company);
	List<Company> getAllCompanies();
	boolean isCompanyExist(int companyId);
	Company getCompanyById(int companyId);
	boolean deleteCompany(int companyId);
	boolean updateCompany(Company company);
	
	//public Company loginvalidate(Company company);
	
	public Company loginvalidate(String email);
}



