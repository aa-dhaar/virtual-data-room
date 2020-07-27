package com.aa.virtualroom.service;

import com.aa.virtualroom.dao.FiuFunctionRepo;
import com.aa.virtualroom.exception.FiuBinaryStorageException;
import com.aa.virtualroom.exception.RecordNotFoundException;
import com.aa.virtualroom.model.FunctionDetails;
import com.aa.virtualroom.model.FunctionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class FiuFunctionService {

    private static final String FILE_STORAGE_LOCATION = Paths.get("").toAbsolutePath().toString() + "/target/uploads";

    @Autowired
    FiuFunctionRepo fiuFunctionRepo;

    @Autowired
    public FiuFunctionService() {
        File directory = new File(FILE_STORAGE_LOCATION);
        if (!directory.exists()) {
            directory.mkdir();
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
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            //create the object before insert
            functionDetails.setFunctionName(fileName);
            functionDetails.setS3Location(targetLocation.toString());
            functionDetails.setState(FunctionState.PENDING.name());
            fiuFunctionRepo.save(functionDetails);
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

}
