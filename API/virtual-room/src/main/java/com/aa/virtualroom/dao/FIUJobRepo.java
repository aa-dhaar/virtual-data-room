package com.aa.virtualroom.dao;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aa.virtualroom.model.JobDetails;

public interface FIUJobRepo extends JpaRepository<JobDetails, Integer>{

	//@Query("select new com.aa.virtualroom.model.JobDetails.JobDetails(j.jobId, j.requesterFiu, j.binaryName, j.uploadDir, j.status) from JobDetails j where j.jobId = ?1")
	@Query("select j from JobDetails j where j.jobId = ?1")
	public Optional<JobDetails> getJobById(@Param("jobId") UUID jobId);
	
}
