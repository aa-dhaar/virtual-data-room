package com.aa.virtualroom.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aa.virtualroom.dao.FIUFuntionRepo;
import com.aa.virtualroom.dao.FIUJobRepo;
import com.aa.virtualroom.exception.FiuBinaryStorageException;
import com.aa.virtualroom.exception.RecordNotFoundException;
import com.aa.virtualroom.model.FUNCTION_STATUS;
import com.aa.virtualroom.model.FuntionDetails;
import com.aa.virtualroom.model.JOB_STATUS;
import com.aa.virtualroom.model.JobDetails;

@Service
public class FiuJobService {
	private final Path fileStorageLocation;

	@Autowired
	FIUFuntionRepo fiuFuntionRepo;
	
	@Autowired
	FIUJobRepo fiuJobRepo;

	@Autowired
	public FiuJobService(FuntionDetails functionDetails) {
		this.fileStorageLocation = Paths.get(functionDetails.getUploadDir())
				.toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FiuBinaryStorageException("Could not create the directory where the uploaded files will be stored.", ex);
		}
	}
	public String storeFile(MultipartFile file, FuntionDetails functionDetails) {
		// Normalize file name
		String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
		String fileName = "";
		try {
			// Check if the file's name contains invalid characters
			if(originalFileName.contains("..")) {
				throw new FiuBinaryStorageException("Sorry! Filename contains invalid path sequence " + originalFileName);
			}
			String fileExtension = "";
			try {
				fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
			} catch(Exception e) {
				fileExtension = "";
			}
			fileName = functionDetails.getRequesterFiu() + "_" + System.currentTimeMillis() + fileExtension;
			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			//JobDetails jobDetails = new JobDetails();
			//create the object before insert
			functionDetails.setBinaryName(fileName);
			functionDetails.setUploadDir(targetLocation.toString());
			functionDetails.setStatus(FUNCTION_STATUS.PENDING.name());
			fiuFuntionRepo.save(functionDetails);
			return functionDetails.getFunctionId().toString();
		}catch (IOException ex) {
			throw new FiuBinaryStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	} 

	public FuntionDetails getFunctionDetails(String functionId) throws RecordNotFoundException {
		FuntionDetails funtionDetail = null;
		Optional<FuntionDetails> functionDetails = fiuFuntionRepo.findById(UUID.fromString(functionId));
		if(functionDetails.isPresent()) {
			funtionDetail = functionDetails.get();
			FuntionDetails copyToFuntionDetails = new FuntionDetails(funtionDetail.getFunctionId(), funtionDetail.getStatus(), funtionDetail.getRequesterFiu(), funtionDetail.getUploadDir(), funtionDetail.getCreateDate(), funtionDetail.getJsonSchema(), funtionDetail.getBinaryName());
			return copyToFuntionDetails;
		}
		else {
			throw new RecordNotFoundException("Function is not avilable");
		}
	}
	
	public List<FuntionDetails> getListOfFunction(){
		return fiuFuntionRepo.findAll();
	}
	
	
	public String createJob(String functionId,String aaId) throws RecordNotFoundException {
		
		Optional<FuntionDetails> functionDetails = fiuFuntionRepo.findById(UUID.fromString(functionId));
		
		if(functionDetails.isPresent()) {
		JobDetails jobDetails = new JobDetails();
		jobDetails.setFunctionDetails(functionDetails.get());
		jobDetails.setAaId(aaId);
		
		jobDetails.setStatus(JOB_STATUS.CREATED.name());
		fiuJobRepo.save(jobDetails);
		
		return jobDetails.getJobId().toString();
		}
		else {
        	throw new RecordNotFoundException("function does not exist, Please register function");
        }
	}
	public JobDetails getJobDetails(String jobId) throws RecordNotFoundException {
		Optional<JobDetails> jobDetails = fiuJobRepo.getJobById(UUID.fromString(jobId));
		if(jobDetails.isPresent()) {
			
			return jobDetails.get();
		}
		else {
			throw new RecordNotFoundException("Auction Id is not avilable");
		}
	}
	
	public List<JobDetails> getListOfJobs(){
		return fiuJobRepo.findAll();
	}
	
}
