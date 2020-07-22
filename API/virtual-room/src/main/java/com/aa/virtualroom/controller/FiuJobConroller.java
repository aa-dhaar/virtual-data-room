package com.aa.virtualroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.aa.virtualroom.model.JobDetails;
import com.aa.virtualroom.service.FiuJobService;

@RestController
@RequestMapping("/fiu")
public class FiuJobConroller {

	@Autowired
	private FiuJobService fiuJobService;

	@PostMapping("/uploadBinary")
	public String uploadFile(@RequestParam("file") MultipartFile file, 
			@RequestParam("requesterFiu") String requesterFiu,
			@RequestParam("fiuInputs") String fiuInputs) {
		JobDetails jobDetails = new JobDetails();
		jobDetails.setRequesterFiu(requesterFiu);
		jobDetails.setFiuInputs(fiuInputs);
		String fileName = fiuJobService.storeFile(file, jobDetails);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/")
				.path(fileName)
				.toUriString();
		return new String("Thank you for submitted request! we are working on this");
	}
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
}
