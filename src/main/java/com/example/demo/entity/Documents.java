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
@Table(name="documents")
@Data
public class Documents {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "docId")
    private int docId;

	@ManyToOne
	@JoinColumn(name="userId" ,referencedColumnName="userId")
	private  JobSeekers jobSeekers;

    @Column(name = "documentType")
    private String documentType;

    @Column(name = "documentFile")
    private String documentFile;

}
