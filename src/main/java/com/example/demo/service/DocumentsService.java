package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Company;
import com.example.demo.entity.Documents;

public interface DocumentsService {
	void addDocuments(Documents documents);
	
	boolean isDocumentsExist(int docId);
	 Documents getDocumentsById(int docId);
		boolean deleteDocuments(int docId);
		boolean updateDocuments(Documents documents);
		
	
	 List<Documents> getDocumentsByUserId(int userId);
	 
}
