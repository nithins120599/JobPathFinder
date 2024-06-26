package com.example.demo.entity;

public class TestResult {
	
	private int resultId;
	
	
	private  JobSeekers jobSeekers;
	
	
	private  Questions  questions;
	

	private  Vacancy vacancy;
	
	
	private String selectedOption;
	
	 
	   private String result;

	  
	    private String  score;


		public int getResultId() {
			return resultId;
		}


		public void setResultId(int resultId) {
			this.resultId = resultId;
		}


		public JobSeekers getJobSeekers() {
			return jobSeekers;
		}


		public void setJobSeekers(JobSeekers jobSeekers) {
			this.jobSeekers = jobSeekers;
		}


		public Questions getQuestions() {
			return questions;
		}


		public void setQuestions(Questions questions) {
			this.questions = questions;
		}


		public Vacancy getVacancy() {
			return vacancy;
		}


		public void setVacancy(Vacancy vacancy) {
			this.vacancy = vacancy;
		}


		public String getSelectedOption() {
			return selectedOption;
		}


		public void setSelectedOption(String selectedOption) {
			this.selectedOption = selectedOption;
		}


		public String getResult() {
			return result;
		}


		public void setResult(String result) {
			this.result = result;
		}


		public String getScore() {
			return score;
		}


		public void setScore(String score) {
			this.score = score;
		}
	    
	    
}
