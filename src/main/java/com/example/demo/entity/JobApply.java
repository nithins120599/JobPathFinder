package com.example.demo.entity;

import lombok.Data;


public class JobApply {
	
	private int applyId;

	
	private String applyDate;

	
	private JobSeekers jobseeker;

	
	private Vacancy vacancy;
	
	private String status;
	
	private String finalScore;

	public int getApplyId() {
		return applyId;
	}

	public void setApplyId(int applyId) {
		this.applyId = applyId;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public JobSeekers getJobseeker() {
		return jobseeker;
	}

	public void setJobseeker(JobSeekers jobseeker) {
		this.jobseeker = jobseeker;
	}

	public Vacancy getVacancy() {
		return vacancy;
	}

	public void setVacancy(Vacancy vacancy) {
		this.vacancy = vacancy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(String finalScore) {
		this.finalScore = finalScore;
	}

	
	

}
