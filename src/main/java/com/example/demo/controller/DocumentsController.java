package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Company;
import com.example.demo.entity.Documents;
import com.example.demo.service.DocumentsService;

@Controller
@RequestMapping("api/v1")

public class DocumentsController {
	@Autowired
	DocumentsService docserv;

	@PostMapping("/addDocuments")
	public ResponseEntity<Object> adddoc(@RequestBody Documents doc) {
		docserv.addDocuments(doc);
		return new ResponseEntity<>("Document Added Welcome To Hire Me Now", HttpStatus.CREATED);

	}

	
	@GetMapping("/getDocuments/{userId}")
	public ResponseEntity<Object> getMethodName(@PathVariable("userId")int userId) {
		
			List<Documents> company = docserv.getDocumentsByUserId(userId);
			ResponseEntity<Object> entity = new ResponseEntity<>(company, HttpStatus.OK);

			return entity;
		}

	
	@GetMapping("/getDoc/{docId}")
	public ResponseEntity<Object> getDocument(@PathVariable("docId") int docId){
		Documents documen;
	if(docserv.isDocumentsExist(docId)) {
		documen=docserv.getDocumentsById(docId);
		
		}else {
			documen=null;
		}
	ResponseEntity<Object> entity=new ResponseEntity<>(documen,HttpStatus.OK);
	return entity;
	}
	
	
	@PutMapping(value="/updateDocument/{docId}")
	public ResponseEntity<Object> updateCompany(@PathVariable("docId") int docId, @RequestBody Documents documents)
	{
		
		boolean flag;
		if(docserv.isDocumentsExist(docId)){
			flag = docserv.updateDocuments(documents); 
		}else {
			flag = false;
		}
		
		return new ResponseEntity<>(flag, HttpStatus.OK);
		}
	
	@DeleteMapping(value="/deleteDocument/{docId}")
	public ResponseEntity<Object> deleteDocument(@PathVariable("docId") int docId){
		
		boolean flag;
		if(docserv.isDocumentsExist(docId)) {
			flag = docserv.deleteDocuments(docId);
		}else {
			flag = false;
		}
		
		return new ResponseEntity<>(flag,HttpStatus.OK);
		
	}

}
