package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="testResult")
@Data
public class TestResult {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "resultId")
	private int resultId;
	
	@ManyToOne
	@JoinColumn(name="userId" ,referencedColumnName="userId")
	private  JobSeekers jobSeekers;
	
	@ManyToOne
	@JoinColumn(name="questionId" ,referencedColumnName="questionId")
	private  Questions  questions;
	
	@ManyToOne
	@JoinColumn(name="vacancyId" ,referencedColumnName="vacancyId")
	private  Vacancy vacancy;
	
	@Column(name = "selectedOption")
	private String selectedOption;
	
	  @Column(name = "result")
	   private String result;

	  @Column(name = "score")
	    private String score;
}
