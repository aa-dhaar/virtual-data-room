package com.aa.virtualroom.controller;

import com.aa.virtualroom.exception.RecordNotFoundException;
import com.aa.virtualroom.model.FunctionDetails;
import com.aa.virtualroom.response.ErrorResponse;
import com.aa.virtualroom.response.FunctionDetailsResponse;
import com.aa.virtualroom.response.FunctionIdResponse;
import com.aa.virtualroom.response.FunctionResponse;
import com.aa.virtualroom.service.FiuFunctionService;
import com.aa.virtualroom.validator.JsonSchemaValidator;
import com.google.gson.JsonParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vdr")
public class FiuFunctionController {

	private static Logger LOGGER = LogManager.getLogger(FiuFunctionController.class);

	@Autowired
	private FiuFunctionService fiuFunctionService;

	@PostMapping("/createFunction")
	public ResponseEntity<?> createFunction(@RequestParam("function") MultipartFile functionFile,
			@RequestParam("fiuId") String fiuId,
			@RequestParam("jsonSchema") String jsonSchema,
			@RequestParam("handler") String handler,
			@RequestParam("runtime") String runtime,
			@RequestParam("functionName") String functionName,
			@RequestParam(required = false) String functionDescription) {
		try {
			JsonSchemaValidator.validate(jsonSchema);
			FunctionDetails functionDetails = new FunctionDetails();
			functionDetails.setFiuId(fiuId);
			functionDetails.setJsonSchema(jsonSchema);
			functionDetails.setFunctionDescription(functionDescription);
			functionDetails.setRuntime(runtime);
			functionDetails.setHandler(handler);
			functionDetails.setFunctionName(functionName);
			FunctionIdResponse functionIdResponse =
					new FunctionIdResponse(fiuFunctionService.createFunction(functionFile, functionDetails));
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(functionIdResponse);
		} catch (InvalidObjectException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse(e.getMessage()));
		}
		catch (JsonParseException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("Invalid Jsonschema"));
		}
	}

	@PutMapping("/updateFunction/{functionId}")
	public ResponseEntity<?> updateFunction(@PathVariable String functionId,
			@RequestParam("function") MultipartFile functionFile,
			@RequestParam("fiuId") String fiuId,
			@RequestParam("jsonSchema") String jsonSchema,
			@RequestParam("handler") String handler,
			@RequestParam("runtime") String runtime,
			@RequestParam("functionName") String functionName,
			@RequestParam(required = false) String functionDescription) {

		try {
			JsonSchemaValidator.validate(jsonSchema);
			FunctionDetails functionDetails = fiuFunctionService.getFunctionDetails(functionId);
			if (functionDetails != null) {
				FunctionDetails updatedFunctionDetails = new FunctionDetails();
				updatedFunctionDetails.setFiuId(fiuId);
				updatedFunctionDetails.setJsonSchema(jsonSchema);
				updatedFunctionDetails.setFunctionDescription(functionDescription);
				updatedFunctionDetails.setRuntime(runtime);
				updatedFunctionDetails.setHandler(handler);
				updatedFunctionDetails.setFunctionName(functionName);
				updatedFunctionDetails.setFunctionId(functionId);
				FunctionIdResponse functionIdResponse =
						new FunctionIdResponse(fiuFunctionService.createFunction(functionFile, updatedFunctionDetails));
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(functionIdResponse);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ErrorResponse("Could not find FunctionId=" + functionId));
			}
		} catch (RecordNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse(e.getMessage()));
		} catch (InvalidObjectException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse(e.getMessage()));
		}
		catch (JsonParseException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("Invalid Jsonschema"));
		}

	}

	@GetMapping("/getFunctionDetails")
	public ResponseEntity<?> getFunctionDetails(@RequestParam("functionId") String functionId) {
		try {
			FunctionDetails functionDetails = fiuFunctionService.getFunctionDetails(functionId);
			FunctionDetailsResponse
			functionDetailsResponse =
			new FunctionDetailsResponse(functionId, functionDetails.getJsonSchema(),
					functionDetails.getState().toString(),
					functionDetails.getCreateDate().toString(),
					functionDetails.getLastUpdateDate().toString(),
					functionDetails.getFunctionName(), functionDetails.getFunctionDescription(),
					functionDetails.getHandler(),
					functionDetails.getRuntime());
			Map<String, FunctionDetailsResponse> functions = new HashMap<String, FunctionDetailsResponse>();
			functions.put(functionId, functionDetailsResponse);
			FunctionResponse functionResponse = new FunctionResponse(functionDetails.getFiuId().toString(), functions);
			return ResponseEntity.status(HttpStatus.OK)
					.body(functionResponse);
		} catch (RecordNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/getFunctionsByFiuId")
	public ResponseEntity<?> getFunctionsByFiuId(@RequestParam("fiuId") String fiuId) {
		List<FunctionDetails> listOfFunctions = fiuFunctionService.getFunctions(fiuId);




		Map<String, FunctionDetailsResponse> functions = new HashMap();
		listOfFunctions.forEach((functionDetails) -> functions.computeIfAbsent(functionDetails.getFunctionId().toString(), k -> new FunctionDetailsResponse(functionDetails.getFunctionId().toString(),
				functionDetails.getJsonSchema(),
				functionDetails.getState().toString(),
				functionDetails.getCreateDate().toString(),
				functionDetails.getLastUpdateDate().toString(),
				functionDetails.getFunctionName(),
				functionDetails.getFunctionDescription(),
				functionDetails.getHandler(),
				functionDetails.getRuntime())
				));
		
			
		FunctionResponse functionResponse = new FunctionResponse(fiuId, functions);
		return ResponseEntity.status(HttpStatus.OK).body(functionResponse);
	}
}
