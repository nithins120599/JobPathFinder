package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Admin;
import com.example.demo.service.AdminService;

@RestController
@RequestMapping("/api/v1")
public class AdminController {
	@Autowired
	AdminService adminService;

/////////////////////////////////////////////////////////

	@PostMapping(value = "/addAdmin")
	public ResponseEntity<Object> addProduct(@RequestBody Admin admin) {
		adminService.addAdmin(admin);
		return new ResponseEntity<>("Admin added Successfully", HttpStatus.CREATED);
	}

	@PostMapping("/adminLogin")
	public ResponseEntity<Object> adminLogin(@RequestBody Admin admin) {

		boolean flag = adminService.loginvalidate(admin);
		return new ResponseEntity<>(flag, HttpStatus.OK);

	}

			///////////////////////////////
}
