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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="vacancy")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Vacancy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="vacancyId",nullable=false)
	private int vacancyId;
	
	@ManyToOne
	@JoinColumn(name="companyId" ,referencedColumnName="companyId")
	private Company company;
	
	@CreationTimestamp
	@Column(name="postDate",nullable=true)
	private String postDate;
	
	@Column(name="jobTitle",nullable=true)
	private String jobTitle;
	
	@Column(name="description",nullable=true)
	private String description;
	
	@Column(name="requirement",nullable=true)
	private String requirement;
	
	@Column(name="noOfVacancies",nullable=true)
	private String noOfVacancies;
	
	@Column(name="openDate",nullable=true)
	private String openDate;
	
	@Column(name="closeDate",nullable=true)
	private String closeDate;
}
