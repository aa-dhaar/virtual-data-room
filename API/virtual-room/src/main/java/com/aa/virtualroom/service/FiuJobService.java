package com.aa.virtualroom.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aa.virtualroom.dao.FIUJobRepo;
import com.aa.virtualroom.exception.FiuBinaryStorageException;
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
			docStorageRepo.save(jobDetails);
			return fileName;
		}catch (IOException ex) {
			throw new FiuBinaryStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	} 
}
