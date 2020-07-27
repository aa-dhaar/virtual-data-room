package com.aa.virtualroom.dao;


import com.aa.virtualroom.model.JobDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FiuJobRepo extends JpaRepository<JobDetails, UUID> {

    @Query("select j from JobDetails j where j.jobId = ?1")
    public Optional<JobDetails> getJobById(@Param("jobId") UUID jobId);

    @Query("select j from JobDetails j where j.fiuId = ?1")
    public Optional<List<JobDetails>> getJobByFiuId(@Param("fiuId") UUID fiuId);

}
