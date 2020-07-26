package com.aa.virtualroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aa.virtualroom.exception.RecordNotFoundException;
import com.aa.virtualroom.model.FuntionDetails;
import com.aa.virtualroom.model.JobDetails;
import com.aa.virtualroom.service.FiuJobService;

@RestController
@RequestMapping("/fiu")
public class FiuJobConroller {

	@Autowired
	private FiuJobService fiuJobService;

	@PostMapping("/job")
	public String uploadFile(
			@RequestParam("functionId") String functionId,
			@RequestParam("aaId") String aaId
			) throws RecordNotFoundException {
		
		String jobId = fiuJobService.createJob(functionId, aaId);
		/*String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/")
				.path(fileName)
				.toUriString();*/
		return new String("Thank you for submitted request! we are working on this..Session ID number is: "+jobId);
	}
	
	
	
	@GetMapping("/job/{jobId}")
	public ResponseEntity<?> getJobDetails(@PathVariable String jobId) throws RecordNotFoundException {
		JobDetails jobDetails = fiuJobService.getJobDetails(jobId);
		return new ResponseEntity<JobDetails>(jobDetails, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/jobs")
	public ResponseEntity<?> getListOfJob() throws RecordNotFoundException {
		List<JobDetails> listOfJobs = fiuJobService.getListOfJobs();
		return new ResponseEntity<List<JobDetails>>(listOfJobs, new HttpHeaders(), HttpStatus.OK);
	}
	
	
}
