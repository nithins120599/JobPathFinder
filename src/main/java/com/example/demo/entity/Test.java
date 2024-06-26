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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "test")
@Data
public class Test {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="testId",nullable=false)
	private int testId;
	
	@ManyToOne
	@JoinColumn(name="vacancyId" ,referencedColumnName="vacancyId")
	private Vacancy vacancy;
	
	@Column(name="testDate",nullable=true)
	private String testDate;
	
	@Column(name="testDuration",nullable=true)
	private String testDuration;
	
	

}
