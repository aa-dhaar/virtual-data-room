package com.aa.virtualroom.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aa.virtualroom.dao.FIUJobRepo;
import com.aa.virtualroom.exception.FiuBinaryStorageException;
import com.aa.virtualroom.exception.RecordNotFoundException;
import com.aa.virtualroom.model.JOB_STATUS;
import com.aa.virtualroom.model.JobDetails;

@Service
public class FiuJobService {
	private final Path fileStorageLocation;

	@Autowired
	FIUJobRepo docStorageRepo;

	@Autowired
	public FiuJobService(JobDetails jobDetails) {
		this.fileStorageLocation = Paths.get(jobDetails.getUploadDir())
				.toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FiuBinaryStorageException("Could not create the directory where the uploaded files will be stored.", ex);
		}
	}
	public String storeFile(MultipartFile file, JobDetails jobDetails) {
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
			fileName = jobDetails.getRequesterFiu() + "_" + System.currentTimeMillis() + fileExtension;
			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			//JobDetails jobDetails = new JobDetails();
			//create the object before insert
			jobDetails.setBinaryName(fileName);
			jobDetails.setUploadDir(targetLocation.toString());
			jobDetails.setStatus(JOB_STATUS.CREATED.name());
			docStorageRepo.save(jobDetails);
			return jobDetails.getJobId().toString();
		}catch (IOException ex) {
			throw new FiuBinaryStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	} 

	public JobDetails getJobDetails(String jobId) throws RecordNotFoundException {
		JobDetails jobDetail = null;
		Optional<JobDetails> jobDetails = docStorageRepo.getJobById(UUID.fromString(jobId));
		if(jobDetails.isPresent()) {
			jobDetail = jobDetails.get();
			JobDetails copyToJobDetails = new JobDetails(jobDetail.getJobId(),jobDetail.getFiuInputs(),jobDetail.getBinaryName(),jobDetail.getUploadDir(),jobDetail.getStatus());
			return copyToJobDetails;
		}
		else {
			throw new RecordNotFoundException("Auction Id is not avilable");
		}
	}
}
