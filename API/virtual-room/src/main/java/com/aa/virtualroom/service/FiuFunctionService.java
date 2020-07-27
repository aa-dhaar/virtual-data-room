package com.aa.virtualroom.service;

import com.aa.virtualroom.dao.FiuFunctionRepo;
import com.aa.virtualroom.exception.FiuBinaryStorageException;
import com.aa.virtualroom.exception.RecordNotFoundException;
import com.aa.virtualroom.model.FunctionDetails;
import com.aa.virtualroom.model.FunctionState;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class FiuFunctionService {

    private static final String FILE_STORAGE_LOCATION = Paths.get("").toAbsolutePath().toString() + "/target/uploads";
    private static final String BUCKET_NAME = "aa-dhaar-api";

    @Autowired
    FiuFunctionRepo fiuFunctionRepo;
    
    @Value("${aws.s3.access-key}")
    private String accessKeyID;
    
    @Value("${aws.s3.secret-access-key}")
    private String secretAccessKey;

    @Autowired
    public FiuFunctionService() {
        File directory = new File(FILE_STORAGE_LOCATION);
        if (!directory.exists()) {
        	try {
    			Files.createDirectories(directory.toPath());
    		} catch (Exception ex) {
    			throw new FiuBinaryStorageException("Could not create the directory where the uploaded files will be stored.", ex);
    		}
        }
        else {
        	System.out.println(directory.toPath());
        }
    }

    public String createFunction(MultipartFile file, FunctionDetails functionDetails) {
        // Normalize file name
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileName = "";
        try {
            // Check if the file's name contains invalid characters
            if (originalFileName.contains("..")) {
                throw new FiuBinaryStorageException(
                    "Sorry! Filename contains invalid path sequence " + originalFileName);
            }
            fileName = functionDetails.getFiuId() + "_" + System.currentTimeMillis() + "_" + originalFileName;
            
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = Paths.get(FILE_STORAGE_LOCATION).resolve(fileName);
            try (OutputStream os = Files.newOutputStream(targetLocation)) {
                os.write(file.getBytes());
            }

            //create the object before insert
            functionDetails.setS3Location(BUCKET_NAME+"/"+functionDetails.getFiuId().toString()+"/"+fileName);
            functionDetails.setState(FunctionState.PENDING.name());
            fiuFunctionRepo.save(functionDetails);
            uploadToS3(targetLocation.toFile(),functionDetails.getFiuId().toString(), fileName);
            
            Files.delete(targetLocation);
            //fiuFunctionRepo.save(functionDetails);
            return functionDetails.getFunctionId().toString();
        } catch (IOException ex) {
            throw new FiuBinaryStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public FunctionDetails getFunctionDetails(String functionId) throws RecordNotFoundException {
        Optional<FunctionDetails> functionDetails = fiuFunctionRepo.getFunctionById(UUID.fromString(functionId));
        if (functionDetails.isPresent()) {
            return functionDetails.get();
        } else {
            throw new RecordNotFoundException("Could not find FunctionId=" + functionId);
        }
    }

    public List<FunctionDetails> getFunctions(String fiuId) {
        Optional<List<FunctionDetails>> functionDetails = fiuFunctionRepo.getFunctionByFiuId(UUID.fromString(fiuId));
        return functionDetails.orElse(Collections.emptyList());
    }
    
    private String uploadToS3(File fileName,String fiuId,String originalFileName) {
    	AWSCredentials credentials = new BasicAWSCredentials(accessKeyID, secretAccessKey);
    	AmazonS3 s3client = AmazonS3ClientBuilder
    			  .standard()
    			  .withCredentials(new AWSStaticCredentialsProvider(credentials))
    			  .withRegion(Regions.US_EAST_1)
    			  .build();
		/*
		 * if(!s3client.doesBucketExist(BUCKET_NAME)) { s3client.createBucket(fiuId);
		 * System.out.println("Bucket Created"); }
		 */
    	String s3FileName = fiuId+ "/" + originalFileName;
    	s3client.putObject(
    			BUCKET_NAME, 
    			s3FileName, 
    			 fileName
    			);
    	
    	return s3FileName;
    }

}
