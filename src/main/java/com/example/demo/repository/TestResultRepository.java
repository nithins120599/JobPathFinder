package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.TestResult;
import com.example.demo.entity.Vacancy;

public interface TestResultRepository extends JpaRepository<TestResult, Integer>{

}
