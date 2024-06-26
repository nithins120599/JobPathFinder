package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.SelectedCandidates;

public interface SelectedCandidatesService {
	void addSelectedCandidates(SelectedCandidates selectedCandidates);
	List<SelectedCandidates> getAllSelectedCandidates();
	boolean isSelectedCandidatesExist(int selectedCandidateId);
	SelectedCandidates getSelectedCandidatesById(int selectedCandidateId);
	
	List<SelectedCandidates> findByVacancyCompanyCompanyId(int companyId);
}
