package com.example.demo.entity;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="company")
@Data
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="companyId",nullable=false)
	private int companyId;
	
	@CreationTimestamp
	@Temporal(TemporalType.DATE)
	@Column(name="regDate",nullable=true)
	private String regDate;
	
	@Column(name="companyName",nullable=false)
	private String companyName;
	
	@Column(name="email",nullable=true)
	
	private String email;
	
	@Column(name="mobile",nullable=true)
	
	private String mobile;
	
	@Column(name="location",nullable=true)
	private String location;
	
	@Column(name="city",nullable=true)
	private String city;
	
	@Column(name="state",nullable=true)
	private String state;
	
	@Column(name="companyLevel",nullable=true)
	private String companyLevel;
	
	@Column(name="url",nullable=true)
	private String url;
	
	@Column(name="logo",nullable=true)
	private String logo;
	
	@Column(name="password",nullable=true)
	
	private String password;
	
	
	
	
}
