package com.aa.virtualroom.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.aa.virtualroom.model.JobDetails;

public interface FIUJobRepo extends JpaRepository<JobDetails, Integer>{

}
