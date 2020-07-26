package com.aa.virtualroom.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aa.virtualroom.model.FuntionDetails;

public interface FIUFuntionRepo extends JpaRepository<FuntionDetails, UUID>{
	//@Query("select new com.aa.virtualroom.model.JobDetails.JobDetails(j.jobId, j.requesterFiu, j.binaryName, j.uploadDir, j.status) from JobDetails j where j.jobId = ?1")
		
}
