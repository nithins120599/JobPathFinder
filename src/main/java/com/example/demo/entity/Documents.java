package com.example.demo.entity;

public class Documents {
	
    private int docId;

	
	private  JobSeekers jobSeekers;

    
    private String documentType;

    
    private String documentFile;


	public int getDocId() {
		return docId;
	}


	public JobSeekers getJobSeekers() {
		return jobSeekers;
	}


	public void setJobSeekers(JobSeekers jobSeekers) {
		this.jobSeekers = jobSeekers;
	}


	public String getDocumentType() {
		return documentType;
	}


	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}


	public String getDocumentFile() {
		return documentFile;
	}


	public void setDocumentFile(String documentFile) {
		this.documentFile = documentFile;
	}


	public void setDocId(int docId) {
		this.docId = docId;
	}


	
}
