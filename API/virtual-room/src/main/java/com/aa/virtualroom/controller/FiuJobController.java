package com.aa.virtualroom.controller;

import com.aa.virtualroom.exception.RecordNotFoundException;
import com.aa.virtualroom.model.JobDetails;
import com.aa.virtualroom.request.JobRequest;
import com.aa.virtualroom.response.ErrorResponse;
import com.aa.virtualroom.response.FunctionDetailsResponse;
import com.aa.virtualroom.response.FunctionResponse;
import com.aa.virtualroom.response.JobDetailsResponse;
import com.aa.virtualroom.response.JobIdResponse;
import com.aa.virtualroom.response.JobResponse;
import com.aa.virtualroom.service.FiuJobService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vdr")
public class FiuJobController {

	private static Logger LOGGER = LogManager.getLogger(FiuJobController.class);

	@Autowired
	private FiuJobService fiuJobService;

	@PostMapping("/createJob")
	public ResponseEntity<?> createJob(@RequestParam("fiuId") String fiuId,
			@RequestParam("functionId") String functionId,
			@RequestParam("aaId") String aaId,
			@RequestParam("requestParams") String requestParams) {
		try {
			JobRequest jobRequest = new JobRequest(fiuId, functionId, aaId, requestParams);
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
			JobDetailsResponse jobDetailsResponse = new JobDetailsResponse(jobId, jobDetails.getFunctionId().toString(), jobDetails.getAaId(), jobDetails.getState(), jobDetails.getCreateDate(), jobDetails.getLastUpdateDate());
			Map<String, JobDetailsResponse> jobs = new HashMap<String, JobDetailsResponse>();
			jobs.put(jobDetails.getFiuId().toString(), jobDetailsResponse);
			JobResponse jobResponse = new JobResponse(jobDetails.getFiuId().toString(), jobs);
			return ResponseEntity.status(HttpStatus.OK).body(jobResponse);
		} catch (RecordNotFoundException e) {
			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/getJobsByFiuId")
	public ResponseEntity<?> getJobsByFiuId(@RequestParam("fiuId") String fiuId) {
		List<JobDetails> listOfJobs = fiuJobService.getJobs(fiuId);
		Map<String, JobDetailsResponse> jobs = new HashMap<String, JobDetailsResponse>();

		for (JobDetails jobDetails : listOfJobs) {
			JobDetailsResponse jobDetailsResponse = new JobDetailsResponse(jobDetails.getJobId().toString(), jobDetails.getFunctionId().toString(), jobDetails.getAaId(), jobDetails.getState(), jobDetails.getCreateDate(), jobDetails.getLastUpdateDate());
			jobs.put(jobDetails.getJobId().toString(), jobDetailsResponse);
		}
		JobResponse jobResponse = new JobResponse(fiuId, jobs);
		return ResponseEntity.status(HttpStatus.OK).body(jobResponse);
	}

}
