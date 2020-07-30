package com.aa.virtualroom.controller;

import com.aa.virtualroom.exception.RecordNotFoundException;
import com.aa.virtualroom.model.FunctionDetails;
import com.aa.virtualroom.response.ErrorResponse;
import com.aa.virtualroom.response.FunctionDetailsResponse;
import com.aa.virtualroom.response.FunctionIdResponse;
import com.aa.virtualroom.response.FunctionResponse;
import com.aa.virtualroom.service.FiuFunctionService;
import com.aa.virtualroom.validator.JsonSchemaValidator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.chrono.CopticChronology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/vdr")
public class FiuFunctionController {

    private static Logger LOGGER = LogManager.getLogger(FiuFunctionController.class);

    @Autowired
    private FiuFunctionService fiuFunctionService;

    @PostMapping("/createFunction")
    public ResponseEntity<?> createFunction(@RequestParam("function") MultipartFile file,
                                            @RequestParam("fiuId") String fiuId,
                                            @RequestParam("jsonSchema") String jsonSchema,
                                            @RequestParam("handler") String handler,
                                            @RequestParam("runtime") String runtime,
                                            @RequestParam("functionName") String functionName,
                                            @RequestParam(required = false) String functionDescription) {
    	JsonSchemaValidator.validation(jsonSchema);
        FunctionDetails functionDetails = new FunctionDetails();
        functionDetails.setFiuId(fiuId);
        functionDetails.setJsonSchema(jsonSchema);
        functionDetails.setFunctionDescription(functionDescription);
        functionDetails.setRuntime(runtime);
        functionDetails.setHandler(handler);
        functionDetails.setFunctionName(functionName);
        //TODO: validate JsonSchema
        FunctionIdResponse functionIdResponse =
            new FunctionIdResponse(fiuFunctionService.createFunction(file, functionDetails));
		    /*
		    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/")
				.path(fileName)
				.toUriString();
			*/
        return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(functionIdResponse);
    }
    
    @PutMapping("/updateFunction/{functionId}")
    public ResponseEntity<?> updateFunction(
    		@PathVariable String functionId,
    		@RequestParam(required = false) MultipartFile function,
                                            @RequestParam("fiuId") String fiuId,
                                            @RequestParam("jsonSchema") String jsonSchema,
                                            @RequestParam("handler") String handler,
                                            @RequestParam("runtime") String runtime,
                                            @RequestParam("functionName") String functionName,
                                            @RequestParam(required = false) String functionDescription
                                            ) throws RecordNotFoundException, ParseException {
    	JsonSchemaValidator.validation(jsonSchema);
        FunctionDetails functionDetails = new FunctionDetails();
        functionDetails.setFunctionId(UUID.fromString(functionId));
        functionDetails.setFiuId(fiuId);
        functionDetails.setJsonSchema(jsonSchema);
        functionDetails.setFunctionDescription(functionDescription);
        functionDetails.setRuntime(runtime);
        functionDetails.setHandler(handler);
        functionDetails.setFunctionName(functionName);
        //TODO: validate JsonSchema
        fiuFunctionService.UpdateFunction(function, functionDetails);
        FunctionDetailsResponse functionDetailsResponse = new FunctionDetailsResponse(functionId, functionDetails.getJsonSchema(), functionDetails.getState().toString(),functionDetails.getCreateDate().toString(), java.time.LocalDateTime.now().toString(),  functionDetails.getFunctionName(), functionDetails.getHandler(), functionDetails.getRuntime());
        Map<String, FunctionDetailsResponse> functions = new HashMap<String, FunctionDetailsResponse>();
        functions.put(functionId, functionDetailsResponse);
        FunctionResponse functionResponse = new FunctionResponse(functionDetails.getFiuId().toString(), functions);
        
        return ResponseEntity.status(HttpStatus.OK)
            .body(functionResponse);
    }

    @GetMapping("/getFunctionDetails")
    public ResponseEntity<?> getFunctionDetails(@RequestParam("functionId") String functionId) {
            FunctionDetails functionDetails = fiuFunctionService.getFunctionDetails(functionId);
            ;
            FunctionDetailsResponse functionDetailsResponse = new FunctionDetailsResponse(functionId, functionDetails.getJsonSchema(), functionDetails.getState().toString(),functionDetails.getCreateDate().toString(), functionDetails.getLastUpdateDate().toString(),  functionDetails.getFunctionName(), functionDetails.getHandler(), functionDetails.getRuntime());
            Map<String, FunctionDetailsResponse> functions = new HashMap<String, FunctionDetailsResponse>();
            functions.put(functionId, functionDetailsResponse);
            FunctionResponse functionResponse = new FunctionResponse(functionDetails.getFiuId().toString(), functions);
            
            return ResponseEntity.status(HttpStatus.OK)
                .body(functionResponse);
       
    }

    @GetMapping("/getFunctionsByFiuId")
    public ResponseEntity<?> getFunctionsByFiuId(@RequestParam("fiuId") String fiuId) {
        List<FunctionDetails> listOfFunctions = fiuFunctionService.getFunctions(fiuId);
        Map<String, FunctionDetailsResponse> functions = new HashMap<String, FunctionDetailsResponse>();
        if(listOfFunctions.isEmpty()) {
        	 throw new RecordNotFoundException("Could not find fiuId=" + fiuId);
        }
        for (FunctionDetails functionDetails : listOfFunctions) {
            FunctionDetailsResponse functionDetailsResponse = new FunctionDetailsResponse(functionDetails.getFunctionId().toString(), functionDetails.getJsonSchema(), functionDetails.getState().toString(), functionDetails.getCreateDate().toString(), functionDetails.getLastUpdateDate().toString(),  functionDetails.getFunctionName(), functionDetails.getHandler(), functionDetails.getRuntime());
			functions.put(functionDetails.getFunctionId().toString(), functionDetailsResponse);
		}
        FunctionResponse functionResponse = new FunctionResponse(fiuId, functions);
        return ResponseEntity.status(HttpStatus.OK).body(functionResponse);
    }
}
