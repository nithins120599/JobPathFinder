package com.example.demo.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Company;
import com.example.demo.entity.Documents;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.DocumentsRepository;
import com.example.demo.service.DocumentsService;

@Service

public class DocumentsServiceImpl implements DocumentsService{
	
	@Autowired
	DocumentsRepository docrep;


	@Override
	public void addDocuments(Documents documents) {
		docrep.save(documents);
		
	}
	
	


	@Override
	public List<Documents> getDocumentsByUserId(int userId) {
		List<Documents> doclist=docrep.findByJobSeekersUserId(userId);
		return doclist;
	}




	@Override
	public Documents getDocumentsById(int docId) {
		Optional<Documents> d =docrep.findById(docId);
		Documents doc;
		if(d.isPresent()) {
			doc=d.get();
		}else {
			throw new ResourceNotFoundException("Documents","docId", docId);
		}
		return doc;
			}
		




	@Override
	public boolean deleteDocuments(int docId) {
		Optional<Documents> documm =  docrep.findById(docId);
		if(documm.isPresent()) {
			docrep.deleteById(docId);
			return true;
		}else {
			return false;
		}
	}

	


	@Override
	public boolean updateDocuments(Documents documents) {
		Optional<Documents> doo =docrep.findById(documents.getDocId());
		if(doo.isPresent()) {
			docrep.save(documents);
				return true;
		}
		else 
		{
			return false;
		}
	}

	@Override
	public boolean isDocumentsExist(int docId) {
		Optional<Documents> dooc =docrep.findById(docId);
		if(dooc.isPresent()) {
			return true;
		}else {
			return false;
		}
}
}
