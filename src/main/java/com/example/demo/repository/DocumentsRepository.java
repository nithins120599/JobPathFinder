package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Documents;
import com.example.demo.entity.JobSeekers;

public interface DocumentsRepository extends JpaRepository<Documents, Integer>{
	
    List<Documents> findByJobSeekersUserId(int userId);
    
}
