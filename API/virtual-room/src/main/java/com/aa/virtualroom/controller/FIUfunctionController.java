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
import com.aa.virtualroom.service.FiuJobService;

@RestController
@RequestMapping("/fiu")
public class FIUfunctionController {
	@Autowired
	private FiuJobService fiuJobService;

	@PostMapping("/function")
	public String uploadFile(@RequestParam("file") MultipartFile file, 
			@RequestParam("requesterFiu") String requesterFiu,
			@RequestParam("jsonSchema") String jsonSchema
			) {
		FuntionDetails funtionDetails = new FuntionDetails();
		funtionDetails.setRequesterFiu(requesterFiu);
		funtionDetails.setJsonSchema(jsonSchema);
		//jobDetails.setFiuInputs(fiuInputs);
		String jobId = fiuJobService.storeFile(file, funtionDetails);
		/*String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/")
				.path(fileName)
				.toUriString();*/
		return new String("Thank you for submitted request! we are working on this..Session ID number is: "+jobId);
	}
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	
	@GetMapping("/function/{functionId}")
	public ResponseEntity<?> getFunctionDetails(@PathVariable String functionId) throws RecordNotFoundException {
		FuntionDetails funtionDetails = fiuJobService.getFunctionDetails(functionId);
		return new ResponseEntity<FuntionDetails>(funtionDetails, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/functions")
	public ResponseEntity<?> getListOfDetails() throws RecordNotFoundException {
		List<FuntionDetails> funtionDetails = fiuJobService.getListOfFunction();
		return new ResponseEntity<List<FuntionDetails>>(funtionDetails, new HttpHeaders(), HttpStatus.OK);
	}
}
