package com.aa.virtualroom.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
@Entity
@Table(name = "job_dteails")
public class JobDetails {

	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name = "job_id")
	 private Integer jobId;
	 
     @Column(name = "requester_fiu")
     private String requesterFiu;
     
	 @Column(name = "binary_name")
	 private String binaryName;
	 
	 @Column(name = "inputs")
	 private String fiuInputs;
	 
	 @Column(name = "binary_format")
	 private String binaryFormat;
	 
	 @Column(name = "upload_dir")
	 private String uploadDir;

	 @Column(nullable = false, updatable = false)
	 @CreationTimestamp
	 private Date createDate;
	 //create time,pick up time
	 
	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String getRequesterFiu() {
		return requesterFiu;
	}

	public void setRequesterFiu(String requesterFiu) {
		this.requesterFiu = requesterFiu;
	}

	public String getBinaryName() {
		return binaryName;
	}

	public void setBinaryName(String binaryName) {
		this.binaryName = binaryName;
	}

	public String getFiuInputs() {
		return fiuInputs;
	}

	public void setFiuInputs(String fiuInputs) {
		this.fiuInputs = fiuInputs;
	}

	public String getBinaryFormat() {
		return binaryFormat;
	}

	public void setBinaryFormat(String binaryFormat) {
		this.binaryFormat = binaryFormat;
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	 
	
	
}
