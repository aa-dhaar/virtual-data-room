package com.aa.virtualroom.model;

import java.util.Date;
import java.util.UUID;

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
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
@Entity
@Table(name = "job_dteails")
public class JobDetails {
	
	

	public JobDetails() {
		super();
	}

	public JobDetails(UUID jobId, String requesterFiu, String binaryName, String uploadDir, String status) {
		super();
		this.jobId = jobId;
		this.requesterFiu = requesterFiu;
		this.binaryName = binaryName;
		this.uploadDir = uploadDir;
		this.status = status;
	}

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator",
		parameters = {
			@Parameter(
					name = "uuid_gen_strategy_class",
					value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
				)
		}
	)
	@Column(name = "job_id", updatable = false, nullable = false)
	 private UUID  jobId;
	 
     @Column(name = "requester_fiu")
     private String requesterFiu;
     
	 @Column(name = "binary_name")
	 private String binaryName;
	 
	 @Column(name = "inputs")
	 private String fiuInputs;
	 
		/*
		 * @Column(name = "binary_format") private String binaryFormat;
		 */
	 
	 @Column(name = "upload_dir")
	 private String uploadDir;

	 @Column(nullable = false, updatable = false)
	 @CreationTimestamp
	 private Date createDate;
	 //create time,pick up time
	 
	 @Column(name = "last_updated_date_time")
	 private Date lastUpdateDate;
	 
	 @Column(name = "job_start_date_time")
	 private Date jobPickupDateTime;
	 
	 @Column(name = "status")
	 private String status;
	 
	public UUID getJobId() {
		return jobId;
	}

	public void setJobId(UUID jobId) {
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

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getJobPickupDateTime() {
		return jobPickupDateTime;
	}

	public void setJobPickupDateTime(Date jobPickupDateTime) {
		this.jobPickupDateTime = jobPickupDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	 
	
	
}
