package com.example.demo.entity;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name="jobapply")
@Data
public class JobApply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "applyId")
	private int applyId;
	
	@CreationTimestamp
	@Column(name = "applyDate",nullable=true)
	private String applyDate;

	@ManyToOne
	@JoinColumn(name = "userId",referencedColumnName="userId")
	private JobSeekers jobseeker;

	@ManyToOne
	@JoinColumn(name = "vacancyId",referencedColumnName="vacancyId")
	private Vacancy vacancy;
	
	
	@Column(name = "status",nullable=true)
	private String status;
	
	
	@Column(name = "finalScore",nullable=true)
	private String finalScore;

}
