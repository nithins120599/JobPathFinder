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
@Table(name="selectedCandidates")
@Data
public class SelectedCandidates {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="selectedCandidateId",nullable=false)
	private int selectedCandidateId;
	
	
	@ManyToOne
	@JoinColumn(name="vacancyId" ,referencedColumnName="vacancyId")
	private Vacancy vacancy;
	
	@ManyToOne
	@JoinColumn(name="testId" ,referencedColumnName="testId")
	private Test test;
	
	@ManyToOne
	@JoinColumn(name="userId" ,referencedColumnName="userId")
	private JobSeekers jobSeeker;
	
	@Column(name ="score")
	private int score;
	
	@Column(name ="status")
	private String status;

}
