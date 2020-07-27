package com.aa.virtualroom.service;

import com.aa.virtualroom.dao.FiuFunctionRepo;
import com.aa.virtualroom.dao.FiuJobRepo;
import com.aa.virtualroom.exception.RecordNotFoundException;
import com.aa.virtualroom.model.FunctionDetails;
import com.aa.virtualroom.model.JobDetails;
import com.aa.virtualroom.model.JobState;
import com.aa.virtualroom.request.JobRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FiuJobService {

    @Autowired
    FiuJobRepo fiuJobRepo;

    @Autowired
    FiuFunctionRepo fiuFunctionRepo;

    @Autowired
    public FiuJobService() {
    }

    public String createJob(JobRequest jobRequest) throws RecordNotFoundException {

        String functionId = jobRequest.getFunctionId();
        Optional<FunctionDetails> functionDetails = fiuFunctionRepo.findById(UUID.fromString(functionId));

        if (functionDetails.isPresent() && functionDetails.get().getFiuId().toString().equals(jobRequest.getFiuId())) {
            JobDetails jobDetails = new JobDetails();
            jobDetails.setFunctionDetails(functionDetails.get());
            jobDetails.setAaId(jobRequest.getAaId());
            jobDetails.setState(JobState.CREATED.name());
            jobDetails.setRequestParams(jobRequest.getRequestParams());
            fiuJobRepo.save(jobDetails);
            return jobDetails.getJobId().toString();
        } else {
            throw new RecordNotFoundException("Could not find FunctionId=" + functionId);
        }
    }

    public JobDetails getJobDetails(String jobId) throws RecordNotFoundException {
        Optional<JobDetails> jobDetails = fiuJobRepo.getJobById(UUID.fromString(jobId));
        if (jobDetails.isPresent()) {
            return jobDetails.get();
        } else {
            throw new RecordNotFoundException("Could not find JobId=" + jobId);
        }
    }

    public List<JobDetails> getJobs(String fiuId) {
        Optional<List<JobDetails>> jobDetails = fiuJobRepo.getJobByFiuId(UUID.fromString(fiuId));
        return jobDetails.orElse(Collections.emptyList());

    }

}
