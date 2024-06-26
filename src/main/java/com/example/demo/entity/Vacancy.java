package com.example.demo.entity;

import lombok.Data;

@Data
public class Vacancy {
	
	private int vacancyId;
	
	
	private Company company;
	
	private String postDate;
	
	private String jobTitle;
	

	private String description;
	
	
	private String requirement;
	
	
	private String noOfVacancies;
	
	
	private String openDate;
	
	
	private String closeDate;
}
