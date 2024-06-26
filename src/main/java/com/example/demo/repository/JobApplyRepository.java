package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.JobApply;
import java.util.List;
import com.example.demo.entity.JobSeekers;

import jakarta.transaction.Transactional;




public interface JobApplyRepository extends JpaRepository<JobApply, Integer>{
	
	List<JobApply> findByJobseekerUserId(int userId);
	
	@Modifying
    @Transactional
    @Query("UPDATE JobApply j SET j.status = :status, j.finalScore = :finalScore WHERE j.jobseeker.userId = :userId AND j.vacancy.vacancyId = :vacancyId")
    void updateStatusAndFinalScoreByUserIdAndVacancyId(@Param("userId") int userId, @Param("vacancyId") int vacancyId, @Param("status") String status, @Param("finalScore") String finalScore);

	
	
	@Query(value = "SELECT * FROM jobapply WHERE vacancy_Id IN (SELECT vacancy_Id FROM vacancy WHERE company_Id = :companyId)", nativeQuery = true)
	List<JobApply> findByCompanyId(int companyId);
	
	////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////
	@Query(value = "SELECT apply_id FROM jobapply WHERE vacancy_id = :vacancyId AND user_id = :userId", nativeQuery = true)
    List<Integer> findApplyIdByVacancyIdAndUserId(@Param("vacancyId") int vacancyId, @Param("userId") int userId);

	
	/////////////////////////////////////////////////////////////////////////////////
	@Query("SELECT ja FROM JobApply ja WHERE ja.jobseeker.userId = :userId")
	List<JobApply> findByUserId(int userId);

	

	
	
	
	
}
