package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.EMail;
import com.example.demo.service.EMailService;

@RestController
public class EmailController {
	@Autowired
	EMailService senderservice;
	
	@PostMapping("/sendMail")
	public ResponseEntity<Object> addCustomer(@RequestBody EMail email ){
		senderservice.sendHtmlEmail(email.getToMail(), email.getSubject(), email.getBody());
		return new ResponseEntity<>("Mail Sent.",HttpStatus.OK);
		
	}
	
}




