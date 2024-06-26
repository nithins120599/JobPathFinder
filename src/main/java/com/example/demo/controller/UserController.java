package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UserController {

	
	
	@GetMapping("/userlogin")
	public ModelAndView loginForUser(Model model) {
		
		return new ModelAndView("companylogin");
	}
	
	@GetMapping("/index")
	public String getMethodName() {
		return "index";
	}
	
	@GetMapping("/about")
	public String getAboutName() {
		return "about";
	}
	@GetMapping("/contact")
	public String getContact() {
		return "contact";
	}
	
}
