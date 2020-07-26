package com.aa.virtualroom.model;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
@Entity
@Table(name = "funtion_dteails")
public class FuntionDetails {





	public FuntionDetails(UUID functionId, String status, String requesterFiu, String uploadDir, Date createDate,
			String jsonSchema, String binaryName) {
		super();
		this.functionId = functionId;
		this.status = status;
		this.requesterFiu = requesterFiu;
		this.uploadDir = uploadDir;
		this.createDate = createDate;
		this.jsonSchema = jsonSchema;
		this.binaryName = binaryName;
	}

	public FuntionDetails() {
		super();
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
	@Column(name = "function_id", updatable = false, nullable = false)
	private UUID  functionId;

	@Column(name = "status")
	private String status;

	@Column(name = "requester_fiu")
	private String requesterFiu;

	@Column(name = "upload_dir")
	private String uploadDir;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Date createDate;

	@Column(name = "jsonSchema")
	private String jsonSchema;

	@Column(name = "binary_name")
	private String binaryName;

	
	
	/*
	 * @OneToMany(cascade=CascadeType.ALL)
	 * 
	 * @JoinColumn(name="job_id") private Set<JobDetails> function;
	 */

	public UUID getFunctionId() {
		return functionId;
	}

	public void setFunctionId(UUID functionId) {
		this.functionId = functionId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRequesterFiu() {
		return requesterFiu;
	}

	public void setRequesterFiu(String requesterFiu) {
		this.requesterFiu = requesterFiu;
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

	public String getJsonSchema() {
		return jsonSchema;
	}

	public void setJsonSchema(String jsonSchema) {
		this.jsonSchema = jsonSchema;
	}

	public String getBinaryName() {
		return binaryName;
	}

	public void setBinaryName(String binaryName) {
		this.binaryName = binaryName;
	}

	/*
	 * public Set<JobDetails> getFunction() { return function; }
	 * 
	 * public void setFunction(Set<JobDetails> function) { this.function = function;
	 * }
	 */

	

	

	

}
