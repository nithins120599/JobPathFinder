package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.entity.SelectedCandidates;

public interface SelectedCandidatesRepository extends JpaRepository<SelectedCandidates,Integer>{
 
	List<SelectedCandidates> findByVacancyCompanyCompanyId(int companyId);
	

}
