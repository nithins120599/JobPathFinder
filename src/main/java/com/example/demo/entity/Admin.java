package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="admin")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Admin {
	@Id
	@Column(name="user_name",nullable=false,length=100)
	private String username;
	
	@Column(name="password",nullable=true)
	private String password;

}
