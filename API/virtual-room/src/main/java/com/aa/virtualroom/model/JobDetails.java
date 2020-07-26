package com.aa.virtualroom.model;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;

//@ConfigurationProperties(prefix = "file")
@Entity
@Table(name = "job_deteails")
public class JobDetails {



	public JobDetails() {
		super();
	}

	

	public JobDetails(UUID jobId, Date createDate, Date lastUpdateDate, Date jobPickupDateTime, String status,
			int retry, FuntionDetails functionDetails, String aaId, String result) {
		super();
		this.jobId = jobId;
		this.createDate = createDate;
		this.lastUpdateDate = lastUpdateDate;
		this.jobPickupDateTime = jobPickupDateTime;
		this.status = status;
		this.retry = retry;
		this.functionDetails = functionDetails;
		this.aaId = aaId;
		this.result = result;
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

	





	/*
	 * @Column(name = "binary_format") private String binaryFormat;
	 */



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

	@Column(name = "retry")
	private int retry;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "function_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private FuntionDetails functionDetails;

	@Column(name = "aaId")
	private String aaId;

	@Column(name = "result")
	private String result;


	public UUID getJobId() {
		return jobId;
	}

	public void setJobId(UUID jobId) {
		this.jobId = jobId;
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

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public String getAaId() {
		return aaId;
	}

	public void setAaId(String aaId) {
		this.aaId = aaId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public FuntionDetails getFunctionDetails() {
		return functionDetails;
	}

	public void setFunctionDetails(FuntionDetails functionDetails) {
		this.functionDetails = functionDetails;
	}




}
