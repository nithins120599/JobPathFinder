package com.example.demo.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Questions;
import com.example.demo.entity.SelectedCandidates;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.repository.SelectedCandidatesRepository;
import com.example.demo.service.SelectedCandidatesService;

@Service
public class SelectedCandidatesServiceImpl implements SelectedCandidatesService{
	@Autowired 
	SelectedCandidatesRepository selCandRepo;

	@Override
	public void addSelectedCandidates(SelectedCandidates selectedCandidates) {
		// TODO Auto-generated method stub
		selCandRepo.save(selectedCandidates);
	}

	@Override
	public List<SelectedCandidates> getAllSelectedCandidates() {
		List<SelectedCandidates> candlist=selCandRepo.findAll();
		return candlist;
	}
	

	@Override
	public boolean isSelectedCandidatesExist(int selectedCandidateId) {
		// TODO Auto-generated method stub
		Optional<SelectedCandidates>  scc = selCandRepo.findById(selectedCandidateId);
		if( scc.isPresent()) {
			return true;
		}else {
			return false;
		}
	}
	
	
	

	@Override
	public SelectedCandidates getSelectedCandidatesById(int selectedCandidateId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SelectedCandidates> findByVacancyCompanyCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return selCandRepo.findByVacancyCompanyCompanyId(companyId);
	}

}
