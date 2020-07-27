package com.aa.virtualroom.controller;

import com.aa.virtualroom.exception.RecordNotFoundException;
import com.aa.virtualroom.model.JobDetails;
import com.aa.virtualroom.request.JobRequest;
import com.aa.virtualroom.response.ErrorResponse;
import com.aa.virtualroom.response.JobIdResponse;
import com.aa.virtualroom.service.FiuJobService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vdr")
public class FiuJobController {

    private static Logger LOGGER = LogManager.getLogger(FiuJobController.class);

    @Autowired
    private FiuJobService fiuJobService;

    @PostMapping("/createJob")
    public ResponseEntity<?> createJob(@RequestBody JobRequest jobRequest) {
        try {
            JobIdResponse jobIdResponse =
                new JobIdResponse(fiuJobService.createJob(jobRequest));
		    /*
		    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/")
				.path(fileName)
				.toUriString();
			*/
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(jobIdResponse);
        } catch (RecordNotFoundException e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/getJobDetails")
    public ResponseEntity<?> getJobDetails(@RequestParam("jobId") String jobId) {
        try {
            JobDetails jobDetails = fiuJobService.getJobDetails(jobId);
            return ResponseEntity.status(HttpStatus.OK).body(jobDetails);
        } catch (RecordNotFoundException e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/getJobsByFiuId")
    public ResponseEntity<?> getJobsByFiuId(@RequestParam("fiuId") String fiuId) {
        List<JobDetails> jobs = fiuJobService.getJobs(fiuId);
        return ResponseEntity.status(HttpStatus.OK).body(jobs);
    }

}
